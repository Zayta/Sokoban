package exp.zhen.zayta.main;

import com.badlogic.gdx.Screen;

import exp.zhen.zayta.main.assets.AssetDescriptors;
import exp.zhen.zayta.main.sokoban.Puzzle;

public class PlayScreen implements Screen {
    private Game game;
    private Puzzle puzzle;
    public PlayScreen(Game game){
        this.game = game;
        this.puzzle = new Puzzle(game.getAssetManager().get(AssetDescriptors.SOKOBAN),game.getBatch());

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    /****For screen transition*****/


    public void progress(){
        game.unlockScene();
    }
    public void fail(){
        reset();
        game.stop();
    }

}
