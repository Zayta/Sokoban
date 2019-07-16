package exp.zhen.zayta.main.game.wake.game_mechanics.npc_ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.entity.components.labels.NPCTag;
import exp.zhen.zayta.main.game.wake.movement.Direction;
import exp.zhen.zayta.main.game.wake.movement.component.VelocityComponent;

public class NPCNonstopMovementSystem extends IteratingSystem {

    private static Family CONSTANT_MOVERS = Family.all(
            NPCTag.class,
            VelocityComponent.class
    ).get();

    public NPCNonstopMovementSystem() {
        super(CONSTANT_MOVERS);
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        VelocityComponent movement = Mappers.MOVEMENT.get(entity);
        if(movement.getDirection()==Direction.none){
            movement.setDirection(Direction.generateDirectionExcluding(movement.getPrevDirection()));
        }
    }
}
