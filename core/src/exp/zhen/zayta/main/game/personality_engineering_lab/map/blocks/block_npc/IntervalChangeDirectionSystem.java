package exp.zhen.zayta.main.game.personality_engineering_lab.map.blocks.block_npc;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;

import exp.zhen.zayta.main.game.personality_engineering_lab.common.Mappers;
import exp.zhen.zayta.main.game.personality_engineering_lab.entity.components.labels.NPCTag;
import exp.zhen.zayta.main.game.personality_engineering_lab.movement.Direction;
import exp.zhen.zayta.main.game.personality_engineering_lab.movement.component.VelocityComponent;

public class IntervalChangeDirectionSystem extends IntervalSystem {

    private static final Family FAMILY = Family.all(
            NPCTag.class,
//            MortalTag.class,
            VelocityComponent.class
    ).get();

    public IntervalChangeDirectionSystem(float interval) {
        super(interval);

    }
    @Override
    protected void updateInterval() {

        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(FAMILY);

        for(Entity entity: entities) {
            VelocityComponent movement = Mappers.MOVEMENT.get(entity);

//            movement.setDirection(Direction.generateDirectionExcluding(
//                    movement.getDirection()
//            )); //generates movement excluding current direction
            movement.setDirection(Direction.generateRandomDirection());

        }

    }


}
