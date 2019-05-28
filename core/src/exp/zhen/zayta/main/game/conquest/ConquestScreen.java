package exp.zhen.zayta.main.game.conquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.RPG;
import exp.zhen.zayta.assets.AssetDescriptors;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.conquest.soldiers.nur.NUR;
import exp.zhen.zayta.main.game.conquest.soldiers.utsubyo.Utsubyo;
import exp.zhen.zayta.main.game.debug.DebugCameraController;
import exp.zhen.zayta.main.menu.MenuScreen;
import exp.zhen.zayta.util.GdxUtils;
import exp.zhen.zayta.util.ViewportUtils;

public class ConquestScreen implements Screen {

    private static final Logger log = new Logger(ConquestScreen.class.getName(),Logger.DEBUG);

    // == constants ==
    private static final float PADDING = 20.0f;

    // == attributes ==
    private final RPG game;
    private final AssetManager assetManager;

    private final SpriteBatch batch;

    private final NUR nur; private final Utsubyo utsubyo;
    private Territory territory;

    private OrthographicCamera camera;
    private Viewport viewport;

    private ShapeRenderer renderer;

    private OrthographicCamera uiCamera;
    private Viewport uiViewport;
    private BitmapFont font;
    private final GlyphLayout layout = new GlyphLayout();


    private DebugCameraController debugCameraController;

    //    private Viewport hudViewport;
//    private ShapeRenderer shapeRenderer;
    Pool<MoveToAction> pool;
    //    private PooledEngine engine;
    public ConquestScreen(RPG game) {
        this.game = game;
        assetManager = game.getAssetManager();
        batch = game.getBatch();

        nur = new NUR(assetManager.get(AssetDescriptors.CONQUEST));
        utsubyo = new Utsubyo(nur.getConquestAtlas());
    }

    @Override
    public void show() {
        //game
        camera = new OrthographicCamera();
        viewport = new FitViewport(SizeManager.CQ_WORLD_WIDTH, SizeManager.CQ_WORLD_HEIGHT, camera);

        territory = new Territory(viewport,batch,nur.getConquestAtlas());
        territory.setDebugAll(true);
        Gdx.input.setInputProcessor(territory);

        renderer = new ShapeRenderer();

        //fonts and display
        uiCamera = new OrthographicCamera();
        uiViewport = new FitViewport(SizeManager.HUD_WIDTH, SizeManager.HUD_HEIGHT, uiCamera);
        font = assetManager.get(AssetDescriptors.FONT);

        //debug
        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(SizeManager.CQ_WORLD_CENTER_X, SizeManager.CQ_WORLD_CENTER_Y);

    }












    @Override
    public void render(float delta) {
        // handle debug camera input and apply configuration to our camera
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

//        update(delta);

        // clear screen
        GdxUtils.clearScreen();

        viewport.apply();
        renderGamePlay();

        uiViewport.apply();
        renderUi();

        viewport.apply();
        renderDebug();

        if(isGameOver()) {
            game.setScreen(new MenuScreen(game));
        }
    }

    private void renderGamePlay() {
        batch.setProjectionMatrix(camera.combined);
        territory.act();
        territory.draw();
    }

    private void renderUi() {
        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();

        // draw lives

        // draw score

        batch.end();
    }

    private void renderDebug() {
        // draw grid
        ViewportUtils.drawGrid(viewport, renderer);
    }

    public boolean isGameOver() {
        //todo determine game over (when all nighters are out of HP)
        return false;
    }

    private void updateScore(float delta) {
            //todo track score
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
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
        territory.dispose();
    }
}