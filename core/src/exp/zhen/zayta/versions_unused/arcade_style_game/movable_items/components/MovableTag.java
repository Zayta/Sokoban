package exp.zhen.zayta.versions_unused.arcade_style_game.movable_items.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction;

public class MovableTag implements Component,Pool.Poolable {
    private boolean beingPushed = false;
    private exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction direction = exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction.none;
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

    public exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction getDirection() {
        return direction;
    }

    public void setDirection(exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction direction) {
        this.direction = direction;
    }

    @Override
    public void reset() {
        beingPushed = false;
        direction = Direction.none;
        boundsOfMovement.set(0,0,0,0);
    }
}
