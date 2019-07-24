package exp.zhen.zayta.main.game.wake.game_mechanics.movable_items;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.game_mechanics.movable_items.components.MovableTag;
import exp.zhen.zayta.main.game.wake.game_mechanics.movable_items.components.NonAutoMotionComponent;
import exp.zhen.zayta.main.game.wake.game_mechanics.movable_items.components.PushComponent;
import exp.zhen.zayta.main.game.wake.movement.component.VelocityComponent;

//updates pocket direction to be the same as movment direction, if entity has movement direction.
public class UpdatePushDirectionSystem extends IteratingSystem {

    private static final Logger log = new Logger(UpdatePushDirectionSystem.class.getName(),Logger.DEBUG);
    public UpdatePushDirectionSystem() {
        super(
                Family.all(
                        VelocityComponent.class,
//                        PushComponent.class,
//                        NonAutoMotionComponent.class,
                        MovableTag.class
                ).get()
        );
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
//        log.debug("num of updater entities"+getEngine().getEntities().size());
        Mappers.MOVEMENT.get(entity).setDirection(
                Mappers.ITEM_SHOVE.get(entity).getDirection()
        );

    }
}
