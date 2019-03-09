package exp.zhen.zayta.mode.quest.system.movement;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;

import exp.zhen.zayta.common.Mappers;
import exp.zhen.zayta.mode.quest.PositionTracker;
import exp.zhen.zayta.mode.quest.component.labels.id.MonsterTag;
import exp.zhen.zayta.mode.quest.component.labels.id.MortalTag;
import exp.zhen.zayta.mode.quest.component.labels.id.NighterTag;
import exp.zhen.zayta.mode.quest.component.labels.id.WielderTag;
import exp.zhen.zayta.mode.quest.component.properties.movement.VelocityComponent;
import exp.zhen.zayta.mode.quest.component.properties.movement.Position;
import exp.zhen.zayta.mode.quest.entity.undead.nur.Nighter;
import exp.zhen.zayta.util.BiMap;

public class MovementSystem extends EntitySystem {

    private final Family MOVING_ENTITIES;
    public MovementSystem()
    {
        MOVING_ENTITIES = Family.all(Position.class,VelocityComponent.class).get();
    }

    @Override
    public void update(float deltaTime) {

        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(MOVING_ENTITIES);
        for(Entity entity:entities) {
            move(entity);
        }
    }


    private void move(Entity entity) {
        Position position = Mappers.POSITION.get(entity);
        VelocityComponent movement = Mappers.MOVEMENT.get(entity);
        position.update(movement.getVelX(), movement.getVelY());
    }

}
