package exp.zhen.zayta.main.game.config;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.wake.movement.PositionTracker;


public class SizeManager {

    private static final Logger log = new Logger(SizeManager.class.getName(),Logger.DEBUG);

    private static int screenWidth = Gdx.graphics.getWidth(),
            screenHeight = Gdx.graphics.getHeight();
    public static float WIDTH,HEIGHT;
    public static float HUD_WIDTH,HUD_HEIGHT; // world units
    public static float WAKE_WORLD_WIDTH,WAKE_WORLD_HEIGHT; // world units
    public static float WAKE_WORLD_CENTER_X,WAKE_WORLD_CENTER_Y; // world units
    public static float maxBoundsRadius, maxObjWidth, maxObjHeight;
    public static final float GAME_TO_CONTROL_RATIO =Gdx.app.getType()==Application.ApplicationType.Android || Gdx.app.getType()==Application.ApplicationType.iOS? 0.8f:1f;
    public static int CONTROLLER_DIAMETER;
//    public static int numObjPerRow = (int)(SizeManager.WAKE_WORLD_WIDTH/SizeManager.maxObjWidth);



    //todo problem: users see dif content depending on whether they have device landscape or portrait.

    public static void config(int screenWidth,int screenHeight){

        maxBoundsRadius = 0.2f;
        maxObjWidth = 0.75f;//world units
        maxObjHeight = 0.9f;
//        maxBoundsRadius = 0.5f;
//        maxObjWidth = 1f;
//        maxObjHeight = 1f;
        if(screenWidth>screenHeight)
            configLandScape();
        else
            configPortrait();

//        numObjPerRow = (int)(SizeManager.WAKE_WORLD_WIDTH/SizeManager.maxObjWidth);
//         must be after configs
        PositionTracker.n = (int)(SizeManager.WAKE_WORLD_WIDTH/SizeManager.maxObjWidth);
    }

    private static void configLandScape(){
        log.debug("configLandscape");
        WIDTH = 1024f; // pixels
        HEIGHT = 720f; // pixels

        HUD_WIDTH = WIDTH; // world units
        HUD_HEIGHT = HEIGHT; // world units

        WAKE_WORLD_WIDTH = 10.0f; // world units
        WAKE_WORLD_HEIGHT = WAKE_WORLD_WIDTH*HEIGHT/WIDTH; // world units

        WAKE_WORLD_CENTER_X = WAKE_WORLD_WIDTH / 2f; // world units
        WAKE_WORLD_CENTER_Y = WAKE_WORLD_HEIGHT / 2f; // world units

        CONTROLLER_DIAMETER = screenWidth < screenHeight ?
                (int)((1-GAME_TO_CONTROL_RATIO)*HEIGHT):(int)((1-GAME_TO_CONTROL_RATIO)*WIDTH);
    }


    private static void configPortrait(){
        log.debug("configPortrait");
        WIDTH = 720f; // pixels
        HEIGHT = 1024f; // pixels

        HUD_WIDTH = WIDTH; // world units
        HUD_HEIGHT = HEIGHT; // world units

        WAKE_WORLD_HEIGHT = 10.0f; // world units
        WAKE_WORLD_WIDTH = WAKE_WORLD_HEIGHT*WIDTH/HEIGHT; // world units

        WAKE_WORLD_CENTER_X = WAKE_WORLD_WIDTH / 2f; // world units
        WAKE_WORLD_CENTER_Y = WAKE_WORLD_HEIGHT / 2f; // world units

        CONTROLLER_DIAMETER = Gdx.app.getType()==Application.ApplicationType.Android || Gdx.app.getType()==Application.ApplicationType.iOS? (int)((1-GAME_TO_CONTROL_RATIO)*HEIGHT):0;
    }




    public static  float CQ_WORLD_WIDTH = WIDTH/100; // world units
    public static  float CQ_WORLD_HEIGHT = HEIGHT/100; // world units

    public static  float CQ_WORLD_CENTER_X = CQ_WORLD_WIDTH / 2f; // world units
    public static  float CQ_WORLD_CENTER_Y = CQ_WORLD_HEIGHT / 2f; // world units


//    //sprites
//    public static  float maxBoundsRadius = 0.4f; // world units
//    public static  float maxObjHeight = 0.9f;
//    public static  float maxObjWidth = 2*maxBoundsRadius;

//    //stone
//    public static  float STONE_BOUNDS_RADIUS = 0.3f;
//    public static  float STONE_SIZE = 2*STONE_BOUNDS_RADIUS;

    private SizeManager() {}
//    //world
//    private static int screenWidth = Gdx.graphics.getWidth();
//    private static int screenHeight = Gdx.graphics.getHeight();
//
//    public static float WIDTH = screenWidth < screenHeight ? 720f : 1024f; // pixels
//    public static float HEIGHT = screenWidth < screenHeight ? 1024f : 720f; // pixels
//
//    public static float HUD_WIDTH = WIDTH; // world units
//    public static float HUD_HEIGHT = HEIGHT; // world units
//
//    public static float WAKE_WORLD_WIDTH = 10.0f; // world units
//    public static float WAKE_WORLD_HEIGHT = WAKE_WORLD_WIDTH*HEIGHT/WIDTH; // world units
//
//    public static float WAKE_WORLD_CENTER_X = WAKE_WORLD_WIDTH / 2f; // world units
//    public static float WAKE_WORLD_CENTER_Y = WAKE_WORLD_HEIGHT / 2f; // world units
//
//    public static float maxBoundsRadius = 0.4f;
//    public static float maxObjWidth = 2*maxBoundsRadius;//world units
//    public static float maxObjHeight = 1.0f;
//
//
//    public static final float GAME_TO_CONTROL_RATIO =Gdx.app.getType()==Application.ApplicationType.Android || Gdx.app.getType()==Application.ApplicationType.iOS? 0.8f:1f;
//
//    public static int CONTROLLER_DIAMETER = screenWidth < screenHeight ?
//            (int)((1-GAME_TO_CONTROL_RATIO)*HEIGHT):(int)((1-GAME_TO_CONTROL_RATIO)*WIDTH);
//
}
