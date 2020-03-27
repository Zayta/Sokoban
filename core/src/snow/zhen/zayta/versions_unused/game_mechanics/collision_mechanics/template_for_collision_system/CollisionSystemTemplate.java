package snow.zhen.zayta.versions_unused.game_mechanics.collision_mechanics.template_for_collision_system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Logger;

import snow.zhen.zayta.main.Game;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionTracker;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.CircularBoundsComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.PositionTrackerComponent;

public abstract class CollisionSystemTemplate extends EntitySystem {
    private static final Logger log = new Logger(CollisionSystemTemplate.class.getName(),Logger.DEBUG);
    //families are entities that can collide
    private final Family FAMILY;



    public CollisionSystemTemplate(Game game, PooledEngine engine){
//        super(game,engine);
        FAMILY = Family.all(
                PositionTrackerComponent.class,
                snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.CircularBoundsComponent.class
        ).get();
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> nighters = getEngine().getEntitiesFor(FAMILY);

        for(Entity nighter: nighters) {
            int key = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.POSITION_TRACKER.get(nighter).getPositionKeyListMap().getKey(nighter);
            int keyAbove = key+ snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionTracker.n;
            int keyBelow = key- snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionTracker.n;
            int [] keys = {keyAbove-1,keyAbove,keyAbove+1,
                    key-1, key, key+1,
                    keyBelow-1, keyBelow, keyBelow+1};
            checkCollision(nighter,keys);
        }
    }

    private void checkCollision(Entity nighter, int [] keys){
        for (int key: keys) {
            //todo change whatever the nighter is colliding with
            Entity civilian = PositionTracker.PositionKeyListMap.civiliansKeyListMap.getKeyListMap().get(key);

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
        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.CircularBoundsComponent playerBounds = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.CIRCULAR_BOUNDS.get(undead);
        CircularBoundsComponent obstacleBounds = Mappers.CIRCULAR_BOUNDS.get(obstacle);

        return Intersector.overlaps(playerBounds.getBounds(),obstacleBounds.getBounds());
    }

    private void collideEvent(Entity nighter, Entity civilian) {
        //implement what happens during collision
    }

}
