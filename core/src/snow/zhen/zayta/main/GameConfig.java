package snow.zhen.zayta.main;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import snow.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionTracker;

public class GameConfig {
    public static float WIDTH = 1024f; // pixels
    public static float HEIGHT = 720f; // pixels
    //todo might need to adjust tile size
    public static final int TILE_SIZE = 64;

    public static final float MOVING_SPEED = 10f;


    //for puzzle
    private static final int VIRTUAL_LONG = 14;
    private static final int VIRTUAL_SHORT = 9;
    public static int VIRTUAL_WIDTH=VIRTUAL_LONG;
    public static int VIRTUAL_HEIGHT=VIRTUAL_SHORT;

    public static final int ENTITY_SIZE = 1;
    public static final float CHARACTER_RENDER_WIDTH = ENTITY_SIZE*0.7f;
    public static final float CHARACTER_RENDER_OFFSET = (ENTITY_SIZE-CHARACTER_RENDER_WIDTH)/2;
    public static float VIRTUAL_CENTER_X = VIRTUAL_WIDTH/2f;
    public static float VIRTUAL_CENTER_Y = VIRTUAL_HEIGHT/2f;

    public static final float SCALE = 1f*2f/(TILE_SIZE);

    //hud
    public static float HUD_WIDTH = WIDTH; // world units
    public static float HUD_HEIGHT = HEIGHT; // world units
    public static final float PADDING = 15f;
    public static final int BTN_SIZE = 128;
    public static final int BTN_SMALL_SIZE = 88;

    public static final float JOYSTICK_RADIUS = 20;
    public static final Color DARK_TINT = new Color(0.7f,0.7f,0.7f,0.9f);

    public static void configScreenOrientation(int screenWidth,int screenHeight){

        if(screenWidth>screenHeight)
            configLandScape();
        else
            configPortrait();

    }
    private static void configLandScape(){
        //////log.debug("configLandscape");
        WIDTH = 1024f; // pixels
        HEIGHT = 720f; // pixels

        HUD_WIDTH = WIDTH; // world units
        HUD_HEIGHT = HEIGHT; // world units

        VIRTUAL_WIDTH = VIRTUAL_LONG; // world units
        VIRTUAL_HEIGHT = VIRTUAL_SHORT; // world units

        VIRTUAL_CENTER_X = VIRTUAL_WIDTH / 2f; // world units
        VIRTUAL_CENTER_Y = VIRTUAL_HEIGHT / 2f; // world units

    }


    private static void configPortrait(){
        //////log.debug("configPortrait");
        WIDTH = 720f; // pixels
        HEIGHT = 1024f; // pixels

        HUD_WIDTH = WIDTH; // world units
        HUD_HEIGHT = HEIGHT; // world units

        VIRTUAL_HEIGHT = VIRTUAL_LONG; // world units
        VIRTUAL_WIDTH = VIRTUAL_SHORT; // world units

        VIRTUAL_CENTER_X = VIRTUAL_WIDTH / 2f; // world units
        VIRTUAL_CENTER_Y = VIRTUAL_HEIGHT / 2f; // world units

     }



}
