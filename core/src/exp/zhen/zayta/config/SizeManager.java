package exp.zhen.zayta.config;


public class SizeManager {

    //world
    public static final float WIDTH = 512f; // pixels
    public static final float HEIGHT = 1024f; // pixels

    public static final float HUD_WIDTH = 512f; // world units
    public static final float HUD_HEIGHT = 1024f; // world units

    public static final float WORLD_WIDTH = 6.0f; // world units
    public static final float WORLD_HEIGHT = 10.0f; // world units


    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2f; // world units
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2f; // world units

    public static final float maxBoundsRadius = 0.3f;
    public static final float maxObjWidth = 2*maxBoundsRadius;//world units
    public static final float maxObjHeight = 0.8f;
//    //sprites
//    public static final float maxBoundsRadius = 0.4f; // world units
//    public static final float maxObjHeight = 0.9f;
//    public static final float maxObjWidth = 2*maxBoundsRadius;

//    //stone
//    public static final float STONE_BOUNDS_RADIUS = 0.3f;
//    public static final float STONE_SIZE = 2*STONE_BOUNDS_RADIUS;

    private SizeManager() {}
}
