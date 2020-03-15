package exp.zhen.zayta.main.arcade_style_game.experiment.engine.movement.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import exp.zhen.zayta.main.arcade_style_game.experiment.common.Mappers;
import exp.zhen.zayta.main.arcade_style_game.movable_items.components.NonAutoMotionComponent;
import exp.zhen.zayta.main.arcade_style_game.experiment.engine.movement.component.VelocityComponent;
import exp.zhen.zayta.main.arcade_style_game.experiment.engine.movement.component.Position;

public class MovementSystem extends EntitySystem {

    private final Family MOVING_ENTITIES;
    public MovementSystem()
    {
        MOVING_ENTITIES = Family.all(Position.class,VelocityComponent.class).exclude(NonAutoMotionComponent.class).get();//remove the .exclude part to make moveable blocks auto-move
    }

    @Override
    public void update(float deltaTime) {

        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(MOVING_ENTITIES);
        for(Entity entity:entities) {
            move(entity);
        }
    }


    private void move(Entity entity) {
        Position position = Mappers.POSITION.get(entity);
        VelocityComponent movement = Mappers.MOVEMENT.get(entity);
//        MovementLimitationComponent movementLimitationComponent = Mappers.MOVEMENT_LIMITATION.get(entity);
//        if(movementLimitationComponent!=null)
//            if(movement.getDirection()==movementLimitationComponent.getBlockedDirection())
//                return;
        position.update(movement.getVelX(), movement.getVelY());
    }

}
