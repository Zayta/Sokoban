package exp.zhen.zayta.versions_unused.conquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.main.Game;
import exp.zhen.zayta.main.assets.AssetDescriptors;
import exp.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager;
import exp.zhen.zayta.versions_unused.conquest.soldiers.nur.NUR;
import exp.zhen.zayta.versions_unused.conquest.soldiers.utsubyo.Utsubyo;
import exp.zhen.zayta.versions_unused.conquest.tiles.Tile;
import exp.zhen.zayta.main.debug.DebugCameraController;
import exp.zhen.zayta.util.GdxUtils;
import exp.zhen.zayta.util.ViewportUtils;

public class Conquest implements Screen {

    private static final Logger log = new Logger(Conquest.class.getName(),Logger.DEBUG);

    // == constants ==
    private static final float PADDING = 20.0f;

    // == attributes ==
    private final Game game;
    private final AssetManager assetManager;
    
    //viewports and cameras
    private OrthographicCamera camera;
    private Viewport viewport;

//    private OrthographicCamera statsCamera;
//    private Viewport statsViewport;

//    private float scaleWidthOfStatsViewportToViewport,scaleHeightOfStatsViewportToViewport;

    private DebugCameraController debugCameraController;

    //visual/drawing
    private ShapeRenderer renderer;
    private final SpriteBatch batch;
    //text
    private BitmapFont font;
    private GlyphLayout layout;
    
    //game Managers
    private final NUR nur; private final Utsubyo utsubyo;
    private Territory territory;
    
    //gamePlay fields
    private Tile[] nPos;
    private Tile[][] mPos;
    private int numNighters = 3, numMonsters = 12;



    //    private Viewport hudViewport;
//    private ShapeRenderer shapeRenderer;
    Pool<MoveToAction> pool;
    //    private PooledEngine engine;
    public Conquest(Game game) {
        this.game = game;
        assetManager = game.getAssetManager();
        batch = game.getBatch();

        nur = new NUR(assetManager.get(AssetDescriptors.CONQUEST));
        utsubyo = new Utsubyo(nur.getConquestAtlas());
    }

    @Override
    public void show() {
        //cameras and viewports
        camera = new OrthographicCamera();
        viewport = new FitViewport(SizeManager.CQ_WORLD_WIDTH, SizeManager.CQ_WORLD_HEIGHT, camera);

//        statsCamera = new OrthographicCamera();
//        statsViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, statsCamera);
//
//        scaleWidthOfStatsViewportToViewport = statsViewport.getWorldWidth()/viewport.getWorldWidth();
//        scaleHeightOfStatsViewportToViewport = statsViewport.getWorldHeight()/viewport.getWorldHeight();

        //fonts and display
        layout = new GlyphLayout();
        font = new BitmapFont();
        renderer = new ShapeRenderer();

        //debug
        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(SizeManager.CQ_WORLD_CENTER_X, SizeManager.CQ_WORLD_CENTER_Y);

//        debugStatsCameraController = new DebugCameraController();
//        debugStatsCameraController.setStartPosition(statsViewport.getWorldWidth()/2,statsViewport.getWorldHeight()/2);

        //game manager
        territory = new Territory(viewport,batch,nur.getConquestAtlas());
        territory.setDebugAll(true);
        Gdx.input.setInputProcessor(territory);

        initGamePlay();
        setFont();
        makeMenuButton();
    }

    private void initGamePlay(){
        territory.create(Territory.Terrain.LAB,numNighters,numMonsters);
        nPos = territory.initNPos(nur);
        mPos = territory.initMPos(nPos);
    }
    private void setFont(){
        font.setColor(Color.RED);
        font.setUseIntegerPositions(false);
        font.getData().setScale(2);
    }

    private void makeMenuButton(){

        nPos[0].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.goToMain();
            }
        });
    }

//    private void makeMenuButton(){
//        TextButton menuButton = new TextButton("MENU", new Skin());
//        menuButton.getSkin().getFont(AssetPaths.UI_FONT).setUseIntegerPositions(false);
//        menuButton.setScaleX(GameConfig.CQ_WORLD_WIDTH/GameConfig.WIDTH);
//        menuButton.setScaleY(GameConfig.CQ_WORLD_HEIGHT/GameConfig.HEIGHT);
//        menuButton.setOrigin(Align.center);
//        menuButton.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                game.goToMain();
//            }
//        });
//        territory.addActor(menuButton);
//        menuButton.setBounds(0,0,territory.getPadding(),territory.getPadding());
////        return menuButton;
//    }




    
    
    
    








    @Override
    public void render(float delta) {
        // handle debug camera input and apply configuration to our camera
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);
//        debugStatsCameraController.handleDebugInput(delta);
//        debugStatsCameraController.applyTo(statsCamera);

//        update(delta);

        // clear screen
        GdxUtils.clearScreen();

        viewport.apply();
        renderGamePlay();

//        statsViewport.apply();
//        renderStats();

        viewport.apply();
        renderDebug();

        if(isGameOver()) {
            game.goToMain();
        }
    }

    private void renderGamePlay() {
        batch.setProjectionMatrix(camera.combined);
        territory.act();
        territory.draw();
    }

//    private void renderStats() {
//        batch.setProjectionMatrix(statsCamera.combined);
//        batch.begin();
//
//        for(int i = 0; i<nPos.length;i++){
//            Tile npos = nPos[i];
////            //////log.debug("nPos ["+i+"] x is "+npos.getX(Align.bottomLeft)+", and scaled is "+npos.getX(Align.bottomLeft)*scaleWidthOfStatsViewportToViewport);
//            String stats = npos.getSoldier().toString();
//            layout.setText(fonts, stats);
//            fonts.draw(batch, layout, (npos.getX(Align.left))*scaleWidthOfStatsViewportToViewport,(npos.getY(Align.bottom))*scaleHeightOfStatsViewportToViewport+layout.height);
//
////            fonts.draw(batch,stats, -130,(npos.getY(Align.center))*scaleHeightOfStatsViewportToViewport);
//        }
////        // draw lives
////        String livesText = "LIVES: " + lives;
////        layout.setText(fonts, livesText);
////        fonts.draw(batch, layout, PADDING, GameConfig.HUD_HEIGHT - layout.height);
////
////        // draw score
////        String scoreText = "SCORE: " + displayScore;
////        layout.setText(fonts, scoreText);
////        fonts.draw(batch, layout,
////                GameConfig.HUD_WIDTH - layout.width - PADDING,
////                GameConfig.HUD_HEIGHT - layout.height
////        );
//
//        batch.end();
//    }

    private void renderDebug() {
        // draw grid
        ViewportUtils.drawGrid(viewport, renderer);
    }

    public boolean isGameOver() {
        //todo determine game over (when all nighters are out of hp)
        return false;
    }

    private void updateScore(float delta) {
            //todo track score
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
//        statsViewport.update(width,height,true);
//        hudViewport.update(width,height,true);
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
//        shapeRenderer.dispose();
        renderer.dispose();
        territory.dispose();
        font.dispose();
    }
}