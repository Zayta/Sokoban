package exp.zhen.zayta;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.loading.LoadingScreen;

public class RPG extends Game {

    private AssetManager assetManager;//dont make static
    private SpriteBatch batch; private ShapeRenderer shapeRenderer;

    private UserData userData;

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

        setScreen(new LoadingScreen(this));




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
}
