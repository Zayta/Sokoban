package exp.zhen.zayta.main.sokoban_OOP;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

import exp.zhen.zayta.main.sokoban_OOP.objects.GameObject;

public class MapLayer {

    public static final int FLOOR   = 0;
    public static final int WALL    = 1;
    public static final int PLACE   = 2;
    public static final int CRATE   = 3;
    public static final int SOKOBAN = 4;

    private final List<GameObject> objects;

    public MapLayer() {
        this.objects = new ArrayList<GameObject>();
    }

    public void addObject(GameObject object) {
        objects.add(object);
    }

    public List<GameObject> getObjects() {
        return objects;
    }

    public void disposeObjects() {
        for (GameObject object : objects) {
            object.dispose();
        }
        objects.clear();
    }

    public void updateObjects(float delta) {
        for (GameObject object : objects) {
            object.update(delta);
        }
    }

    public void renderObjects(SpriteBatch batch, float scale) {
        for (GameObject object : objects) {
            object.render(batch, scale);
        }
    }
}