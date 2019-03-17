package exp.zhen.zayta.game.quest;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.InputListener.GestureInputHandler;
import exp.zhen.zayta.InputListener.KeyboardInputHandler;
import exp.zhen.zayta.RPG;
import exp.zhen.zayta.assets.AssetDescriptors;
import exp.zhen.zayta.config.SizeManager;
import exp.zhen.zayta.game.quest.entity.EntityFactory;
import exp.zhen.zayta.game.quest.entity.EntityFactoryController;
import exp.zhen.zayta.game.quest.entity.game_objects.Manufacturer;
import exp.zhen.zayta.game.quest.movement.system.movementLimitations.blocks.movable_items.MovableBlocksSystem;
import exp.zhen.zayta.game.quest.movement.system.BoundsSystem;
import exp.zhen.zayta.game.quest.collision.UndeadXCivilianCollisionSystem;
import exp.zhen.zayta.game.quest.mode.stone_gathering.StonesSystem;
import exp.zhen.zayta.game.quest.movement.system.PositionTrackerSystem;
import exp.zhen.zayta.game.quest.movement.system.movementLimitations.blocks.BlocksSystem;
import exp.zhen.zayta.game.quest.render.HudRenderSystem;
import exp.zhen.zayta.game.quest.movement.system.MovementSystem;
import exp.zhen.zayta.game.quest.render.MapRenderSystem;
import exp.zhen.zayta.game.quest.render.QuestRenderSystem;
import exp.zhen.zayta.game.quest.movement.system.movementLimitations.world_wrap.WorldWrapChangeDirectionSystem;
import exp.zhen.zayta.game.quest.movement.system.movementLimitations.world_wrap.WorldWrapPauseSystem;
import exp.zhen.zayta.debug.debug_system.DebugCameraSystem;
import exp.zhen.zayta.debug.debug_system.DebugRenderSystem;
import exp.zhen.zayta.debug.debug_system.GridRenderSystem;
import exp.zhen.zayta.game.quest.user_control.TiledMapStageSystem;
import exp.zhen.zayta.util.GdxUtils;

public class Quest implements Screen {
    /*Quest mode is when you gather the stones without running into civilians*/
    /*Conquest mode flip tile to control movement, end when defeat all monsters*/
    /*Defense mode is when you defend someone's soul from monsters.*/
    private static final Logger log = new Logger(Quest.class.getName(),Logger.DEBUG);
    private static final boolean DEBUG = true;

    private final RPG game;
    private final AssetManager assetManager;

    private EntityFactoryController entityFactoryController;
    public static Manufacturer manufacturer;

    private OrthographicCamera orthographicCamera;
    private Viewport viewport;
    private Viewport hudViewport;
    private ShapeRenderer shapeRenderer;
//    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TiledMap tiledMap;
    private PooledEngine engine;
//    private Sound hitSound;
//    private boolean reset;

    public Quest(RPG game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        tiledMap = assetManager.get(AssetDescriptors.MAP_MEMLAB2);
        /*Camera*/
        shapeRenderer = new ShapeRenderer();
        orthographicCamera = new OrthographicCamera();
        viewport = new FitViewport(SizeManager.WORLD_WIDTH,SizeManager.WORLD_HEIGHT,orthographicCamera);
        hudViewport = new FitViewport(SizeManager.HUD_WIDTH,SizeManager.HUD_HEIGHT);
        /*Game Engines*/
        engine = new PooledEngine();
        entityFactoryController = new EntityFactoryController(new EntityFactory(engine,assetManager));
        manufacturer = new Manufacturer(assetManager.get(AssetDescriptors.GAME_PLAY),engine);
        setInputHandler();
        addEntities();
        addSystems();
    }
    private void addSystems(){

        engine.addSystem(new MapRenderSystem(tiledMap,viewport));
        engine.addSystem(new TiledMapStageSystem(tiledMap,viewport));
        addEntityMovementSystems();
        addRenderSystems();
        addGameControllingSystems();
    }

    private void addEntityMovementSystems(){

        engine.addSystem(new PositionTrackerSystem());//should be first
        engine.addSystem(new MovementSystem());
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new MovableBlocksSystem(engine,viewport));//sb before movement
        engine.addSystem(new WorldWrapPauseSystem(viewport));
        engine.addSystem(new WorldWrapChangeDirectionSystem(viewport));
        engine.addSystem(new BlocksSystem((TiledMapTileLayer) tiledMap.getLayers().get(0)));
//        engine.addSystem(new BlockPauseSystem((TiledMapTileLayer) tiledMap.getLayers().get(0)));

        engine.addSystem(new AnimationSystem());
    }
    private void addRenderSystems(){
        engine.addSystem(new QuestRenderSystem(viewport,game.getBatch()));
        engine.addSystem(new HudRenderSystem(hudViewport,game.getBatch()/*,assetManager.get(AssetDescriptors.FONT)*/));

        if(DEBUG) {
            engine.addSystem(new GridRenderSystem(viewport, shapeRenderer));
            engine.addSystem(new DebugRenderSystem(viewport, shapeRenderer));
            engine.addSystem(new DebugCameraSystem(orthographicCamera, SizeManager.WORLD_CENTER_X, SizeManager.WORLD_CENTER_Y));
        }
    }

    private void addGameControllingSystems(){
        engine.addSystem(new StonesSystem(game,engine));
//        engine.addSystem(new UndeadXCivilianCollisionSystem(game,engine));
    }


    private void addEntities() {
        entityFactoryController.addEntities();
    }


    private void setInputHandler(){
        if(Gdx.app.getType()==Application.ApplicationType.Desktop) {
            Gdx.input.setInputProcessor(new KeyboardInputHandler(engine));
        }
        else {
            Gdx.input.setInputProcessor(new GestureDetector(new GestureInputHandler(engine)));
        }
    }

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
