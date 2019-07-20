package exp.zhen.zayta.main.game.wake.game_mechanics.movable_items;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.movement.component.VelocityComponent;

//updates pocket direction to be the same as movment direction, if entity has movement direction.
public class UpdatePushDirectionSystem extends IteratingSystem {
    public UpdatePushDirectionSystem() {
        super(
                Family.all(
                        VelocityComponent.class,
                        PushComponent.class
                ).get()
        );
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Mappers.POCKET.get(entity).setDirection(
                Mappers.MOVEMENT.get(entity).getDirection()
        );
    }
}
