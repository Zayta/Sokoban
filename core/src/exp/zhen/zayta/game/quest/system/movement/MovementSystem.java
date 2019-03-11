package exp.zhen.zayta.game.quest.system.movement;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import exp.zhen.zayta.common.Mappers;
import exp.zhen.zayta.game.quest.component.properties.movement.VelocityComponent;
import exp.zhen.zayta.game.quest.component.properties.movement.Position;

public class MovementSystem extends EntitySystem {

    private final Family MOVING_ENTITIES;
    public MovementSystem()
    {
        MOVING_ENTITIES = Family.all(Position.class,VelocityComponent.class).get();
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
        position.update(movement.getVelX(), movement.getVelY());
    }

}
