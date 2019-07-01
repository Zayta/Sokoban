package exp.zhen.zayta.main.game.config;


import com.badlogic.gdx.Gdx;

public class SizeManager {

    //world
    private static int screenWidth = Gdx.graphics.getWidth();
    private static int screenHeight = Gdx.graphics.getHeight();

    public static final float WIDTH = screenWidth < screenHeight ? 512f : 1024f; // pixels
    public static final float HEIGHT = screenWidth < screenHeight ? 1024f : 512f; // pixels

    public static final float HUD_WIDTH = WIDTH; // world units
    public static final float HUD_HEIGHT = HEIGHT; // world units

    public static final float WAKE_WORLD_WIDTH = 10.0f; // world units
    public static final float WAKE_WORLD_HEIGHT = WAKE_WORLD_WIDTH*HEIGHT/WIDTH; // world units

    public static final float WAKE_WORLD_CENTER_X = WAKE_WORLD_WIDTH / 2f; // world units
    public static final float WAKE_WORLD_CENTER_Y = WAKE_WORLD_HEIGHT / 2f; // world units

    public static final float maxBoundsRadius = 0.4f;
    public static final float maxObjWidth = 2*maxBoundsRadius;//world units
    public static final float maxObjHeight = 1.0f;




    public static final float CQ_WORLD_WIDTH = WIDTH/100; // world units
    public static final float CQ_WORLD_HEIGHT = HEIGHT/100; // world units

    public static final float CQ_WORLD_CENTER_X = CQ_WORLD_WIDTH / 2f; // world units
    public static final float CQ_WORLD_CENTER_Y = CQ_WORLD_HEIGHT / 2f; // world units


//    //sprites
//    public static final float maxBoundsRadius = 0.4f; // world units
//    public static final float maxObjHeight = 0.9f;
//    public static final float maxObjWidth = 2*maxBoundsRadius;

//    //stone
//    public static final float STONE_BOUNDS_RADIUS = 0.3f;
//    public static final float STONE_SIZE = 2*STONE_BOUNDS_RADIUS;

    private SizeManager() {}

}
