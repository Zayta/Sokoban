package exp.zhen.zayta.main;

public class GameConfig {
    public static final float WIDTH = 1024f; // pixels
    public static final float HEIGHT = 720f; // pixels
    //todo might need to adjust tile size
    public static final int TILE_SIZE = 64;

    public static final float MOVING_SPEED = 10f;


    //for puzzle
    public static final int VIRTUAL_WIDTH = 14;
    public static final int VIRTUAL_HEIGHT = 9;

    public static final int ENTITY_SIZE = 1;
    public static final float VIRTUAL_CENTER_X = VIRTUAL_WIDTH/2f;
    public static final float VIRTUAL_CENTER_Y = VIRTUAL_HEIGHT/2f;

    public static final float SCALE = 1f*2f/(TILE_SIZE);

    public static final float HUD_WIDTH = WIDTH; // world units
    public static final float HUD_HEIGHT = HEIGHT; // world units


}
