package exp.zhen.zayta.main.game.essence_lab.game_mechanics.collision_mechanics.template_for_collision_system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.movement.PositionTracker;
import exp.zhen.zayta.main.game.essence_lab.movement.component.RectangularBoundsComponent;

public abstract class CollisionSystemTemplate extends IteratingSystem {
    private static final Logger log = new Logger(CollisionSystemTemplate.class.getName(),Logger.DEBUG);
    
    private final Family FAMILY;
    public CollisionSystemTemplate(Family family) {
        super(family);
        this.FAMILY = family;
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(FAMILY);

        for(Entity entity: entities) {
            int key = Mappers.POSITION_TRACKER.get(entity).getPositionKeyListMap().getKey(entity);
            int keyAbove = key+PositionTracker.n;
            int keyBelow = key-PositionTracker.n;
            int [] keys = {keyAbove-1,keyAbove,keyAbove+1,
                    key-1, key, key+1,
                    keyBelow-1, keyBelow, keyBelow+1};
            checkCollision(entity,keys);
        }
    }

    private void checkCollision(Entity entity, int [] keys){
        for (int key: keys) {
            //todo change whatever the entity is colliding with
            Entity civilian = PositionTracker.PositionKeyListMap.civiliansKeyListMap.getKeyListMap().get(key);

            if (civilian != null) {
                if (checkCollisionBetween(entity, civilian)) {
                    collideEvent(entity, civilian);
                    break;
                }
            }
        }
    }
    private boolean checkCollisionBetween(Entity entity1, Entity entity2)
    {
        RectangularBoundsComponent entity1Bounds = Mappers.RECTANGULAR_BOUNDS.get(entity1);
        RectangularBoundsComponent entity2Bounds = Mappers.RECTANGULAR_BOUNDS.get(entity2);

        return Intersector.overlaps(entity1Bounds.getBounds(),entity2Bounds.getBounds());
    }

    public abstract void collideEvent(Entity entity1, Entity entity2);

}
