package exp.zhen.zayta.game.quest.system.movement;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import exp.zhen.zayta.common.Mappers;
import exp.zhen.zayta.game.quest.PositionTracker;
import exp.zhen.zayta.game.quest.component.labels.id.MonsterTag;
import exp.zhen.zayta.game.quest.component.labels.id.MortalTag;
import exp.zhen.zayta.game.quest.component.labels.id.NighterTag;
import exp.zhen.zayta.game.quest.component.labels.id.WielderTag;
import exp.zhen.zayta.game.quest.component.properties.movement.DimensionComponent;
import exp.zhen.zayta.game.quest.component.properties.movement.Position;

public class PositionTrackerSystem extends EntitySystem {

    private final Family NIGHTERS;
    private final Family WIELDERS;
    private final Family CIVILIANS;
    private final Family MONSTERS;

    public PositionTrackerSystem()
    {
        NIGHTERS = Family.all(NighterTag.class,Position.class,DimensionComponent.class).get();
        WIELDERS = Family.all(WielderTag.class,Position.class,DimensionComponent.class).get();
        CIVILIANS = Family.all(MortalTag.class,Position.class,DimensionComponent.class).get();
        MONSTERS = Family.all(MonsterTag.class,Position.class,DimensionComponent.class).get();
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> nighters = getEngine().getEntitiesFor(NIGHTERS);
        updatePositionTracker(nighters,PositionTracker.PositionBiMap.nightersBiMap);

        ImmutableArray<Entity> wielders = getEngine().getEntitiesFor(WIELDERS);
        updatePositionTracker(wielders,PositionTracker.PositionBiMap.wieldersBiMap);

        ImmutableArray<Entity> civilians = getEngine().getEntitiesFor(CIVILIANS);
        updatePositionTracker(civilians,PositionTracker.PositionBiMap.civiliansBiMap);

        ImmutableArray<Entity> monsters = getEngine().getEntitiesFor(MONSTERS);
        updatePositionTracker(monsters,PositionTracker.PositionBiMap.monstersBiMap);

    }
    
    private void updatePositionTracker(ImmutableArray<Entity> entities, PositionTracker.PositionBiMap posMap){
        for(Entity entity:entities) {
            updateEntityInTracker(entity,posMap);
        }
    }

    private void updateEntityInTracker(Entity entity,PositionTracker.PositionBiMap posMap) {
        Position position = Mappers.POSITION.get(entity);
        DimensionComponent dimension = Mappers.DIMENSION.get(entity);

        PositionTracker.updateBiMap(posMap.getBiMap(),entity,
                position.getX()+dimension.getWidth()/2,
                position.getY()+dimension.getHeight()/2);
    }

}
