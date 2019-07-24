package exp.zhen.zayta.main.game.wake.movement.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import exp.zhen.zayta.main.game.wake.movement.Direction;


public class MovementLimitationComponent implements Component {

    private int ghostificationLvl = 0;
    private Direction blockedDirection = Direction.none;
    private Entity block;

    public Direction getBlockedDirection() {
        return blockedDirection;
    }

    public Entity getBlock() {
        return block;
    }
    public void setBlock(Entity block, Direction direction){
        this.block = block;
        this.blockedDirection = direction;
    }
    public void reset(){
        this.block = null;
        this.blockedDirection = Direction.none;
    }

    //    public void setBlockedDirection(Direction blockedDirection) {
//        this.blockedDirection = blockedDirection;
//    }

    public int getGhostificationLvl() {
        return ghostificationLvl;
    }

    public void setGhostificationLvl(int ghostificationLvl) {
        this.ghostificationLvl = ghostificationLvl;
    }
}
