package exp.zhen.zayta.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

import java.util.Random;


public class GdxUtils {

    public static final Random RANDOM = new Random();
    public static void clearScreen() {
        clearScreen(Color.BLACK);
    }

    public static void clearScreen(Color color) {
        // clear screen
        // DRY - Don't repeat yourself
        // WET - Waste everyone's time
        Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private GdxUtils() {}
}
