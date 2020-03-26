package exp.zhen.zayta.main.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import exp.zhen.zayta.main.Game;
import exp.zhen.zayta.main.UserData;
import exp.zhen.zayta.main.assets.AssetDescriptors;
import exp.zhen.zayta.main.sokoban.input.Hud;
import exp.zhen.zayta.main.sokoban.input.KeyboardController;
import exp.zhen.zayta.main.sokoban.map.Map;
//import exp.zhen.zayta.main.sokoban_OOP.Puzzle;

public class PlayScreen extends ScreenAdapter {
    private Game game;
    private UserData userData;

    // == attributes ==

    private PlayController controller;
    private PlayRenderer renderer;
    private Map map;
    private Hud hud;
    private InputMultiplexer inputMultiplexer;
//    private int curLvl =0;
    private final AssetManager assetManager; private Skin skin;


    public PlayScreen(Game game){
        //game data
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.userData = game.getUserData();

        this.map = new Map(assetManager.get(AssetDescriptors.SOKOBAN));
        this.controller = new PlayController(map);
        this.hud = new Hud(controller,game.getBatch(),assetManager);
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(hud.getStage());
        inputMultiplexer.addProcessor(new KeyboardController(controller));
        Gdx.input.setInputProcessor(inputMultiplexer);
        this.renderer = new PlayRenderer(game.getBatch(), assetManager, map,hud);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
        controller.initLvl(map);

    }
    @Override
    public void render(float delta) {
        controller.update(delta);
        renderer.render(delta);

        hud.getStage().act(delta); //act the Hud
        if(controller.isComplete())
            progress();
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void hide() {
//        dispose();
        controller.hide();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }


    /****For screen transition*****/
    public void progress(){
        game.unlockScene();
    }
    public void fail(){
        game.stop();
    }

    /*For level management*/
    public void setLevel(int lvl){
        controller.setLvl(lvl);
    }

}
