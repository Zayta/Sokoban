package exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position;
import exp.zhen.zayta.util.KeyListMap;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionTracker;

public class PositionTrackerUpdateSystem extends EntitySystem {

//    private final Family NIGHTERS;
//    private final Family WIELDERS;
//    private final Family CIVILIANS;
//    private final Family MONSTERS;

    private final Family TRACKED_ENTITIES;
    public PositionTrackerUpdateSystem()
    {
        TRACKED_ENTITIES = Family.all(
                PositionTrackerComponent.class,
                Position.class
//                DimensionComponent.class
        ).get();
//        NIGHTERS = Family.all(NighterTag.class,Position.class,DimensionComponent.class).get();
//        WIELDERS = Family.all(WielderTag.class,Position.class,DimensionComponent.class).get();
//        CIVILIANS = Family.all(MortalTag.class,Position.class,DimensionComponent.class).get();
//        MONSTERS = Family.all(MonsterTag.class,Position.class,DimensionComponent.class).get();
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> trackedEntities = getEngine().getEntitiesFor(TRACKED_ENTITIES);
        for(Entity entity:trackedEntities) {
            KeyListMap<Integer,Entity> posMap = Mappers.POSITION_TRACKER.get(entity).getPositionKeyListMap();
            updateEntityInTracker(entity,posMap);
        }
    }
    //    @Override
//    public void update(float deltaTime) {
//        ImmutableArray<Entity> nighters = getEngine().getEntitiesFor(NIGHTERS);
//        updatePositionTracker(nighters,PositionTracker.PositionKeyListMap.nightersKeyListMap);
//
//        ImmutableArray<Entity> wielders = getEngine().getEntitiesFor(WIELDERS);
//        updatePositionTracker(wielders,PositionTracker.PositionKeyListMap.wieldersKeyListMap);
//
//        ImmutableArray<Entity> civilians = getEngine().getEntitiesFor(CIVILIANS);
//        updatePositionTracker(civilians,PositionTracker.PositionKeyListMap.civiliansKeyListMap);
//
//        ImmutableArray<Entity> monsters = getEngine().getEntitiesFor(MONSTERS);
//        updatePositionTracker(monsters,PositionTracker.PositionKeyListMap.monstersKeyListMap);
//
//    }
//
//    private void updatePositionTracker(ImmutableArray<Entity> entities, PositionTracker.PositionKeyListMap posMap){
//        for(Entity entity:entities) {
//            updateEntityInTracker(entity,posMap);
//        }
//    }

    private void updateEntityInTracker(Entity entity,KeyListMap<Integer,Entity> posMap) {
        Position position = Mappers.POSITION.get(entity);
//        DimensionComponent dimension = Mappers.DIMENSION.get(entity);

        PositionTracker.updateKeyListMap(posMap,entity,
                position.getX(),
                position.getY());
    }

}
