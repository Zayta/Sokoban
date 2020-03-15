package exp.zhen.zayta.main.sokoban.entity;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

/*
* Contains position and bounds of an entity
* */
public abstract class EntityBase{

    // == attributes ==
    protected float x;
    protected float y;

    protected float width = 1;
    protected float height = 1;

    protected Rectangle bounds;

    // == constructors ==
    public EntityBase() {
        bounds = new Rectangle(x, y, width, height);
    }

    // == public methods ==
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateBounds();
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        updateBounds();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
        updateBounds();
    }

    public void setY(float y) {
        this.y = y;
        updateBounds();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void updateBounds() {
        bounds.setPosition(x, y);
        bounds.setSize(width, height);
    }
}
