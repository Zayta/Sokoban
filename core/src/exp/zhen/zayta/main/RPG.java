package exp.zhen.zayta.main;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.assets.AssetDescriptors;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.characters.nur.NUR;
import exp.zhen.zayta.main.game.essence_lab.engine.entity.utsubyo.Utsubyo;

public class RPG extends Game {

    private static final Logger log = new Logger(RPG.class.getName(),Logger.DEBUG);
    private AssetManager assetManager;//dont make static
    private SpriteBatch batch; private ShapeRenderer shapeRenderer;

    private UserData userData;
    private MainScreen mainScreen;

    private NUR nur; private Utsubyo utsubyo;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        SizeManager.config(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


        assetManager = new AssetManager();
        assetManager.getLogger().setLevel(Logger.DEBUG);


        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
//        login();
        userData = new UserData();
        mainScreen = new MainScreen(this);//need to create a main for loading screen to go to

        LoadingScreen loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);//goes to main
        createWithAssets();
    }
    //dependent on assets loaded by loading screen todo put these in loading screen.
    void createWithAssets(){
        nur = new NUR(assetManager.get(AssetDescriptors.LAB));//must be before create screens cuz experiment uses it.
        mainScreen.createScreens();

    }


    public void unlockScene(){

        log.debug("research is advancing");
        userData.unlockScene();
        goToMain();
    }

    public void stop(){
        log.debug("stop is occurring");
        goToMain();
    }


    public void goToMain(){
        setScreen(mainScreen);
    }

    public UserData getUserData() {
        return userData;
    }



    //    private void login(){
//        HttpRequestBuilder builder = new HttpRequestBuilder();
//        Net.HttpRequest request = builder.newRequest().method(Net.HttpMethods.GET).url("http://yourdomain.com/script.php").build();
//        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
//
//        final long start = System.nanoTime(); //for checking the time until response
//        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
//            @Override
//            public void handleHttpResponse(Net.HttpResponse httpResponse) {
//                Gdx.app.log("WebRequest", "HTTP Response code: " + httpResponse.getStatus().getStatusCode());
//                Gdx.app.log("WebRequest", "HTTP Response code: " + httpResponse.getResultAsString());
//                Gdx.app.log("WebRequest", "Response time: " + ((System.nanoTime() - start) / 1000000) + "ms");
//            }
//
//            @Override
//            public void failed(Throwable t) {
//                Gdx.app.log("WebRequest", "HTTP request failed");
//            }
//
//            @Override
//            public void cancelled() {
//                Gdx.app.log("WebRequest", "HTTP request cancelled");
//            }
//        });
//    }


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

    public NUR getNur() {
        return nur;
    }

    public Utsubyo getUtsubyo() {
        return utsubyo;
    }
}
