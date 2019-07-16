package exp.zhen.zayta.main.game.wake.movement.component;

import com.badlogic.ashley.core.Component;

import exp.zhen.zayta.main.game.wake.movement.Direction;


public class MovementLimitationComponent implements Component {

    private int ghostificationLvl = 0;
    private Direction blockedDirection = Direction.none;

    public Direction getBlockedDirection() {
        return blockedDirection;
    }

    public void setBlockedDirection(Direction blockedDirection) {
        this.blockedDirection = blockedDirection;
    }

    public int getGhostificationLvl() {
        return ghostificationLvl;
    }

    public void setGhostificationLvl(int ghostificationLvl) {
        this.ghostificationLvl = ghostificationLvl;
    }
}
