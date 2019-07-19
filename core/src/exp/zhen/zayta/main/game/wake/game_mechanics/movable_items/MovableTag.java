package exp.zhen.zayta.main.game.wake.game_mechanics.movable_items;

import com.badlogic.ashley.core.Component;

import exp.zhen.zayta.main.game.wake.movement.Direction;

public class MovableTag implements Component {
    private boolean beingPushed = false;
    private Direction direction = Direction.none;

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
