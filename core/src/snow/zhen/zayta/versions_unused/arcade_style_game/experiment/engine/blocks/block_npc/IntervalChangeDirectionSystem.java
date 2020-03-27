package snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.blocks.block_npc;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;

import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.labels.NPCTag;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent;

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
            MovementLimitationComponent movementLimitationComponent = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT_LIMITATION.get(entity);
            if(movementLimitationComponent!=null){
                movement.setDirection(Direction.generateDirectionExcluding(movementLimitationComponent.getBlockedDirection()));
            }
            else
                movement.setDirection(Direction.generateRandomDirection());

        }

    }


}
