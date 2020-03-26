package exp.zhen.zayta.versions_unused.game_mechanics;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;

import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.labels.NPCTag;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.HealthComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.PositionTrackerComponent;

public class NPCReaperSystem extends EntitySystem {
    private final static Family NPC = Family.all(
            HealthComponent.class,NPCTag.class,
            PositionTrackerComponent.class
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
                PositionTrackerComponent positionTrackerComponent = Mappers.POSITION_TRACKER.get(npc);
                positionTrackerComponent.getPositionKeyListMap().remove(npc);
                getEngine().removeEntity(npc);

            }
        }

    }
}
