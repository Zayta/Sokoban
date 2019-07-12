package exp.zhen.zayta.main.game.wake.game_mechanics;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;

import exp.zhen.zayta.RPG;
import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.entity.components.labels.NPCTag;
import exp.zhen.zayta.main.game.wake.entity.components.labels.PlayerTag;
import exp.zhen.zayta.main.game.wake.entity.components.properties.HealthComponent;

public class NPCReaperSystem extends EntitySystem {
    private final static Family NPC = Family.all(
            HealthComponent.class,NPCTag.class
    ).get();
    private ImmutableArray<Entity> entities;
    //todo set next level if all stones are gathered, set game over if player loses all hp
    public NPCReaperSystem(PooledEngine engine) {
        entities = engine.getEntitiesFor(NPC);
    }



    @Override
    public void update(float deltaTime) {
        for(Entity npc:entities){ //if all playable characters have no hp, player failed mission.
            HealthComponent healthComponent = (Mappers.HEALTH.get(npc));
            if(healthComponent.getHp()<=0){
                getEngine().removeEntity(npc);
            }
        }

    }
}
