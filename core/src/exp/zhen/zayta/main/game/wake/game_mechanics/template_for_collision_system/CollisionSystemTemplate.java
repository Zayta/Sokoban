package exp.zhen.zayta.main.game.wake.game_mechanics.template_for_collision_system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.RPG;
import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.entity.components.labels.UndeadTag;
import exp.zhen.zayta.main.game.wake.movement.PositionTracker;
import exp.zhen.zayta.main.game.wake.movement.component.CircularBoundsComponent;
import exp.zhen.zayta.main.game.wake.movement.component.PositionTrackerComponent;

public abstract class CollisionSystemTemplate extends EntitySystem {
    //todo later add in wielder x mortal in this same class and rename class to undead x mortal collision system
    private static final Logger log = new Logger(CollisionSystemTemplate.class.getName(),Logger.DEBUG);
    //families are entities that can collide
    private final Family FAMILY;



    public CollisionSystemTemplate(RPG game, PooledEngine engine){
//        super(game,engine);
        FAMILY = Family.all(
                PositionTrackerComponent.class,
                CircularBoundsComponent.class
        ).get();
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> nighters = getEngine().getEntitiesFor(FAMILY);

        for(Entity nighter: nighters) {
            int key = PositionTracker.PositionBiMap.nightersBiMap.getBiMap().getKey(nighter);
            int keyAbove = key+PositionTracker.n;
            int keyBelow = key-PositionTracker.n;
            int [] keys = {keyAbove-1,keyAbove,keyAbove+1,
                    key-1, key, key+1,
                    keyBelow-1, keyBelow, keyBelow+1};
            checkCollision(nighter,keys);
        }
    }

    private void checkCollision(Entity nighter, int [] keys){
        for (int key: keys) {
            Entity civilian = PositionTracker.PositionBiMap.civiliansBiMap.getBiMap().get(key);

            if (civilian != null) {
                if (checkCollisionBetween(nighter, civilian)) {
                    collideEvent(nighter, civilian);
                    break;
                }
            }
        }
    }
    private boolean checkCollisionBetween(Entity undead, Entity obstacle)
    {
        CircularBoundsComponent playerBounds = Mappers.BOUNDS.get(undead);
        CircularBoundsComponent obstacleBounds = Mappers.BOUNDS.get(obstacle);

        return Intersector.overlaps(playerBounds.getBounds(),obstacleBounds.getBounds());
    }

    private void collideEvent(Entity nighter, Entity civilian) {

    }

}
