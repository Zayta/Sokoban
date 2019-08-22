package exp.zhen.zayta.main.game.essence_lab.engine.movement.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

import exp.zhen.zayta.main.game.essence_lab.engine.movement.Direction;


public class MovementLimitationComponent implements Component,Pool.Poolable {

    private int ghostificationLvl = 0;
    private Direction blockedDirection = Direction.none;
    private Entity block; private float minX = 0, minY = 0, maxX, maxY;

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
    public void setBlock(float blockedCoordinate,Direction direction){
        switch (direction){
            case up:
                maxY = blockedCoordinate;
                break;
            case down:
                minY = blockedCoordinate;
                break;
            case left:
                minX = blockedCoordinate;
                break;
            case right:
                maxX = blockedCoordinate;
                break;
        }
    }
    @Override
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
