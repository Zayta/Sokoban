package exp.zhen.zayta.main.game.essence_lab.input.move_character;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.essence_lab.entity.components.labels.PlayerTag;
import exp.zhen.zayta.main.game.essence_lab.movement.Direction;
import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.movement.component.VelocityComponent;

public class FlingController extends GestureDetector.GestureAdapter {

    private static final Logger log = new Logger(FlingController.class.getName(),Logger.DEBUG);
    private Direction direction=Direction.none;
    private final static Family playableCharacters = Family.all(
            VelocityComponent.class,PlayerTag.class
    ).get();
    private PooledEngine engine;
    private ImmutableArray<Entity> entities;

    public FlingController(PooledEngine engine) {
        this.engine = engine;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        //velX and velY are in pixels
        //log.debug("fling occurred gestureinputhandler");
        if(Math.abs(velocityX)>Math.abs(velocityY)){
            if(velocityX>0){
                updateAllPlayableCharacters(Direction.right);
            }else {
                updateAllPlayableCharacters(Direction.left);
            }
        }else{
            if(velocityY>0) {
                updateAllPlayableCharacters(Direction.down);
            }
            else {
                updateAllPlayableCharacters(Direction.up);
            }
        }
        return false;//key! return false, else stage wont work.
    }

    /*changes all playable character's directoins*/
    private void updateAllPlayableCharacters(Direction direction){
        entities = engine.getEntitiesFor(playableCharacters);
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            VelocityComponent movement = Mappers.MOVEMENT.get(entity);
            if(movement.getDirection()==direction)
                continue;
            movement.setDirection(direction);
        }
    }
}
