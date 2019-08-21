package exp.zhen.zayta.main;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.essence_lab.Experiment;
import exp.zhen.zayta.main.loading.LoadingScreen;
import exp.zhen.zayta.main.shop.ShopScreen;
import exp.zhen.zayta.main.story.StoryBoardScreen;

public class RPG extends Game {

    private static final Logger log = new Logger(RPG.class.getName(),Logger.DEBUG);
    private AssetManager assetManager;//dont make static
    private SpriteBatch batch; private ShapeRenderer shapeRenderer;

    private UserData userData;
    private MainScreen mainScreen;

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
        setScreen(new LoadingScreen(this));//goes to main
        mainScreen.createExperiment(); //experiment is dependent on assets loaded by loading screen
    }


    private class MainScreen extends ScreenBase {

        private final Logger log = new Logger(exp.zhen.zayta.main.RPG.MainScreen.class.getName(), Logger.DEBUG);

        private Experiment experiment; private UserData userData;

        MainScreen(RPG game) {
            super(game);
            this.userData = game.getUserData();
        }

        private void createExperiment(){
            experiment = new Experiment(game);
        }


        @Override
        protected Actor createUi() {
            Table table = new Table();

            TextureAtlas menuAtlas = assetManager.get(UIAssetDescriptors.MENU_CLIP);

            TextureRegion backgroundRegion = menuAtlas.findRegion("fullscanner");
            table.setBackground(new TextureRegionDrawable(backgroundRegion));

            Label label = new Label("Experiment "+userData.getNumScenesUnlocked(),assetManager.get(UIAssetDescriptors.UI_SKIN));
            label.setFontScale(3);

            table.add(label).top().left();
            table.row();
            table.add(scene(menuAtlas));
            table.row();
            table.pad(20);
            table.add(buttonTable());
            table.center();
            table.setFillParent(true);
            table.pack();

            return table;
        }

        private ImageButton scene(TextureAtlas menuAtlas){
            TextureRegion scene = menuAtlas.findRegion("Lorale");
            TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(scene);
            ImageButton imageButton = new ImageButton(textureRegionDrawable){};
            imageButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    play(userData.getNumScenesUnlocked());
                }
            });
            imageButton.top();
            return imageButton;
        }


        private Table buttonTable(){
            Skin uiskin = assetManager.get(UIAssetDescriptors.UI_SKIN);
            // play button
            TextButton playButton = new TextButton("PLAY", uiskin);
            playButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    play(userData.getNumScenesUnlocked());
                }
            });

            // high score button
            TextButton shopButton = new TextButton("SHOP", uiskin);
            shopButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    shop();
                }
            });

            // options button
            TextButton storyButton = new TextButton("STORY", uiskin);
            storyButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    story();
                }
            });

            // quit button
            TextButton quitButton = new TextButton("QUIT", uiskin);
            quitButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    quit();
                }
            });

            // setup table
            Table buttonTable = new Table(uiskin);
            int numButtons = 4;
            buttonTable.add(playButton).width(SizeManager.WIDTH/numButtons);
            buttonTable.add(shopButton).width(SizeManager.WIDTH/numButtons);
            buttonTable.add(storyButton).width(SizeManager.WIDTH/numButtons);
            buttonTable.add(quitButton).width(SizeManager.WIDTH/numButtons);


            buttonTable.bottom();
            return buttonTable;
        }

        private void play(int chosenLvl) {
            experiment.setCurrentLvl(chosenLvl);
            game.setScreen(experiment);
        }

        private void shop() {
            game.setScreen(new ShopScreen(game));
        }

        private void story() {
            game.setScreen(new StoryBoardScreen(game));
        }

        private void quit() {
            Gdx.app.exit();
        }

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


}
