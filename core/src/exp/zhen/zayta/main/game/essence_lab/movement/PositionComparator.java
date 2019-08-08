package exp.zhen.zayta.main.game.essence_lab.movement;

import com.badlogic.ashley.core.Entity;

import java.util.Comparator;

import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.movement.component.Position;
import exp.zhen.zayta.main.game.essence_lab.movement.component.VelocityComponent;

public class PositionComparator implements Comparator<Entity> {
    @Override
    public int compare(Entity entity1, Entity entity2) {
        VelocityComponent velocityComponent1 = Mappers.MOVEMENT.get(entity1);
        if(velocityComponent1==null)
            return 1;
        VelocityComponent velocityComponent2 = Mappers.MOVEMENT.get(entity2);
        if(velocityComponent2==null)
            return -1;

        Direction direction1 = velocityComponent1.getDirection();
        Direction direction2 = velocityComponent2.getDirection();
        if(direction1!=direction2){
            return direction1.ordinal()-direction2.ordinal();
        }
        Position position1 = Mappers.POSITION.get(entity1);
        Position position2 = Mappers.POSITION.get(entity2);
        int scalar =100;
        //else same direction
        //todo not sure about ascending vs descending for comparing positions
        switch (direction1){
            case up://descendingorder
                return (int)(scalar*position1.getY())-(int)(scalar*position2.getY());
            case down://ascendingorder so lower posY gets taken care of first
                return (int)(scalar*position2.getY())-(int)(scalar*position1.getY());
            case left:
                return (int)(scalar*position2.getX())-(int)(scalar*position1.getX());
            case right:
                return (int)(scalar*position1.getX())-(int)(scalar*position2.getX());

        }

        return 0;
    }
}
