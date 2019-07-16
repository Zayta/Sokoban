package exp.zhen.zayta.main.game.wake;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.RPG;
import exp.zhen.zayta.main.UIAssetDescriptors;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.debug.debug_system.DebugPositionTrackerSystem;
import exp.zhen.zayta.main.game.debug.debug_system.DebugRectangularBoundsRenderSystem;
import exp.zhen.zayta.main.game.wake.game_mechanics.NPCReaperSystem;
import exp.zhen.zayta.main.game.wake.game_mechanics.PlayerReaperSystem;
import exp.zhen.zayta.main.game.wake.game_mechanics.npc_ai.NPCNonstopMovementSystem;
import exp.zhen.zayta.main.game.wake.game_mechanics.collision_mechanics.bomb_trigger.LandmineExplosionSystem;
import exp.zhen.zayta.main.game.wake.game_mechanics.collision_mechanics.collide_and_fight.MonsterAttacksNighterSystem;
import exp.zhen.zayta.main.game.wake.entity.EntityLab;
import exp.zhen.zayta.main.game.wake.game_mechanics.mission.stone_gathering.StonesSystem;
import exp.zhen.zayta.main.game.wake.input.InputSystem;
import exp.zhen.zayta.main.game.wake.map.MapMaker;
import exp.zhen.zayta.main.game.wake.map.blocks.BlockSystem;
import exp.zhen.zayta.main.game.wake.map.blocks.movable_items.MovableObjSystem;
import exp.zhen.zayta.main.game.wake.map.blocks.block_player.MapBlockPauseSystem;
import exp.zhen.zayta.main.game.wake.movement.system.CircularBoundsSystem;
import exp.zhen.zayta.main.game.wake.movement.system.RectangularBoundsSystem;
import exp.zhen.zayta.main.game.wake.movement.system.CameraUpdateSystem;
import exp.zhen.zayta.main.game.wake.movement.system.PositionTrackerUpdateSystem;
import exp.zhen.zayta.main.game.wake.map.blocks.block_npc.MapBlockChangeDirectionSystem;
import exp.zhen.zayta.main.game.wake.map.blocks.block_npc.IntervalChangeDirectionSystem;
import exp.zhen.zayta.main.game.wake.render.HudRenderSystem;
import exp.zhen.zayta.main.game.wake.movement.system.MovementSystem;
import exp.zhen.zayta.main.game.wake.render.GameRenderSystem;
import exp.zhen.zayta.main.game.wake.map.blocks.block_player.WorldWrapPauseSystem;
import exp.zhen.zayta.main.game.debug.debug_system.DebugCameraSystem;
import exp.zhen.zayta.main.game.debug.debug_system.DebugCircularBoundsRenderSystem;
import exp.zhen.zayta.main.game.debug.debug_system.GridRenderSystem;
import exp.zhen.zayta.main.game.wake.render.NameTagRenderSystem;
import exp.zhen.zayta.main.game.wake.render.StatsRenderSystem;
import exp.zhen.zayta.main.game.wake.render.TiledMapRenderSystem;
import exp.zhen.zayta.main.game.wake.render.animation.particle.ParticleAnimationSystem;
import exp.zhen.zayta.main.game.wake.render.animation.sprite.SpriteAnimationSystem;
import exp.zhen.zayta.util.GdxUtils;

public class WakeMode implements Screen {
    /*WakeMode mode is when you gather the stones without running into civilians*/
    /*Conquest mode flip tile to control movement, end when defeat all monsters*/
    /*Defense mode is when you defend someone's soul from monsters.*/
    private static final Logger log = new Logger(WakeMode.class.getName(),Logger.DEBUG);
    private static final boolean DEBUG = true;

    private final RPG game;
    private final AssetManager assetManager; private Skin skin;

    private EntityLab entityLab;
//    public static Manufacturer manufacturer;

    private MapMaker mapMaker; private boolean enableTiledMap = false;//private TiledMap tiledMap;

    private OrthographicCamera orthographicCamera;
    private Viewport viewport;
    private Viewport hudViewport;

    private ShapeRenderer shapeRenderer;

    private PooledEngine engine;

    public WakeMode(RPG game) {
        this.game = game;
        assetManager = game.getAssetManager();

        SizeManager.config(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
        /*Camera*/
        shapeRenderer = new ShapeRenderer();
        orthographicCamera = new OrthographicCamera();
        viewport = new FitViewport(SizeManager.WAKE_WORLD_WIDTH,SizeManager.WAKE_WORLD_HEIGHT,orthographicCamera);

        hudViewport = new FitViewport(SizeManager.HUD_WIDTH,SizeManager.HUD_HEIGHT);

        /*Text*/
        skin = assetManager.get(UIAssetDescriptors.UI_SKIN);

        /*Game Engines*/
        engine = new PooledEngine();
        entityLab = new EntityLab(engine,assetManager);
        mapMaker = new MapMaker(assetManager);

        addEntities();
        addSystems();
    }
    private void addSystems(){
        if(enableTiledMap)
            addTiledMapSystems();//sb before movement systems

        addInputSystems();
        addEntityMovementSystems();
        addAnimationSystems();//must be before render
        addRenderSystems();
        addBattleSystems();
        addGameControllingSystems();
    }
    private void addTiledMapSystems(){
        TiledMap tiledMap = mapMaker.generateMap();
//        engine.addSystem(new WorldWrapChangeDirectionSystem(tiledMap));

        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get(MapMaker.collisionLayer);
        log.debug("CollisionLayer is "+collisionLayer);
        if(collisionLayer!=null){
            engine.addSystem(new MapBlockChangeDirectionSystem(collisionLayer));
            engine.addSystem(new MapBlockPauseSystem(collisionLayer));//sb before movement
        }

        engine.addSystem(new CameraUpdateSystem(orthographicCamera,RPG.userData.Player,tiledMap));

        engine.addSystem(new TiledMapRenderSystem(tiledMap,viewport));

    }

    private void addEntityMovementSystems(){


        engine.addSystem(new PositionTrackerUpdateSystem());//should be first

        engine.addSystem(new MovableObjSystem(engine,viewport,assetManager.get(UIAssetDescriptors.WAKE_PLAY)));//sb before movement

        engine.addSystem(new WorldWrapPauseSystem(mapMaker.getMapBoundmaxX(),mapMaker.getMapBoundmaxY()));
        engine.addSystem(new BlockSystem(engine,assetManager.get(UIAssetDescriptors.WAKE_PLAY)));//sb before npcnonstopmovmentsystem
        engine.addSystem(new NPCNonstopMovementSystem());
        engine.addSystem(new IntervalChangeDirectionSystem(5));

        /*after mechs are set, add base movement systems*/
        engine.addSystem(new MovementSystem());
        engine.addSystem(new RectangularBoundsSystem());
        engine.addSystem(new CircularBoundsSystem());
    }


    private void addAnimationSystems(){
        engine.addSystem(new SpriteAnimationSystem());
        engine.addSystem(new ParticleAnimationSystem());
    }

    private void addRenderSystems(){
//        engine.addSystem(new GeneratedMapRenderSystem(mapMaker.generateMap(),viewport,game.getBatch()));
        engine.addSystem(new GameRenderSystem(viewport,game.getBatch()));
        engine.addSystem(new HudRenderSystem(hudViewport,game.getBatch(),assetManager.get(UIAssetDescriptors.FONT)));
//        engine.addSystem(new NameTagRenderSystem(viewport,game.getBatch()));
        engine.addSystem(new StatsRenderSystem(viewport,shapeRenderer));

        if(DEBUG) {
            engine.addSystem(new GridRenderSystem(viewport, shapeRenderer));
            engine.addSystem(new DebugCircularBoundsRenderSystem(viewport, shapeRenderer));
            engine.addSystem(new DebugRectangularBoundsRenderSystem(viewport,shapeRenderer));
            engine.addSystem(new DebugCameraSystem(orthographicCamera, SizeManager.WAKE_WORLD_CENTER_X, SizeManager.WAKE_WORLD_CENTER_Y));
            engine.addSystem(new DebugPositionTrackerSystem(viewport,game.getBatch()));
        }
    }

    private void addBattleSystems(){
        engine.addSystem(new MonsterAttacksNighterSystem());
        engine.addSystem(new LandmineExplosionSystem());
        engine.addSystem(new NPCReaperSystem(engine));
    }

    private void addGameControllingSystems(){
        engine.addSystem(new StonesSystem(game,engine));
        engine.addSystem(new PlayerReaperSystem(game,engine));
    }


    private void addInputSystems(){
        engine.addSystem(new InputSystem(engine,hudViewport,skin,assetManager.get(UIAssetDescriptors.WAKE_PLAY)));
    }



    private void addEntities() {
        entityLab.addEntities();
    }


    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();
        engine.update(delta);
    }



    @Override
    public void resize(int width, int height) {
        SizeManager.config(width,height);
        viewport.setWorldSize(SizeManager.WAKE_WORLD_WIDTH,SizeManager.WAKE_WORLD_HEIGHT);
        viewport.update(width,height,true);
        hudViewport.setWorldSize(SizeManager.HUD_WIDTH,SizeManager.HUD_HEIGHT);
        hudViewport.update(width,height,true);
        log.debug("Resize is called\nWidth = "+width+"\nHeight = "+height+"\nWORLD HEIGHT: "+SizeManager.WAKE_WORLD_HEIGHT);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
//        tiledMap.dispose();orthogonalTiledMapRenderer.dispose();
        shapeRenderer.dispose();
    }
}
