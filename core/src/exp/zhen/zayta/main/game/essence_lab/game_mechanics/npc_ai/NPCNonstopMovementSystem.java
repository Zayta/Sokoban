package exp.zhen.zayta.main.game.essence_lab.game_mechanics.npc_ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.entity.components.labels.NPCTag;
import exp.zhen.zayta.main.game.essence_lab.movement.Direction;
import exp.zhen.zayta.main.game.essence_lab.movement.component.AutoMovementTag;
import exp.zhen.zayta.main.game.essence_lab.movement.component.VelocityComponent;

public class NPCNonstopMovementSystem extends IteratingSystem {
private static final Logger log = new Logger(NPCNonstopMovementSystem.class.getName(),Logger.DEBUG);
    private static Family CONSTANT_MOVERS = Family.all(
            AutoMovementTag.class,
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
