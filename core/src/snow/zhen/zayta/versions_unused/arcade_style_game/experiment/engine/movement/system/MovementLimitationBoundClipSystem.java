package snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent;

public class MovementLimitationBoundClipSystem extends IteratingSystem {
    private static Family entities = Family.all(
            snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent.class,
            snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent.class,
            VelocityComponent.class,
            snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position.class).get();
    public MovementLimitationBoundClipSystem() {
        super(entities);
    }

    @Override
    protected void processEntity(Entity movingEntity, float deltaTime) {
        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent movementLimitationComponent = Mappers.MOVEMENT_LIMITATION.get(movingEntity);
        if(movementLimitationComponent.getBlockedDirection()!= Direction.none) {
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

    private void limitMovementByBlock(Entity movingEntity, MovementLimitationComponent movementLimitationComponent){
        RectangularBoundsComponent blockBounds = Mappers.RECTANGULAR_BOUNDS.get(
                movementLimitationComponent.getBlock());

        Position position = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.POSITION.get(movingEntity);

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
