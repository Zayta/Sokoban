package snow.zhen.zayta.main;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;

import snow.zhen.zayta.main.assets.AssetDescriptors;

public class Game extends com.badlogic.gdx.Game {

    private static final Logger log = new Logger(Game.class.getName(),Logger.DEBUG);
    private AssetManager assetManager;//dont make static
    private SpriteBatch batch; private ShapeRenderer shapeRenderer;

    private UserData userData;
    private MainScreen mainScreen;


    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        GameConfig.configScreenOrientation(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


        assetManager = new AssetManager();
        assetManager.getLogger().setLevel(Logger.DEBUG);


        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
//        login();
        userData = UserData.getInstance();
        mainScreen = new MainScreen(this);//need to create a main for loading screen to go to

        snow.zhen.zayta.main.LoadingScreen loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);//goes to main
        createWithAssets();
    }
    //dependent on assets loaded by loading screen todo put these in loading screen.
    void createWithAssets(){
        if(snow.zhen.zayta.main.assets.AssetDescriptors.UI_SKIN== snow.zhen.zayta.main.assets.AssetDescriptors.NEON_SKIN)
            assetManager.get(AssetDescriptors.UI_SKIN).getFont("font").getData().setScale(2,2);
        mainScreen.createScreens();

    }


    public void unlockScene(){

        //log.debug("research is advancing");
        userData.unlockScene();
        goToMain();
    }

    public void stop(){
        //log.debug("stop is occurring");
        goToMain();
    }


    public void goToMain(){
        setScreen(mainScreen);
    }

    public UserData getUserData() {
        return userData;
    }



    @Override
    public void dispose() {
        assetManager.dispose();
        batch.dispose();
        shapeRenderer.dispose();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

}
