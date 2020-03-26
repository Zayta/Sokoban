package exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.input.move_character.joystick;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.labels.PlayerTag;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent;

public class JoyStickMovementListener extends ChangeListener {
    private static final Logger log = new Logger(JoyStickMovementListener.class.getName(),Logger.DEBUG);

    private exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction direction= exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction.none;
    private final static Family PLAYABLE_CHARACTERS = Family.all(
            exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent.class, PlayerTag.class
    ).get();
    private PooledEngine engine;
    private ImmutableArray<Entity> entities;
    //todo later enable 8 way movement
    public JoyStickMovementListener(PooledEngine engine) {
        this.engine = engine;
    }


    @Override
    public void changed(ChangeEvent event, Actor actor) {
        // This is run when anything is changed on this actor.
        float deltaX = ((JoyStickMovementController) actor).getKnobPercentX();
        float deltaY = ((JoyStickMovementController) actor).getKnobPercentY();

        if(deltaX==0&&deltaY==0){
            updateAllPlayableCharacters(exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction.none);
            return;
        }

        if(Math.abs(deltaX)>Math.abs(deltaY)) {
            if (deltaX > 0)
                updateAllPlayableCharacters(exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction.right);
            else if (deltaX < 0)
                updateAllPlayableCharacters(exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction.left);
        }
        else{
            if (deltaY > 0)
                updateAllPlayableCharacters(exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction.up);
            else if (deltaY < 0)
                updateAllPlayableCharacters(exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction.down);
        }

    }

    /*changes all playable character's directoins*/
    /*changes all playable character's directoins*/
    private void updateAllPlayableCharacters(Direction direction){

        entities = engine.getEntitiesFor(PLAYABLE_CHARACTERS);
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            VelocityComponent movement = Mappers.MOVEMENT.get(entity);
            MovementLimitationComponent movementLimitationComponent = Mappers.MOVEMENT_LIMITATION.get(entity);

            if(movement.getDirection()==direction||movementLimitationComponent.getBlockedDirection()==direction)
                continue;
            movement.setDirection(direction);
//            MovementLimitationComponent movementLimitation = Mappers.MOVEMENT_LIMITATION.get(entity);
//          if(movementLimitation!=null)
//            if(direction!=movementLimitation.getBlockedDirection()||direction==Direction.none)
//                movement.setDirection(direction);
//            else
//                movement.setDirection(Direction.none);

        }
    }
//    /*changes all playable character's directoins*/
//    private void updateAllPlayableCharacters(Direction direction){
//        entities = engine.getEntitiesFor(playableCharacters);
//        for (int i = 0; i < entities.size(); ++i) {
//            Entity entity = entities.get(i);
//            VelocityComponent movement = Mappers.MOVEMENT.get(entity);
//            movement.setDirection(direction);
//        }
//    }
}
