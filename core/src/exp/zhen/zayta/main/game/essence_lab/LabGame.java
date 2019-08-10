package exp.zhen.zayta.main.game.essence_lab;

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
import exp.zhen.zayta.main.game.debug.debug_system.DebugMovableBlocksSystem;
import exp.zhen.zayta.main.game.debug.debug_system.DebugPositionTrackerSystem;
import exp.zhen.zayta.main.game.debug.debug_system.DebugRectangularBoundsRenderSystem;
//import exp.zhen.zayta.main.game.essence_lab.blocks.BlocksTrackerSystem;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.NPCReaperSystem;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.PlayerReaperSystem;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.mind_growing.LanternSystem;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.mind_growing.NaiveLanternSystem;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.movable_items.MoveItemSystem;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.movable_items.PickUpMovableItem;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.movable_items.RemoveItemSystem;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.movable_items.locker.LockerByColorSystem;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.movable_items.UpdatePushDirectionSystem;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.spirit_gathering.SpiritSystem;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.npc_ai.NPCNonstopMovementSystem;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.collision_mechanics.bomb_trigger.LandmineExplosionSystem;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.collision_mechanics.collide_and_fight.MonsterAttacksNighterSystem;
import exp.zhen.zayta.main.game.essence_lab.entity.EntityLab;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.stone_gathering.StonesSystem;
import exp.zhen.zayta.main.game.essence_lab.input.InputSystem;
import exp.zhen.zayta.main.game.essence_lab.map.MapMaker;
import exp.zhen.zayta.main.game.essence_lab.blocks.BlockSystem;
import exp.zhen.zayta.main.game.essence_lab.blocks.UnblockSystem;
import exp.zhen.zayta.main.game.essence_lab.blocks.block_player.MapBlockPauseSystem;
import exp.zhen.zayta.main.game.essence_lab.movement.NonOverlapBoundsSystem;
import exp.zhen.zayta.main.game.essence_lab.movement.system.CircularBoundsSystem;
import exp.zhen.zayta.main.game.essence_lab.movement.system.MovementLimitationBoundClipSystem;
import exp.zhen.zayta.main.game.essence_lab.movement.system.RectangularBoundsSystem;
import exp.zhen.zayta.main.game.essence_lab.movement.system.CameraUpdateSystem;
import exp.zhen.zayta.main.game.essence_lab.movement.system.PositionTrackerUpdateSystem;
import exp.zhen.zayta.main.game.essence_lab.blocks.block_npc.MapBlockChangeDirectionSystem;
import exp.zhen.zayta.main.game.essence_lab.blocks.block_npc.IntervalChangeDirectionSystem;
import exp.zhen.zayta.main.game.essence_lab.render.HudRenderSystem;
import exp.zhen.zayta.main.game.essence_lab.movement.system.MovementSystem;
import exp.zhen.zayta.main.game.essence_lab.render.LockerRenderSystem;
import exp.zhen.zayta.main.game.essence_lab.render.MonoColorEntityRenderSystem;
import exp.zhen.zayta.main.game.essence_lab.render.MultiColorEntityRenderSystem;
import exp.zhen.zayta.main.game.essence_lab.blocks.block_player.WorldWrapPauseSystem;
import exp.zhen.zayta.main.game.debug.debug_system.DebugCameraSystem;
import exp.zhen.zayta.main.game.debug.debug_system.DebugCircularBoundsRenderSystem;
import exp.zhen.zayta.main.game.debug.debug_system.GridRenderSystem;
import exp.zhen.zayta.main.game.essence_lab.render.StatsRenderSystem;
import exp.zhen.zayta.main.game.essence_lab.render.TiledMapRenderSystem;
import exp.zhen.zayta.main.game.essence_lab.render.animation.particle.ParticleAnimationSystem;
import exp.zhen.zayta.main.game.essence_lab.render.animation.sprite.SpriteAnimationSystem;
import exp.zhen.zayta.util.GdxUtils;

public class LabGame implements Screen {
    /*LabGame mode is when you gather the stones without running into civilians*/
    /*Conquest mode flip tile to control movement, end when defeat all monsters*/
    /*Defense mode is when you defend someone's soul from monsters.*/
    private static final Logger log = new Logger(LabGame.class.getName(),Logger.DEBUG);
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

    public LabGame(RPG game) {
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
//        addMovableItemSystems();//should be after movement systems
        addAnimationSystems();//must be before render
        addRenderSystems();
        addBattleSystems();
        addGameControllingSystems();
    }
    private void addTiledMapSystems(){
        TiledMap tiledMap = mapMaker.getTiledMap(MapMaker.Map.irondale);
//        engine.addSystem(new WorldWrapChangeDirectionSystem(tiledMap));
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get(MapMaker.collisionLayer);
        //log.debug("CollisionLayer is "+collisionLayer);
        if(collisionLayer!=null){
            engine.addSystem(new MapBlockChangeDirectionSystem(collisionLayer));
            engine.addSystem(new MapBlockPauseSystem(collisionLayer));//sb before movement
        }

        engine.addSystem(new CameraUpdateSystem(orthographicCamera,tiledMap));

        engine.addSystem(new TiledMapRenderSystem(tiledMap,viewport));

    }

    private void addEntityMovementSystems(){
        engine.addSystem(new PositionTrackerUpdateSystem());//should be first
//        engine.addSystem(new BlocksTrackerSystem());
//        engine.addSystem(new CircMovableObjSystem(engine,viewport,assetManager.get(UIAssetDescriptors.LAB)));//sb before movement. defective.

        engine.addSystem(new WorldWrapPauseSystem());
//        //log.debug("maxX: "+mapMaker.getMapBoundmaxX()+", maxY: "+mapMaker.getMapBoundmaxY());
        engine.addSystem(new BlockSystem(engine,assetManager.get(UIAssetDescriptors.LAB)));//sb before npcnonstopmovmentsystem


        engine.addSystem(new LanternSystem(game,engine));

        engine.addSystem(new NPCNonstopMovementSystem());
        engine.addSystem(new IntervalChangeDirectionSystem(5));

        /*after mechs are set, add base movement systems*/
        engine.addSystem(new MovementSystem());
        engine.addSystem(new RectangularBoundsSystem());
        engine.addSystem(new CircularBoundsSystem());

    }
    private void addMovableItemSystems(){
        engine.addSystem(new RemoveItemSystem());//sb before pickup and move
        engine.addSystem(new PickUpMovableItem(engine,viewport,assetManager.get(UIAssetDescriptors.LAB)));
        engine.addSystem(new UpdatePushDirectionSystem());
        engine.addSystem(new MoveItemSystem());//sb after movement and bounds system

//        engine.addSystem(new MovementLimitationBoundClipSystem()); //todo Nighter gets sticky wit this system
//        engine.addSystem(new NonOverlapBoundsSystem());
//        engine.addSystem(new LanternSystem(game,engine));
////        engine.addSystem(new NaiveLanternSystem(game,engine));

        engine.addSystem(new UnblockSystem());

    }


    private void addAnimationSystems(){
        engine.addSystem(new SpriteAnimationSystem());
        engine.addSystem(new ParticleAnimationSystem());
    }

    private void addRenderSystems(){
        engine.addSystem(new LockerRenderSystem(viewport,shapeRenderer));//must be first cuz its background
//        engine.addSystem(new GeneratedMapRenderSystem(mapMaker.generateMap(),viewport,game.getBatch()));
        engine.addSystem(new MultiColorEntityRenderSystem(viewport,game.getBatch()));
        engine.addSystem(new HudRenderSystem(hudViewport,game.getBatch(),assetManager.get(UIAssetDescriptors.FONT)));
//        engine.addSystem(new NameTagRenderSystem(viewport,game.getBatch()));
        engine.addSystem(new StatsRenderSystem(viewport,shapeRenderer));

        engine.addSystem(new MonoColorEntityRenderSystem(viewport));//sb last cuz it changes batch color.
        if(DEBUG) {
            engine.addSystem(new GridRenderSystem(viewport, shapeRenderer));
            engine.addSystem(new DebugCircularBoundsRenderSystem(viewport, shapeRenderer));
            engine.addSystem(new DebugRectangularBoundsRenderSystem(viewport,shapeRenderer));
            engine.addSystem(new DebugCameraSystem(orthographicCamera, SizeManager.WAKE_WORLD_CENTER_X, SizeManager.WAKE_WORLD_CENTER_Y));
//            engine.addSystem(new DebugBlocksSystem(viewport,game.getBatch()));
            engine.addSystem(new DebugMovableBlocksSystem(viewport,game.getBatch()));
//            engine.addSystem(new DebugPositionTrackerSystem(viewport,game.getBatch()));
        }
    }

    private void addBattleSystems(){
        engine.addSystem(new MonsterAttacksNighterSystem());
        engine.addSystem(new LandmineExplosionSystem());
        engine.addSystem(new NPCReaperSystem(engine));
    }

    private void addGameControllingSystems(){
        engine.addSystem(new StonesSystem(game,engine));
        engine.addSystem(new LockerByColorSystem(game,engine));
        engine.addSystem(new SpiritSystem(game,engine));
        engine.addSystem(new PlayerReaperSystem(game,engine));
    }


    private void addInputSystems(){
        engine.addSystem(new InputSystem(engine,hudViewport,skin,assetManager.get(UIAssetDescriptors.LAB)));
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
//        //log.debug("Resize is called\nWidth = "+width+"\nHeight = "+height+"\nWORLD HEIGHT: "+SizeManager.WAKE_WORLD_HEIGHT);

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
