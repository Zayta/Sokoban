package exp.zhen.zayta.main.game.wake;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.RPG;
import exp.zhen.zayta.UserData;
import exp.zhen.zayta.main.UIAssetDescriptors;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.wake.collision.battle.MonsterAttacksNighterSystem;
import exp.zhen.zayta.main.game.wake.entity.EntityLab;
import exp.zhen.zayta.main.game.wake.map.MapMaker;
import exp.zhen.zayta.main.game.wake.map.blocks.movable_items.MovableBlocksSystem;
import exp.zhen.zayta.main.game.wake.map.blocks.block_player.BlockPauseSystem;
import exp.zhen.zayta.main.game.wake.movement.system.BoundsSystem;
import exp.zhen.zayta.main.game.wake.collision.mission.stone_gathering.StonesSystem;
import exp.zhen.zayta.main.game.wake.movement.system.CameraUpdateSystem;
import exp.zhen.zayta.main.game.wake.movement.system.PositionTrackerUpdateSystem;
import exp.zhen.zayta.main.game.wake.map.blocks.block_npc.BlockChangeDirectionSystem;
import exp.zhen.zayta.main.game.wake.map.blocks.block_npc.IntervalChangeDirectionSystem;
import exp.zhen.zayta.main.game.wake.render.HudRenderSystem;
import exp.zhen.zayta.main.game.wake.movement.system.MovementSystem;
import exp.zhen.zayta.main.game.wake.render.MapRenderSystem;
import exp.zhen.zayta.main.game.wake.render.GameRenderSystem;
import exp.zhen.zayta.main.game.wake.map.blocks.block_npc.WorldWrapChangeDirectionSystem;
import exp.zhen.zayta.main.game.wake.map.blocks.block_player.WorldWrapPauseSystem;
import exp.zhen.zayta.main.game.debug.debug_system.DebugCameraSystem;
import exp.zhen.zayta.main.game.debug.debug_system.DebugRenderSystem;
import exp.zhen.zayta.main.game.debug.debug_system.GridRenderSystem;
import exp.zhen.zayta.main.game.wake.render.NameTagRenderSystem;
import exp.zhen.zayta.main.game.wake.render.StatsRenderSystem;
import exp.zhen.zayta.main.game.wake.visual.AnimationSystem;
import exp.zhen.zayta.main.game.wake.input.InputSystem;
import exp.zhen.zayta.util.GdxUtils;

public class WakeMode implements Screen {
    /*WakeMode mode is when you gather the stones without running into civilians*/
    /*Conquest mode flip tile to control movement, end when defeat all monsters*/
    /*Defense mode is when you defend someone's soul from monsters.*/
    private static final Logger log = new Logger(WakeMode.class.getName(),Logger.DEBUG);
    private static final boolean DEBUG = true;

    private final RPG game;
    private final AssetManager assetManager;

    private EntityLab entityLab;
//    public static Manufacturer manufacturer;

    private MapMaker mapMaker;private TiledMap tiledMap;

    private OrthographicCamera orthographicCamera;
    private Viewport viewport;
    private Viewport hudViewport;
    private ShapeRenderer shapeRenderer;

    private PooledEngine engine;

    public WakeMode(RPG game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        /*Camera*/
        shapeRenderer = new ShapeRenderer();
        orthographicCamera = new OrthographicCamera();
        viewport = new FitViewport(SizeManager.WAKE_WORLD_WIDTH,SizeManager.WAKE_WORLD_HEIGHT,orthographicCamera);
        hudViewport = new FitViewport(SizeManager.HUD_WIDTH,SizeManager.HUD_HEIGHT);

        /*Game Engines*/
        engine = new PooledEngine();
        entityLab = new EntityLab(engine,assetManager);
//        manufacturer = new Manufacturer(assetManager.get(UIAssetDescriptors.WAKE_PLAY),engine);
        mapMaker = new MapMaker(assetManager);
        tiledMap = mapMaker.getTiledMap(MapMaker.Map.irondale);


        addEntities();
        addSystems();
    }
    private void addSystems(){
//        engine.addSystem(new MapRenderSystem(tiledMap,viewport));
        engine.addSystem(new InputSystem(engine));
//        engine.addSystem(new TiledMapStageSystem(tiledMap,viewport,engine));
        addEntityMovementSystems();
        addRenderSystems();
        addGameControllingSystems();
    }

    private void addEntityMovementSystems(){

        engine.addSystem(new PositionTrackerUpdateSystem());//should be first
        engine.addSystem(new MovableBlocksSystem(engine,viewport,assetManager.get(UIAssetDescriptors.WAKE_PLAY)));//sb before movement
        engine.addSystem(new WorldWrapPauseSystem(tiledMap));
        engine.addSystem(new WorldWrapChangeDirectionSystem(tiledMap));
        engine.addSystem(new IntervalChangeDirectionSystem(5));
        engine.addSystem(new BlockChangeDirectionSystem((TiledMapTileLayer) tiledMap.getLayers().get(MapMaker.collisionLayer)));
        engine.addSystem(new BlockPauseSystem((TiledMapTileLayer) tiledMap.getLayers().get(MapMaker.collisionLayer)));//sb before movement


        engine.addSystem(new MovementSystem());
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new CameraUpdateSystem(orthographicCamera,UserData.Player,tiledMap));
    }



    private void addRenderSystems(){
        engine.addSystem(new MapRenderSystem(tiledMap,viewport));
        engine.addSystem(new GameRenderSystem(viewport,game.getBatch()));
        engine.addSystem(new HudRenderSystem(hudViewport,game.getBatch()/*,assetManager.get(UIAssetDescriptors.FONT)*/));
        engine.addSystem(new NameTagRenderSystem(viewport,game.getBatch()));
        engine.addSystem(new StatsRenderSystem(viewport,shapeRenderer));

        if(DEBUG) {
            engine.addSystem(new GridRenderSystem(viewport, shapeRenderer));
            engine.addSystem(new DebugRenderSystem(viewport, shapeRenderer));
            engine.addSystem(new DebugCameraSystem(orthographicCamera, SizeManager.WAKE_WORLD_CENTER_X, SizeManager.WAKE_WORLD_CENTER_Y));
        }
    }

    private void addGameControllingSystems(){
        engine.addSystem(new StonesSystem(game,engine));
        engine.addSystem(new MonsterAttacksNighterSystem(game,engine));
//        engine.addSystem(new UndeadXCivilianCollisionSystem(game,engine));
    }


    private void addEntities() {
        entityLab.addEntities();
    }


//    private void setInputHandler(){
//        if(Gdx.app.getType()==Application.ApplicationType.Desktop) {
//            Gdx.input.setInputProcessor(new KeyboardInputHandler(engine));
//        }
//        else {
//            Gdx.input.setInputProcessor(new GestureDetector(new GestureInputHandler(engine)));
//        }
//    }

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();
        engine.update(delta);
    }



    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
        hudViewport.update(width,height,true);
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
