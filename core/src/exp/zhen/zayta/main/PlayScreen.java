package exp.zhen.zayta.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.main.assets.AssetDescriptors;
import exp.zhen.zayta.main.sokoban.Puzzle;
import exp.zhen.zayta.main.sokoban.PuzzleRenderer;
import exp.zhen.zayta.util.GdxUtils;

public class PlayScreen implements Screen {
    private Game game;
    private Puzzle puzzle; private PuzzleRenderer renderer;
    private UserData userData; private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;
    
    public PlayScreen(Game game){
        //game data
        this.game = game;
        this.puzzle = new Puzzle(game.getAssetManager().get(AssetDescriptors.SOKOBAN));
        this.userData = game.getUserData();

        //render
        this.batch = game.getBatch();
        this.camera = new OrthographicCamera(SizeManager.VIRTUAL_WIDTH, SizeManager.VIRTUAL_HEIGHT);
        this.viewport = new ExtendViewport(SizeManager.VIRTUAL_WIDTH, SizeManager.VIRTUAL_HEIGHT, camera);
        this.renderer = new PuzzleRenderer(viewport,camera);
    }

    @Override
    public void show() {
        puzzle.loadLevel(userData.getNumScenesUnlocked());
    }

    @Override
    public void render(float delta) {
        viewport.apply(true);
        batch.setProjectionMatrix(camera.combined);
//        batch.disableBlending();
//        batch.begin();
//        batch.draw(Assets.getGameBackground(), 0, 0, getViewportWidth(), getViewportHeight());
//        batch.end();
        renderer.render(puzzle, batch);
//        hud.render(batch);
    }

    @Override
    public void resize(int width, int height) {
        Gdx.input.setCatchBackKey(true);
//        Gdx.input.setInputProcessor(this);
        viewport.update(width, height);
        viewport.apply();
        camera.update();
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
        game.stop();
    }

}
