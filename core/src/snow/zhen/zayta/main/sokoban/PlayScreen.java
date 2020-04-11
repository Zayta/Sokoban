package snow.zhen.zayta.main.sokoban;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import snow.zhen.zayta.main.Game;
import snow.zhen.zayta.main.GameConfig;
import snow.zhen.zayta.main.UserData;
import snow.zhen.zayta.main.assets.AssetDescriptors;
import snow.zhen.zayta.main.sokoban.input.Hud;
import snow.zhen.zayta.main.sokoban.input.KeyboardController;
import snow.zhen.zayta.main.sokoban.input.SwipeController;
import snow.zhen.zayta.main.sokoban.map.Map;
//import snow.zhen.zayta.main.sokoban_OOP.Puzzle;

public class PlayScreen extends ScreenAdapter {
    private Game game;
    private UserData userData;

    // == attributes ==

    private PlayController controller;
    private PlayRenderer renderer;
    private snow.zhen.zayta.main.sokoban.map.Map map;
    private Hud hud;
    private InputMultiplexer inputMultiplexer;
    private int curLvl =0;
    private final AssetManager assetManager;


    public PlayScreen(Game game){
        //gameplay data
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.userData = game.getUserData();

        this.map = new Map(assetManager.get(AssetDescriptors.GAMEPLAY));
        this.controller = new PlayController(map);
        this.hud = new Hud(controller,game);

        this.renderer = new PlayRenderer(game.getBatch(), assetManager, map,hud);
        setInput();

    }
    private void setInput(){
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(hud);

        if(Gdx.app.getType()== Application.ApplicationType.Desktop) { //if desktop
            inputMultiplexer.addProcessor(new KeyboardController(controller));
        }
        else {
            inputMultiplexer.addProcessor(new GestureDetector(new SwipeController(controller)));
        }
        Gdx.input.setInputProcessor(inputMultiplexer);
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
        controller.initLvl(curLvl);

    }
    @Override
    public void render(float delta) {
        controller.update(delta);

        hud.act(delta); //act the Hud
        renderer.render(delta);

        if(controller.isComplete())
            progress();
    }

    @Override
    public void resize(int width, int height) {
        GameConfig.configScreenOrientation(width,height);
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
        game.complete(curLvl);
    }

    /*For level management*/
    public void setLvl(int lvl){
        this.curLvl = lvl;
    }

}
