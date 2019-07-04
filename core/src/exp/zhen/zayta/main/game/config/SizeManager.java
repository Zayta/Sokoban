package exp.zhen.zayta.main.game.config;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Logger;


public class SizeManager {

    private static final Logger log = new Logger(SizeManager.class.getName(),Logger.DEBUG);
    //world
    private static int screenWidth = Gdx.graphics.getWidth();
    private static int screenHeight = Gdx.graphics.getHeight();

    public static float WIDTH = screenWidth < screenHeight ? 512f : 1024f; // pixels
    public static float HEIGHT = screenWidth < screenHeight ? 1024f : 512f; // pixels

    public static float HUD_WIDTH = WIDTH; // world units
    public static float HUD_HEIGHT = HEIGHT; // world units

    public static float WAKE_WORLD_WIDTH = 10.0f; // world units
    public static float WAKE_WORLD_HEIGHT = WAKE_WORLD_WIDTH*HEIGHT/WIDTH; // world units

    public static float WAKE_WORLD_CENTER_X = WAKE_WORLD_WIDTH / 2f; // world units
    public static float WAKE_WORLD_CENTER_Y = WAKE_WORLD_HEIGHT / 2f; // world units

    public static float maxBoundsRadius = 0.4f;
    public static float maxObjWidth = 2*maxBoundsRadius;//world units
    public static float maxObjHeight = 1.0f;


    public static final float GAME_TO_CONTROL_RATIO =Gdx.app.getType()==Application.ApplicationType.Android || Gdx.app.getType()==Application.ApplicationType.iOS? 0.75f:1f;
    public static int CONTROLLER_DIAMETER = screenWidth < screenHeight ?
            (int)((1-GAME_TO_CONTROL_RATIO)*HEIGHT):(int)((1-GAME_TO_CONTROL_RATIO)*WIDTH);



    public static void config(int screenWidth,int screenHeight){
        //todo problem: users see dif content depending on whether they have device landscape or portrait.
        if(screenWidth>screenHeight)
            configLandScape(screenWidth,screenHeight);
        else
            configPortrait(screenWidth,screenHeight);
//        WIDTH = screenWidth < screenHeight ? 512f : 1024f; // pixels
//        HEIGHT = screenWidth < screenHeight ? 1024f : 512f; // pixels
//
//        HUD_WIDTH = WIDTH; // world units
//        HUD_HEIGHT = HEIGHT; // world units
//
//        WAKE_WORLD_WIDTH = 10.0f; // world units
//        WAKE_WORLD_HEIGHT = WAKE_WORLD_WIDTH*HEIGHT/WIDTH; // world units
//
//        WAKE_WORLD_CENTER_X = WAKE_WORLD_WIDTH / 2f; // world units
//        WAKE_WORLD_CENTER_Y = WAKE_WORLD_HEIGHT / 2f; // world units
//
//        maxBoundsRadius = 0.4f;
//        maxObjWidth = 2*maxBoundsRadius;//world units
//        maxObjHeight = 1.0f;
//
//        CONTROLLER_DIAMETER = Gdx.app.getType()==Application.ApplicationType.Android || Gdx.app.getType()==Application.ApplicationType.iOS? (int)((1-GAME_TO_CONTROL_RATIO)*HEIGHT):0;

    }

    private static void configLandScape(int screenWidth, int screenHeight){
        log.debug("configLandscape");
        WIDTH = 1024f; // pixels
        HEIGHT = 512f; // pixels

        HUD_WIDTH = WIDTH; // world units
        HUD_HEIGHT = HEIGHT; // world units

        WAKE_WORLD_WIDTH = 10.0f; // world units
        WAKE_WORLD_HEIGHT = WAKE_WORLD_WIDTH*HEIGHT/WIDTH; // world units

        WAKE_WORLD_CENTER_X = WAKE_WORLD_WIDTH / 2f; // world units
        WAKE_WORLD_CENTER_Y = WAKE_WORLD_HEIGHT / 2f; // world units

        maxBoundsRadius = 0.4f;
        maxObjWidth = 2*maxBoundsRadius;//world units
        maxObjHeight = 1.0f;

        CONTROLLER_DIAMETER = Gdx.app.getType()==Application.ApplicationType.Android || Gdx.app.getType()==Application.ApplicationType.iOS? (int)((1-GAME_TO_CONTROL_RATIO)*WIDTH):0;
    }


    private static void configPortrait(int screenWidth, int screenHeight){
        log.debug("configPortrait");
        WIDTH = 512f; // pixels
        HEIGHT = 1024f; // pixels

        HUD_WIDTH = WIDTH; // world units
        HUD_HEIGHT = HEIGHT; // world units

        WAKE_WORLD_HEIGHT = 10.0f; // world units
        WAKE_WORLD_WIDTH = WAKE_WORLD_HEIGHT*WIDTH/HEIGHT; // world units

        WAKE_WORLD_CENTER_X = WAKE_WORLD_WIDTH / 2f; // world units
        WAKE_WORLD_CENTER_Y = WAKE_WORLD_HEIGHT / 2f; // world units

        maxBoundsRadius = 0.4f;
        maxObjWidth = 2*maxBoundsRadius;//world units
        maxObjHeight = 1.0f;

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

}
