package exp.zhen.zayta.main.game.wake.input.joystick;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.entity.components.labels.PlayerTag;
import exp.zhen.zayta.main.game.wake.input.standard.controllers.KeyboardController;
import exp.zhen.zayta.main.game.wake.movement.Direction;
import exp.zhen.zayta.main.game.wake.movement.PositionTracker;
import exp.zhen.zayta.main.game.wake.movement.component.VelocityComponent;

public class JoyStickController extends ChangeListener {
    private static final Logger log = new Logger(JoyStickController.class.getName(),Logger.DEBUG);

    private Direction direction=Direction.none;
    private final static Family playableCharacters = Family.all(
            VelocityComponent.class,PlayerTag.class
    ).get();
    private PooledEngine engine;
    private ImmutableArray<Entity> entities;

    public JoyStickController(PooledEngine engine) {
        this.engine = engine;
    }


    @Override
    public void changed(ChangeEvent event, Actor actor) {
        // This is run when anything is changed on this actor.
        float deltaX = ((Touchpad) actor).getKnobPercentX();
        float deltaY = ((Touchpad) actor).getKnobPercentY();

        if(deltaX>0)
            updateAllPlayableCharacters(Direction.right);
        else if(deltaX<0)
            updateAllPlayableCharacters(Direction.left);

    }

    /*changes all playable character's directoins*/
    private void updateAllPlayableCharacters(Direction direction){
        entities = engine.getEntitiesFor(playableCharacters);
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            VelocityComponent movement = Mappers.MOVEMENT.get(entity);
            movement.setDirection(direction);
        }
    }
}
