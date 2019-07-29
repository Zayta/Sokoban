package exp.zhen.zayta.main.game.wake.input.input_listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.wake.movement.Direction;
import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.movement.PositionTracker;
import exp.zhen.zayta.main.game.wake.entity.components.labels.PlayerTag;
import exp.zhen.zayta.main.game.wake.movement.component.VelocityComponent;

public class KeyboardInputHandler extends InputAdapter {
    private static final Logger log = new Logger(KeyboardInputHandler.class.getName(),Logger.DEBUG);

    private Direction direction=Direction.none;
    private final static Family playableCharacters = Family.all(
            VelocityComponent.class,PlayerTag.class
    ).get();
    private PooledEngine engine;
    private ImmutableArray<Entity> entities;

    public KeyboardInputHandler(PooledEngine engine) {
        this.engine = engine;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode==Input.Keys.LEFT){
            updateAllPlayableCharacters(Direction.left);
        }
        else if(keycode==Input.Keys.RIGHT){
            updateAllPlayableCharacters(Direction.right);
        }
        else if(keycode==Input.Keys.UP){
            updateAllPlayableCharacters(Direction.up);
        }
        else if(keycode==Input.Keys.DOWN){
            updateAllPlayableCharacters(Direction.down);
        }
        else if(keycode==Input.Keys.ENTER)
        {
            log.debug("NIGHTERS"+PositionTracker.EntityPositionBiMap.nightersBiMap.getBiMap().toString());
//            log.debug("WIELDERS"+PositionTracker.EntityPositionBiMap.wieldersBiMap.getBiMap().toString());
//            log.debug("CIVILIANS"+PositionTracker.EntityPositionBiMap.civiliansBiMap.getBiMap().toString());
//            log.debug("MONSTERS"+PositionTracker.EntityPositionBiMap.monstersBiMap.getBiMap().toString());
        }
//        else direction = Direction.none; //comment this out for reset direction effect if anotehr key is pressed
        return true;
    }

    @Override
    public boolean keyUp(int keycode) { //commment this out for non-continuous movement of character
        updateAllPlayableCharacters(Direction.none);
        return true;
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
