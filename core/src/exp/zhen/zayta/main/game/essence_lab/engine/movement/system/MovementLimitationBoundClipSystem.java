package exp.zhen.zayta.main.game.essence_lab.engine.movement.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.Direction;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.Position;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.VelocityComponent;

public class MovementLimitationBoundClipSystem extends IteratingSystem {
    private static Family entities = Family.all(
            MovementLimitationComponent.class,
            RectangularBoundsComponent.class,
            VelocityComponent.class,
            Position.class).get();
    public MovementLimitationBoundClipSystem() {
        super(entities);
    }

    @Override
    protected void processEntity(Entity movingEntity, float deltaTime) {
        MovementLimitationComponent movementLimitationComponent = Mappers.MOVEMENT_LIMITATION.get(movingEntity);
        if(movementLimitationComponent.getBlockedDirection()!=Direction.none) {
            Entity block = movementLimitationComponent.getBlock();
            if(block!=null){
                limitMovementByBlock(movingEntity,movementLimitationComponent);
            }
//            else{
//                limitByWorldWrap(movingEntity, movementLimitationComponent);
//            }


            Mappers.MOVEMENT.get(movingEntity).setDirection(Direction.none);
        }
    
    }

    private void limitMovementByBlock(Entity movingEntity,MovementLimitationComponent movementLimitationComponent){
        RectangularBoundsComponent blockBounds = Mappers.RECTANGULAR_BOUNDS.get(
                movementLimitationComponent.getBlock());

        Position position = Mappers.POSITION.get(movingEntity);

        switch (movementLimitationComponent.getBlockedDirection()) {
            case up:
                position.setY(blockBounds.getBottom());
                break;
            case down://down and left are working ok
                position.setY(blockBounds.getTop());
                break;
            case left:
                position.setX(blockBounds.getRight());
                break;
            case right:
                position.setX(blockBounds.getLeft());
                break;
            case none:
                break;
        }
    }
    
}
