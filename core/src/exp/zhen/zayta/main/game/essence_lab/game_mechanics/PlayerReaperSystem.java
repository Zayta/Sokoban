package exp.zhen.zayta.main.game.essence_lab.game_mechanics;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;

import exp.zhen.zayta.RPG;
import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.entity.components.labels.PlayerTag;
import exp.zhen.zayta.main.game.essence_lab.entity.components.properties.HealthComponent;

public class PlayerReaperSystem extends GameControllingSystem {
    private final static Family PLAYABLE_CHARACTERS = Family.all(
            HealthComponent.class,PlayerTag.class
    ).get();
    private ImmutableArray<Entity> entities;

    //todo set next level if all stones are gathered, set game over if player loses all hp
    public PlayerReaperSystem(RPG game, PooledEngine engine) {
        super(game,engine);
        entities = engine.getEntitiesFor(PLAYABLE_CHARACTERS);
    }



    @Override
    public void update(float deltaTime) {
        boolean failed = true;
        for(Entity playable_character:entities){ //if all playable characters have no hp, player failed mission.
            failed = failed && (Mappers.HEALTH.get(playable_character).getHp()<=0);
        }
        if(failed){
            setGameOver();
        }

    }

    @Override
    public void reset() {

    }
}
