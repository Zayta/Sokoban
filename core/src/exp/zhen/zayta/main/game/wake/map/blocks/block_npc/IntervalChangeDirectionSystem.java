package exp.zhen.zayta.main.game.wake.map.blocks.block_npc;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;

import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.entity.components.labels.NPCTag;
import exp.zhen.zayta.main.game.wake.movement.Direction;
import exp.zhen.zayta.main.game.wake.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.wake.movement.component.Position;
import exp.zhen.zayta.main.game.wake.movement.component.VelocityComponent;
import exp.zhen.zayta.main.game.wake.movement.component.WorldWrapTag;

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
