package exp.zhen.zayta.main.game.wake.movement.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import exp.zhen.zayta.main.game.wake.movement.Direction;


public class MovementLimitationComponent implements Component {

    private int ghostificationLvl = 0;
    private Direction blockedDirection = Direction.none;
    private Entity currentBlock;

    public Direction getBlockedDirection() {
        return blockedDirection;
    }

    public void setBlock(Entity currentBlock,Direction blockedDirection) {
        this.currentBlock = currentBlock;
        this.blockedDirection = blockedDirection;
    }

    public int getGhostificationLvl() {
        return ghostificationLvl;
    }

    public void setGhostificationLvl(int ghostificationLvl) {
        this.ghostificationLvl = ghostificationLvl;
    }

    public Entity getCurrentBlock() {
        return currentBlock;
    }

}
