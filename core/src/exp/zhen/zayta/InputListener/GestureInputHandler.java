package exp.zhen.zayta.InputListener;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.input.GestureDetector;

import exp.zhen.zayta.game.quest.movement.Direction;
import exp.zhen.zayta.common.Mappers;
import exp.zhen.zayta.game.quest.component.labels.PlayerTag;
import exp.zhen.zayta.game.quest.movement.component.VelocityComponent;

public class GestureInputHandler extends GestureDetector.GestureAdapter {
    private Direction direction=Direction.none;
    private final static Family playableCharacters = Family.all(
            VelocityComponent.class,PlayerTag.class
    ).get();
    private PooledEngine engine;
    private ImmutableArray<Entity> entities;

    public GestureInputHandler(PooledEngine engine) {
        this.engine = engine;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        //velX and velY are in pixels

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
