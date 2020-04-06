package snow.zhen.zayta.main.sokoban;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import snow.zhen.zayta.main.GameConfig;
import snow.zhen.zayta.main.assets.AssetDescriptors;
import snow.zhen.zayta.main.assets.RegionNames;
import snow.zhen.zayta.main.sokoban.entity.units.Crate;
import snow.zhen.zayta.main.sokoban.entity.units.Goal;
import snow.zhen.zayta.main.sokoban.entity.units.Nighter;
import snow.zhen.zayta.main.sokoban.entity.units.Wall;
import snow.zhen.zayta.main.sokoban.input.Hud;
import snow.zhen.zayta.util.GdxUtils;
import snow.zhen.zayta.util.ViewportUtils;
import snow.zhen.zayta.main.debug.DebugCameraController;
import snow.zhen.zayta.main.sokoban.entity.EntityBase;
import snow.zhen.zayta.main.sokoban.map.Map;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position;

import static snow.zhen.zayta.main.GameConfig.CHARACTER_RENDER_WIDTH;
import static snow.zhen.zayta.main.GameConfig.CHARACTER_RENDER_OFFSET;
import static snow.zhen.zayta.main.GameConfig.ENTITY_SIZE;
import static snow.zhen.zayta.main.GameConfig.VIRTUAL_HEIGHT;
import static snow.zhen.zayta.main.GameConfig.VIRTUAL_WIDTH;

public class PlayRenderer {


    // == attributes ==
    private final AssetManager assetManager;
    //camera/viewport
    private OrthographicCamera camera;
    private Viewport viewport;
//    private Viewport hudViewport;
    private snow.zhen.zayta.main.debug.DebugCameraController debugCameraController;
//    private boolean cameraShouldMove;

    //for render/drawing
    private ShapeRenderer renderer;
    private final SpriteBatch batch;

    private final GlyphLayout layout = new GlyphLayout();
//    private TextureRegion backgroundRegion;
    private TextureRegion floorRegion;
//    private TextureRegion borderRegion;

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

//        backgroundRegion = gamePlayAtlas.findRegion(RegionNames.SNOW);
        floorRegion = gamePlayAtlas.findRegions(RegionNames.VR_ROOM_P1).get(2);

//        borderRegion = gamePlayAtlas.findRegion(RegionNames.YELLOW);

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
        updateCamera(map);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        drawGamePlay();

        batch.end();
    }

    private void drawGamePlay() {
        //background
//        batch.draw(backgroundRegion,0,0,GameConfig.VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        //floor
//        for(int j = 0; j<VIRTUAL_HEIGHT;j++){
//            batch.draw(borderRegion,0,j,ENTITY_SIZE,ENTITY_SIZE);
//            batch.draw(borderRegion,VIRTUAL_WIDTH-1,j,ENTITY_SIZE,ENTITY_SIZE);
//        }
//
//        System.out.println("GameConfig Virtual width is "+VIRTUAL_WIDTH);
//        System.out.println("GameConfig Virtual height is "+VIRTUAL_HEIGHT);
        for(int i = 0; i<VIRTUAL_WIDTH;i++){
            for(int j = 0; j<VIRTUAL_HEIGHT;j++){
                batch.draw(floorRegion,i,j,ENTITY_SIZE,ENTITY_SIZE);
            }
        }
        drawEntities(map.getGoals(),ENTITY_SIZE,ENTITY_SIZE,0);
        drawEntities(map.getCrates(),ENTITY_SIZE,ENTITY_SIZE,0);
        drawEntities(map.getWalls(),ENTITY_SIZE,ENTITY_SIZE,0);
        drawEntities(map.getNighters(),CHARACTER_RENDER_WIDTH,ENTITY_SIZE,CHARACTER_RENDER_OFFSET);

    }
    private void drawEntities(ArrayList<? extends EntityBase>entities, float width, float height, float renderOffset){
        for(EntityBase entityBase: entities){
            batch.draw(entityBase.getTextureRegion(),entityBase.getX()+renderOffset,entityBase.getY(),width,height);
        }
    }
    private void renderHud(){
        hud.getViewport().apply();
        batch.setProjectionMatrix(hud.getCamera().combined);
        hud.draw(); //draw the Hud

    }

    private void updateCamera(Map map) {
        boolean cameraShouldMove =  (map.getMapWidth() > VIRTUAL_HEIGHT || map.getMapHeight() > VIRTUAL_HEIGHT);

        if (cameraShouldMove) {
            /*finds the avg player position*/
            int num = map.getNighters().size();
            float posX =0, posY =0;
            for(int i = 0; i<num;i++) {
                EntityBase player = map.getNighters().get(i);

                posX +=player.getX();
                posY +=player.getY();
            }
            posX/=num;
            posY/=num;
            //sets camera's new pos based on player's pos
            Vector3 newCameraPosition = new Vector3(posX,
                    posY, 0);
            camera.position.interpolate(newCameraPosition, 0.45f, Interpolation.exp10In);
        } else {
            camera.position.set(map.getMapWidth() * 0.5f, 1 - map.getMapHeight() * 0.5f, 0);
        }
    }

    public void resize(int width, int height) {

        viewport.setWorldSize(map.getMapWidth(),map.getMapHeight());
        viewport.update(width, height,true);



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