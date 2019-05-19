package exp.zhen.zayta.main.game.conquest;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.RPG;
import exp.zhen.zayta.assets.AssetDescriptors;
import exp.zhen.zayta.assets.RegionNames;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.util.GdxUtils;

public class Conquest implements Screen {
    /*Conquest mode is when you gather the stones without running into civilians*/
    /*Conquest mode flip tile to control movement, end when defeat all monsters*/
    /*Defense mode is when you defend someone's soul from monsters.*/
    private static final Logger log = new Logger(Conquest.class.getName(),Logger.DEBUG);

    private final RPG game;
    private final AssetManager assetManager;

    private Stage stage;
    private Territory territory;

    private OrthographicCamera orthographicCamera;
    private Viewport viewport;
//    private Viewport hudViewport;
//    private ShapeRenderer shapeRenderer;
    Pool<MoveToAction> pool;
//    private PooledEngine engine;
    public Conquest(RPG game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {

        orthographicCamera = new OrthographicCamera();
        viewport = new FitViewport(SizeManager.CQ_WORLD_WIDTH,SizeManager.CQ_WORLD_HEIGHT,orthographicCamera);

        //libgdx action pooling - used so that new action does not have to be made every time for an actor
        pool = new Pool<MoveToAction>() {
            protected MoveToAction newObject () {
                return new MoveToAction();
            }
        };

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        initTerritory();
    }

    private void initTerritory(){
        territory = new Territory(assetManager.get(AssetDescriptors.CONQUEST));

        territory.setSize(SizeManager.CQ_WORLD_WIDTH,SizeManager.CQ_WORLD_HEIGHT);
        stage.addActor(territory);


    }
















    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();
        stage.act(delta);
        stage.draw();
//        engine.update();
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
        stage.dispose();
    }
}
