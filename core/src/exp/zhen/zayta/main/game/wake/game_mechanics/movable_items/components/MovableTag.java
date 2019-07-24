package exp.zhen.zayta.main.game.wake.game_mechanics.movable_items.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

import exp.zhen.zayta.main.game.wake.movement.Direction;

public class MovableTag implements Component {
    private boolean beingPushed = false;
    private Direction direction = Direction.none;
    private Rectangle boundsOfMovement = new Rectangle();

    public void setBoundsOfMovement(float left, float bottom, float width, float height) {
        boundsOfMovement.set(left,bottom,width,height);
    }

    public boolean withinBoundsOfMovement(Rectangle entityBounds){
        return boundsOfMovement.contains(entityBounds);
    }

    public boolean isBeingPushed() {
        return beingPushed;
    }

    public void setBeingPushed(boolean beingPushed) {
        this.beingPushed = beingPushed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
