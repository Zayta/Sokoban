package snow.zhen.zayta.main;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;

import snow.zhen.zayta.main.assets.AssetDescriptors;
import snow.zhen.zayta.main.sokoban.PlayScreen;
import snow.zhen.zayta.main.story.StoryScreen;

public class Game extends com.badlogic.gdx.Game {

    private static final Logger log = new Logger(Game.class.getName(),Logger.DEBUG);
    private AssetManager assetManager;//dont make static
    private SpriteBatch batch; private ShapeRenderer shapeRenderer;

    private UserData userData;



    private MainScreen mainScreen;
    private PlayScreen playScreen;
    private StoryScreen storyScreen;



    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_NONE);

        GameConfig.configScreenOrientation(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


        assetManager = new AssetManager();
        assetManager.getLogger().setLevel(Logger.DEBUG);


        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
//        login();
        userData = UserData.getInstance();

        snow.zhen.zayta.main.LoadingScreen loadingScreen = new LoadingScreen(this);

        setScreen(loadingScreen);
        if(snow.zhen.zayta.main.assets.AssetDescriptors.UI_SKIN== snow.zhen.zayta.main.assets.AssetDescriptors.NEON_SKIN)
            assetManager.get(AssetDescriptors.UI_SKIN).getFont("font").getData().setScale(2,2);

        //creates screens after assets have been loaded
        storyScreen = new StoryScreen(this);
        playScreen = new PlayScreen(this);
        mainScreen = new MainScreen(this,playScreen,storyScreen);//need to create a main for loading screen to go to

    }


    //completes the lvl of the game
    public void complete(int lvl){

        userData.complete(lvl);
        System.out.println("Number of lvls completed: "+userData.getNumCompleted());
//        goToMain();
        continueSoko();
    }

    private void continueSoko(){

        playScreen.setLvl(getUserData().getNumCompleted());
        setScreen(playScreen);
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
