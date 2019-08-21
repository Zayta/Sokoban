package exp.zhen.zayta.main.loading;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import exp.zhen.zayta.main.RPG;
import exp.zhen.zayta.main.UIAssetDescriptors;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.essence_lab.assets.WPAssetDescriptors;
import exp.zhen.zayta.util.GdxUtils;


public class LoadingScreen extends ScreenAdapter {

    // == constants ==
    private static final Logger log = new Logger(LoadingScreen.class.getName(), Logger.DEBUG);

    private static final float PROGRESS_BAR_WIDTH = SizeManager.HUD_WIDTH / 2f; // world units
    private static final float PROGRESS_BAR_HEIGHT = 60; // world units

    // == attributes ==
    private OrthographicCamera orthographicCamera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private float progress;
    private float waitTime = 0.75f;
    private boolean changeScreen;

    private final RPG game;
    private final AssetManager assetManager;


    // == constructors ==
    public LoadingScreen(RPG game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    // == public methods ==
    @Override
    public void show() {
//        ////log.debug("show");
        orthographicCamera = new OrthographicCamera();
        viewport = new FitViewport(SizeManager.HUD_WIDTH, SizeManager.HUD_HEIGHT, orthographicCamera);
        renderer = new ShapeRenderer();


        loadAssets();
        loadMap();

        assetManager.finishLoading();

    }
    private void loadAssets(){
        assetManager.load(UIAssetDescriptors.HEADING_FONT);
        assetManager.load(UIAssetDescriptors.FONT);
        assetManager.load(UIAssetDescriptors.LAB);
        assetManager.load(UIAssetDescriptors.CONQUEST);
        assetManager.load(UIAssetDescriptors.MENU_CLIP);
        assetManager.load(UIAssetDescriptors.UI_SKIN);


        assetManager.load(WPAssetDescriptors.MAP_GENERATOR);

    }
    private void loadMap(){
        //todo in future when use TiledMap, load here
        assetManager.setLoader(TiledMap.class,new TmxMapLoader());
        assetManager.load(WPAssetDescriptors.MAP_MEMLAB2);
        assetManager.load(WPAssetDescriptors.MAP_IRONDALE);
        assetManager.load(WPAssetDescriptors.MAP_TILE_STORAGE);

    }

    @Override
    public void render(float delta) {
        update(delta);

        GdxUtils.clearScreen();
        viewport.apply();
        renderer.setProjectionMatrix(orthographicCamera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        draw();

        renderer.end();

        if(changeScreen) {
            game.goToMain();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
//        ////log.debug("hide");
        // NOTE: screens dont dispose automatically
        dispose();
    }

    @Override
    public void dispose() {
//        ////log.debug("dispose");
        renderer.dispose();
        renderer = null;
    }

    // == private methods ==
    private void update(float delta) {
        // progress is between 0 and 1
        progress = assetManager.getProgress();

        // update returns true when all assets are loaded
        if(assetManager.update()) {
            waitTime -= delta;

            if(waitTime <= 0) {
                changeScreen = true;
            }
        }
    }

    private void draw() {
        float progressBarX = (SizeManager.HUD_WIDTH - PROGRESS_BAR_WIDTH) / 2f;
        float progressBarY = (SizeManager.HUD_HEIGHT - PROGRESS_BAR_HEIGHT) / 2f;

        renderer.rect(progressBarX, progressBarY,
                progress * PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT
        );
    }

    private static void waitMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
