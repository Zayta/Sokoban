package exp.zhen.zayta.main.sokoban;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.main.GameConfig;
import exp.zhen.zayta.main.arcade_style_game.debug.DebugCameraController;
import exp.zhen.zayta.main.assets.AssetDescriptors;
import exp.zhen.zayta.main.assets.RegionNames;
import exp.zhen.zayta.util.GdxUtils;
import exp.zhen.zayta.util.ViewportUtils;

import static exp.zhen.zayta.main.GameConfig.VIRTUAL_HEIGHT;

public class PlayRenderer {


    // == attributes ==
    private final SpriteBatch batch;
    private final AssetManager assetManager;
    private final PlayController controller;

    private OrthographicCamera camera;
    private Viewport viewport;
    private Viewport hudViewport;
    private ShapeRenderer renderer;

    private BitmapFont font;
    private final GlyphLayout layout = new GlyphLayout();

    private TextureRegion backgroundRegion;

    private DebugCameraController debugCameraController;

    private boolean cameraShouldMove;

    public PlayRenderer(SpriteBatch batch, AssetManager assetManager, PlayController controller) {
        this.batch = batch;
        this.assetManager = assetManager;
        this.controller = controller;
        init();
    }

    // == init ==
    private void init() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.VIRTUAL_WIDTH, GameConfig.VIRTUAL_HEIGHT, camera);
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        renderer = new ShapeRenderer();

        font = assetManager.get(AssetDescriptors.FONT);

        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.SOKOBAN);

        backgroundRegion = gamePlayAtlas.findRegion(RegionNames.SNOW);

        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.VIRTUAL_CENTER_X, GameConfig.VIRTUAL_CENTER_Y);
    }


    public void render(float delta) {
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

        GdxUtils.clearScreen();

        renderGamePlay();
        renderHud();

    }
    private void renderGamePlay(){
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        drawGamePlay();

        batch.end();
    }

    private void drawGamePlay() {
        // background
        batch.draw(backgroundRegion,
                0, 0,
                GameConfig.VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        //draw all game entities here, accessed via controller
//        // coin
//        Coin coin = controller.getCoin();
//        if (coin.isAvailable()) {
//            batch.draw(coinRegion,
//                    coin.getX(), coin.getY(),
//                    coin.getWidth(), coin.getHeight()
//            );
//        }
//
//        // snake
//        Snake snake = controller.getSnake();
//
//        // body parts
//        for (BodyPart bodyPart : snake.getBodyParts()) {
//            batch.draw(bodyRegion,
//                    bodyPart.getX(), bodyPart.getY(),
//                    bodyPart.getWidth(), bodyPart.getHeight()
//            );
//        }
//
//        // head
//        SnakeHead head = snake.getHead();
//        batch.draw(headRegion,
//                head.getX(), head.getY(),
//                head.getWidth(), head.getHeight()
//        );
    }
    private void renderHud(){
        
    }

//    private void updateCamera(Puzzle puzzle) {
//        if (cameraShouldMove) {
//            Vector2 playerPosition = puzzle.getPlayer().getPosition();
//            Vector3 newCameraPosition = new Vector3(playerPosition.x * SCALE,
//                    playerPosition.y * SCALE, 0);
//            camera.position.interpolate(newCameraPosition, 0.45f, Interpolation.exp10In);
//        } else {
//            camera.position.set(puzzle.getWidth() * 0.5f, 1-puzzle.getHeight() * 0.5f, 0);
//        }
//    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
        ViewportUtils.debugPixelsPerUnit(viewport);
        ViewportUtils.debugPixelsPerUnit(hudViewport);
    }
    public void dispose() {

        renderer.dispose();
    }

}