package exp.zhen.zayta.main.sokoban;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import exp.zhen.zayta.main.Game;
import exp.zhen.zayta.main.UserData;
import exp.zhen.zayta.main.assets.AssetDescriptors;
import exp.zhen.zayta.main.sokoban.map.Map;
//import exp.zhen.zayta.main.sokoban_OOP.Puzzle;

public class PlayScreen extends ScreenAdapter {
    private Game game;
    private UserData userData;

    // == attributes ==

    private PlayController controller;
    private PlayRenderer renderer;
    private Map map;

//    private int curLvl =0;
    private final AssetManager assetManager; private Skin skin;


    public PlayScreen(Game game){
        //game data
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.userData = game.getUserData();

        this.map = new Map(assetManager.get(AssetDescriptors.SOKOBAN));
        this.renderer = new PlayRenderer(game.getBatch(), assetManager, controller);
        this.controller = new PlayController(map);

    }

    @Override
    public void show() {
        controller.initLvl(map);

    }
    @Override
    public void render(float delta) {
        controller.update(delta);
        renderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void hide() {
//        dispose();
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
