package exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent;

import static exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction.none;

public class MovementLimitationBoundClipSystem extends IteratingSystem {
    private static Family entities = Family.all(
            exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent.class,
            exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent.class,
            VelocityComponent.class,
            exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position.class).get();
    public MovementLimitationBoundClipSystem() {
        super(entities);
    }

    @Override
    protected void processEntity(Entity movingEntity, float deltaTime) {
        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent movementLimitationComponent = exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT_LIMITATION.get(movingEntity);
        if(movementLimitationComponent.getBlockedDirection()!= none) {
            Entity block = movementLimitationComponent.getBlock();
            if(block!=null){
                limitMovementByBlock(movingEntity,movementLimitationComponent);
            }
//            else{
//                limitByWorldWrap(movingEntity, movementLimitationComponent);
//            }


            exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT.get(movingEntity).setDirection(none);
        }
    
    }

    private void limitMovementByBlock(Entity movingEntity, MovementLimitationComponent movementLimitationComponent){
        RectangularBoundsComponent blockBounds = exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.RECTANGULAR_BOUNDS.get(
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
