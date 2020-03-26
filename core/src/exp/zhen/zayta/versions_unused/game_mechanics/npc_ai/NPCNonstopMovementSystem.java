package exp.zhen.zayta.versions_unused.game_mechanics.npc_ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.AutoMovementTag;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent;

public class NPCNonstopMovementSystem extends IteratingSystem {
private static final Logger log = new Logger(NPCNonstopMovementSystem.class.getName(),Logger.DEBUG);
    private static Family CONSTANT_MOVERS = Family.all(

            AutoMovementTag.class,
            VelocityComponent.class,
            MovementLimitationComponent.class
    ).get();


    public NPCNonstopMovementSystem() {
        super(CONSTANT_MOVERS);
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        VelocityComponent movement = Mappers.MOVEMENT.get(entity);
        if(movement.getDirection()==Direction.none){
            //todo be careful of way of generate D
//            movement.setDirection(movement.getPrevDirection().getOpposite());


            MovementLimitationComponent movementLimitationComponent=Mappers.MOVEMENT_LIMITATION.get(entity);
//            Direction generatedDirection = Direction.none;
//            if(movement.getPrevDirection()==Direction.up||movement.getPrevDirection()==Direction.down)
//            {
//                generatedDirection = Direction.generateHorizontalDirection();
//            }
//            else {
//                generatedDirection = Direction.generateVerticalDirection();
//            }
//            movement.setDirection(generatedDirection);

            movement.setDirection(Direction.generateDirectionExcluding(movementLimitationComponent.getBlockedDirection()));
//            movement.setDirection(movement.getPrevDirection().getOpposite());
        }
    }
}
