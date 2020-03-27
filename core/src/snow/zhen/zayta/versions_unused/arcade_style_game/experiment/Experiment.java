package snow.zhen.zayta.versions_unused.arcade_style_game.experiment;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import snow.zhen.zayta.main.Game;
import snow.zhen.zayta.main.assets.AssetDescriptors;
//import snow.zhen.zayta.main.game.experiment.engine.blocks.BlocksTrackerSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.blocks.BlockSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.blocks.block_player.MapBlockPauseSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.blocks.block_player.WorldWrapPauseSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.EntityLab;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.lanterns.LanternSpawnSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.monsters.MonsterAttacksNighterSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionTracker;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.system.CameraUpdateSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.system.CircularBoundsSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.system.PositionTrackerUpdateSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.system.RectangularBoundsSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.EntityRenderSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.HudRenderSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.MonoColorEntityRenderSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.StatsRenderSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.TiledMapRenderSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.particle.ParticleAnimationSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.sprite.SpriteAnimationSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.movable_items.MoveItemSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.movable_items.PickUpMovableItem;
import snow.zhen.zayta.versions_unused.arcade_style_game.movable_items.RemoveItemSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.movable_items.UpdatePushDirectionSystem;
import snow.zhen.zayta.versions_unused.game_mechanics.NPCReaperSystem;
import snow.zhen.zayta.versions_unused.game_mechanics.PlayerReaperSystem;
import snow.zhen.zayta.versions_unused.game_mechanics.npc_ai.NPCNonstopMovementSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.input.InputSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.MapMaker;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.blocks.block_npc.MapBlockChangeDirectionSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.blocks.block_npc.IntervalChangeDirectionSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.system.MovementSystem;
import snow.zhen.zayta.util.GdxUtils;
import snow.zhen.zayta.versions_unused.arcade_style_game.debug.debug_system.DebugCameraSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.debug.debug_system.DebugCircularBoundsRenderSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.debug.debug_system.DebugPositionTrackerSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.debug.debug_system.DebugRectangularBoundsRenderSystem;
import snow.zhen.zayta.versions_unused.arcade_style_game.debug.debug_system.GridRenderSystem;

public class Experiment implements Screen {
    /*Puzzle mode is when you gather the stones without running into civilians*/
    /*Conquest mode flip tile to control movement, end when defeat all monsters*/
    /*Defense mode is when you defend someone's soul from monsters.*/
    private static final Logger log = new Logger(Experiment.class.getName(),Logger.DEBUG);
    private static final boolean DEBUG = true;

//    private final Game game;
    private final AssetManager assetManager; private Skin skin;

    private EntityLab entityLab;
//    public static Manufacturer manufacturer;

    private MapMaker mapMaker; private boolean enableTiledMap = false;//private TiledMap tiledMap;

    private OrthographicCamera orthographicCamera;
    private Viewport viewport;
    private Viewport hudViewport;

    private ShapeRenderer shapeRenderer; private SpriteBatch batch;

    private PooledEngine engine;
    private Game game;

    private int currentLvl=0;

    public Experiment(Game game) {
//        this.game = game;
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.batch = game.getBatch();
        this.shapeRenderer = game.getShapeRenderer();

        SizeManager.config(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        /*Camera*/
        orthographicCamera = new OrthographicCamera();
        viewport = new FitViewport(SizeManager.WAKE_WORLD_WIDTH, SizeManager.WAKE_WORLD_HEIGHT,orthographicCamera);

        hudViewport = new FitViewport(SizeManager.HUD_WIDTH, SizeManager.HUD_HEIGHT);

        /*Text*/
        skin = assetManager.get(AssetDescriptors.UI_SKIN);

        /*Game Engines*/
        engine = new PooledEngine();
        entityLab = new EntityLab(game.getNur(),engine,assetManager);
        mapMaker = new MapMaker(assetManager);


    }

    @Override
    public void show() {
        //log.debug("Puzzle is showing");
        switch (currentLvl){
            default:
                addEntities(2,1);
                addSystems();
        }
    }
    private void addSystems(){
        if(enableTiledMap)
            addTiledMapSystems();//sb before movement systems

        addInputSystems();
        addEntityMovementSystems();
//        addMovableItemSystems();//should be after movement systems
        addAnimationSystems();//must be before render
        addRenderSystems();
        addGameSystems();
        
        if(DEBUG) {
            addDebugSystems();
        }
    }
    private void addTiledMapSystems(){
        TiledMap tiledMap = mapMaker.getTiledMap(MapMaker.Map.irondale);
//        engine.addSystem(new WorldWrapChangeDirectionSystem(tiledMap));
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get(MapMaker.collisionLayer);
        ////log.debug("CollisionLayer is "+collisionLayer);
        if(collisionLayer!=null){
            engine.addSystem(new MapBlockChangeDirectionSystem(collisionLayer));
            engine.addSystem(new MapBlockPauseSystem(collisionLayer));//sb before movement
        }

        engine.addSystem(new CameraUpdateSystem(engine,orthographicCamera,tiledMap));

        engine.addSystem(new TiledMapRenderSystem(tiledMap,viewport));

    }

    private void addEntityMovementSystems(){
        engine.addSystem(new PositionTrackerUpdateSystem());//should be first
//        engine.addSystem(new BlocksTrackerSystem());
//        engine.addSystem(new CircMovableObjSystem(engine,viewport,assetManager.get(AssetDescriptors.LAB)));//sb before movement. defective.

        engine.addSystem(new WorldWrapPauseSystem());
//        ////log.debug("maxX: "+mapMaker.getMapBoundmaxX()+", maxY: "+mapMaker.getMapBoundmaxY());
        engine.addSystem(new BlockSystem(engine,assetManager.get(AssetDescriptors.LAB)));//sb before npcnonstopmovmentsystem


//        engine.addSystem(new MovingBlocksSystem(game,engine));

        engine.addSystem(new NPCNonstopMovementSystem());
        engine.addSystem(new IntervalChangeDirectionSystem(5));

//        engine.addSystem(new MovementLimitationDetectResetSystem());
        /*after mechs are set, add base movement systems*/
        engine.addSystem(new MovementSystem());
        engine.addSystem(new RectangularBoundsSystem());
        engine.addSystem(new CircularBoundsSystem());
//        addMovableItemSystems();

    }
    private void addMovableItemSystems(){
        engine.addSystem(new RemoveItemSystem());//sb before pickup and move
        engine.addSystem(new PickUpMovableItem(engine,viewport,assetManager.get(AssetDescriptors.LAB)));
        engine.addSystem(new UpdatePushDirectionSystem());
        engine.addSystem(new MoveItemSystem());//sb after movement and bounds system
//        engine.addSystem(new UnblockSystem());

    }


    private void addAnimationSystems(){
        engine.addSystem(new SpriteAnimationSystem());
        engine.addSystem(new ParticleAnimationSystem());
    }

    private void addRenderSystems(){
//        engine.addSystem(new LockerRenderSystem(viewport,shapeRenderer));//must be first cuz its background
//        engine.addSystem(new GeneratedMapRenderSystem(mapMaker.generateMap(),viewport,game.getBatch()));
        engine.addSystem(new EntityRenderSystem(viewport,batch));
        engine.addSystem(new HudRenderSystem(hudViewport,batch,assetManager.get(AssetDescriptors.FONT)));
//        engine.addSystem(new NameTagRenderSystem(viewport,game.getBatch()));
        engine.addSystem(new StatsRenderSystem(viewport,shapeRenderer));

        engine.addSystem(new MonoColorEntityRenderSystem(viewport));//sb last cuz it changes batch color.
    }
    
    private void addGameSystems(){
        //todo make this depend on level: lantern interval depend on lvl, number of monsters dependent on lvl
        engine.addSystem(new MonsterAttacksNighterSystem());
        engine.addSystem(new NPCReaperSystem(engine));
        engine.addSystem(new LanternSpawnSystem(this,entityLab,engine,assetManager.get(AssetDescriptors.LAB),10));
        engine.addSystem(new PlayerReaperSystem(this,engine));
    }

//    private void addBattleSystems(){
//        engine.addSystem(new MonsterAttacksNighterSystem());
////        engine.addSystem(new LandmineExplosionSystem());
//        engine.addSystem(new NPCReaperSystem(engine));
//    }
//
//    private void addGameControllingSystems(){
////        engine.addSystem(new StonesSystem(game,engine));
////        engine.addSystem(new LockerByColorSystem(game,engine));
////        engine.addSystem(new SpiritSystem(game,engine));
//        engine.addSystem(new LanternSpawnSystem(game,engine,10));
//        engine.addSystem(new PlayerReaperSystem(game,engine));
//    }


    private void addInputSystems(){
        engine.addSystem(new InputSystem(engine,hudViewport,skin,assetManager.get(AssetDescriptors.LAB)));
    }

    private void addDebugSystems(){
        engine.addSystem(new GridRenderSystem(viewport, shapeRenderer));
        engine.addSystem(new DebugCircularBoundsRenderSystem(viewport, shapeRenderer));
        engine.addSystem(new DebugRectangularBoundsRenderSystem(viewport,shapeRenderer));
        engine.addSystem(new DebugCameraSystem(orthographicCamera, SizeManager.WAKE_WORLD_CENTER_X, SizeManager.WAKE_WORLD_CENTER_Y));
//            engine.addSystem(new DebugBlocksSystem(viewport,game.getBatch()));
//            engine.addSystem(new DebugColorSystem(viewport,game.getBatch()));
//            engine.addSystem(new DebugMovableBlocksSystem(viewport,game.getBatch()));
        engine.addSystem(new DebugPositionTrackerSystem(viewport,batch));
    }



    private void addEntities(int numNighters, int numMonsters) {
        entityLab.addEntities(numNighters, numMonsters);
    }








    /****For screen transition*****/


    public void progress(){
        reset();
        //log.debug("experiment is progressing");
        game.unlockScene();
    }
    public void fail(){
        reset();
        game.stop();
    }
    private void reset(){
        PositionTracker.reset();
        engine.removeAllEntities();
    }
    public void setCurrentLvl(int currentLvl) {
        this.currentLvl = currentLvl;
    }












    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();
        engine.update(delta);
    }



    @Override
    public void resize(int width, int height) {
        SizeManager.config(width,height);
        viewport.setWorldSize(SizeManager.WAKE_WORLD_WIDTH, SizeManager.WAKE_WORLD_HEIGHT);
        viewport.update(width,height,true);
        hudViewport.setWorldSize(SizeManager.HUD_WIDTH, snow.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager.HUD_HEIGHT);
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
//        shapeRenderer.dispose();//disposing shapeRenderer here causes error
    }
}
