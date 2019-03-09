package exp.zhen.zayta.mode.quest;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.InputListener.GestureInputHandler;
import exp.zhen.zayta.InputListener.KeyboardInputHandler;
import exp.zhen.zayta.RPG;
import exp.zhen.zayta.config.SizeManager;
import exp.zhen.zayta.mode.quest.entity.EntityFactory;
import exp.zhen.zayta.mode.quest.entity.EntityFactoryController;
import exp.zhen.zayta.mode.quest.system.AnimationSystem;
import exp.zhen.zayta.mode.quest.system.movement.BoundsSystem;
import exp.zhen.zayta.mode.quest.system.CleanUpSystem;
import exp.zhen.zayta.mode.quest.system.collision.UndeadXCivilianCollisionSystem;
import exp.zhen.zayta.mode.quest.system.collision.NighterXStoneCollisionSystem;
import exp.zhen.zayta.mode.quest.system.movement.PositionTrackerSystem;
import exp.zhen.zayta.mode.quest.system.movement.movementLimitations.BlockPauseSystem;
import exp.zhen.zayta.mode.quest.system.render.HudRenderSystem;
import exp.zhen.zayta.mode.quest.system.movement.MovementSystem;
import exp.zhen.zayta.mode.quest.system.render.QuestRenderSystem;
import exp.zhen.zayta.mode.quest.system.movement.movementLimitations.WorldWrapChangeDirectionSystem;
import exp.zhen.zayta.mode.quest.system.movement.movementLimitations.WorldWrapPauseSystem;
import exp.zhen.zayta.mode.quest.system.debug.DebugCameraSystem;
import exp.zhen.zayta.mode.quest.system.debug.DebugRenderSystem;
import exp.zhen.zayta.mode.quest.system.debug.GridRenderSystem;
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
    private OrthographicCamera camera;
    private Viewport viewport;
    private Viewport hudViewport;
    private ShapeRenderer shapeRenderer;
//    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;private TiledMap tiledMap;
    private PooledEngine engine;
//    private Sound hitSound;
//    private boolean reset;

    public Quest(RPG game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        /*Camera*/
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        viewport = new FitViewport(SizeManager.WORLD_WIDTH,SizeManager.WORLD_HEIGHT,camera);
        hudViewport = new FitViewport(SizeManager.HUD_WIDTH,SizeManager.HUD_HEIGHT);
        /*Game Engines*/
        engine = new PooledEngine();
        entityFactoryController = new EntityFactoryController(new EntityFactory(engine,assetManager));
        setInputHandler();
        addEntities();
        addSystems();
    }
    private void addSystems(){
        addEntityMovementSystems();
        addRenderSystems();
        addGameControllingSystems();
//        engine.addSystem(new ScoreSystem());
//        engine.addSystem(new MapRenderSystem(assetManager.get(AssetDescriptors.MAP_MEMLAB1),viewport));
    }

    private void addEntityMovementSystems(){
        engine.addSystem(new MovementSystem());
        engine.addSystem(new BlockPauseSystem());
        engine.addSystem(new WorldWrapPauseSystem(viewport));
        engine.addSystem(new WorldWrapChangeDirectionSystem(viewport));
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new PositionTrackerSystem());
        engine.addSystem(new CleanUpSystem());
        engine.addSystem(new AnimationSystem());
    }
    private void addRenderSystems(){
        engine.addSystem(new QuestRenderSystem(viewport,game.getBatch()));
        engine.addSystem(new HudRenderSystem(hudViewport,game.getBatch()/*,assetManager.get(AssetDescriptors.FONT)*/));

        if(DEBUG) {
            engine.addSystem(new GridRenderSystem(viewport, shapeRenderer));
            engine.addSystem(new DebugRenderSystem(viewport, shapeRenderer));
            engine.addSystem(new DebugCameraSystem(camera, SizeManager.WORLD_CENTER_X, SizeManager.WORLD_CENTER_Y));
        }
    }

    private void addGameControllingSystems(){
        engine.addSystem(new NighterXStoneCollisionSystem(game,engine));
        engine.addSystem(new UndeadXCivilianCollisionSystem(game,engine));
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
