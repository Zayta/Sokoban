package snow.zhen.zayta.main.sokoban.entity;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/*
* Contains position and bounds of an entity
* */
public abstract class EntityBase{

    // == attributes ==
    protected Vector2 position;
    protected float width = 1;
    protected float height = 1;

    protected Rectangle bounds;

    // == constructors ==
    public EntityBase(){//nighters use this
        position = new Vector2();
        bounds = new Rectangle(0,0,width,height);
    }
    public EntityBase(float x, float y) {//gameplay obj use this
        position = new Vector2(x,y);
        bounds = new Rectangle(x, y, width, height);
    }

    // == abstract methods ==
    public abstract TextureRegion getTextureRegion();
    public abstract boolean is(snow.zhen.zayta.main.sokoban.entity.EntityType entityType);
    public abstract EntityType getEntityType();

    // == public methods ==
    public void setPosition(float x, float y) {
        position.set(x,y);
        updateBounds();
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        updateBounds();
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public void setX(float x) {
        position.set(x,position.y);
        updateBounds();
    }

    public void setY(float y) {
        position.set(position.x,y);
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
        bounds.setPosition(position);
    }
}
