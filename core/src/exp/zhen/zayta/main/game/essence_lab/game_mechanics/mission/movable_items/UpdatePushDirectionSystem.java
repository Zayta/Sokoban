package exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.movable_items;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.movable_items.components.MovableTag;
import exp.zhen.zayta.main.game.essence_lab.movement.component.VelocityComponent;

//updates pocket direction to be the same as movment direction, if entity has movement direction.
public class UpdatePushDirectionSystem extends IteratingSystem {

    private static final Logger log = new Logger(UpdatePushDirectionSystem.class.getName(),Logger.DEBUG);
    public UpdatePushDirectionSystem() {
        super(
                Family.all(
                        VelocityComponent.class,
                        MovableTag.class
                ).get()
        );
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
//        //log.debug("num of updater entities"+getEngine().getEntities().size());
        Mappers.MOVEMENT.get(entity).setDirection(
                Mappers.ITEM_SHOVE.get(entity).getDirection()
        );

    }
}
