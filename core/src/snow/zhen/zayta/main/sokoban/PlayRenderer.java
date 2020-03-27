package snow.zhen.zayta.main.sokoban;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import snow.zhen.zayta.main.GameConfig;
import snow.zhen.zayta.main.assets.AssetDescriptors;
import snow.zhen.zayta.main.assets.RegionNames;
import snow.zhen.zayta.main.sokoban.input.Hud;
import snow.zhen.zayta.util.GdxUtils;
import snow.zhen.zayta.util.ViewportUtils;
import snow.zhen.zayta.main.debug.DebugCameraController;
import snow.zhen.zayta.main.sokoban.entity.EntityBase;
import snow.zhen.zayta.main.sokoban.map.Map;

import static snow.zhen.zayta.main.GameConfig.ENTITY_SIZE;
import static snow.zhen.zayta.main.GameConfig.VIRTUAL_HEIGHT;

public class PlayRenderer {


    // == attributes ==
    private final AssetManager assetManager;
    //camera/viewport
    private OrthographicCamera camera;
    private Viewport viewport;
//    private Viewport hudViewport;
    private snow.zhen.zayta.main.debug.DebugCameraController debugCameraController;
    private boolean cameraShouldMove;

    //for render/drawing
    private ShapeRenderer renderer;
    private final SpriteBatch batch;

    private final GlyphLayout layout = new GlyphLayout();
    private TextureRegion backgroundRegion;

    //game-specific
    //    private final PlayController controller;
    private snow.zhen.zayta.main.sokoban.map.Map map;
    private Hud hud;


    public PlayRenderer(SpriteBatch batch, AssetManager assetManager, Map map, Hud hud) {
        this.batch = batch;
        this.assetManager = assetManager;
//        this.controller = controller;
        this.map = map;
        this.hud = hud;
        init();
    }

    // == init ==
    private void init() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.VIRTUAL_WIDTH, GameConfig.VIRTUAL_HEIGHT, camera);

        renderer = new ShapeRenderer();


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

        ArrayList<snow.zhen.zayta.main.sokoban.entity.EntityBase> entityBases = map.getEntities();
        for(EntityBase entityBase: entityBases)
        {
            batch.draw(entityBase.getTextureRegion(),entityBase.getX(),entityBase.getY(), ENTITY_SIZE,ENTITY_SIZE);
        }

    }
    private void renderHud(){
        hud.getViewport().apply();
        batch.setProjectionMatrix(hud.getCamera().combined);
        hud.draw(); //draw the Hud

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
        hud.resize(width,height);
        ViewportUtils.debugPixelsPerUnit(viewport);
        ViewportUtils.debugPixelsPerUnit(hud.getViewport());
    }
    public void dispose() {
        hud.dispose();
        renderer.dispose();
        batch.dispose();
    }

}