package exp.zhen.zayta.main.game.essence_lab.blocks;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;

import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.movement.PositionTracker;
import exp.zhen.zayta.main.game.essence_lab.movement.component.Position;
import exp.zhen.zayta.util.KeyListMap;

public class BlocksTrackerSystem extends IteratingSystem {
    public static KeyListMap<Integer,Entity> blocksKeyListMap = new KeyListMap<Integer, Entity>();


    private static Family family = Family.all(BlockComponent.class,Position.class).get();
    public BlocksTrackerSystem() {
        super(family);
//        ImmutableArray<Entity> trackedEntities = getEngine().getEntitiesFor(family);
//        for(Entity entity:trackedEntities){
//
//        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        updateEntityInTracker(entity);
    }

    private void updateEntityInTracker(Entity entity) {
        Position position = Mappers.POSITION.get(entity);
        PositionTracker.updateKeyListMap(blocksKeyListMap,entity,
                position.getX(),
                position.getY());
    }
}
