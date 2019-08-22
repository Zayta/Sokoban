package exp.zhen.zayta.versions_unused.game_mechanics.collision_mechanics.collide_and_fight;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.essence_lab.engine.movement.PositionTracker;
//import exp.zhen.zayta.versions_unused.conquest.battle.HealthComponent;
import exp.zhen.zayta.main.game.essence_lab.engine.entity.components.labels.UndeadTag;
import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.PositionTrackerComponent;


public class UndeadXCivilianCollisionSystem extends EntitySystem {

    //todo later add in wielder x mortal in this same class and rename class to undead x mortal collision system
    private static final Logger log = new Logger(UndeadXCivilianCollisionSystem.class.getName(),Logger.DEBUG);

    //families are entities that can collide
    private final Family UNDEADS;


    public UndeadXCivilianCollisionSystem(){
        UNDEADS = Family.all(
                UndeadTag.class,
                PositionTrackerComponent.class,
                RectangularBoundsComponent.class
        ).get();
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> nighters = getEngine().getEntitiesFor(UNDEADS);

        for(Entity nighter: nighters) {
            int key = PositionTracker.PositionKeyListMap.nightersKeyListMap.getKeyListMap().getKey(nighter);
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
            Entity civilian = PositionTracker.PositionKeyListMap.civiliansKeyListMap.getKeyListMap().get(key);

            if (civilian != null) {
                if (checkCollisionBetween(nighter, civilian)) {
//                    ////log.debug("NighterXCivilian collide");
                    collideEvent(nighter, civilian);
                    break;
                }
            }
        }
    }
    private boolean checkCollisionBetween(Entity undead, Entity obstacle)
    {
        RectangularBoundsComponent playerBounds = Mappers.RECTANGULAR_BOUNDS.get(undead);
        RectangularBoundsComponent obstacleBounds = Mappers.RECTANGULAR_BOUNDS.get(obstacle);

        return Intersector.overlaps(playerBounds.getBounds(),obstacleBounds.getBounds());
    }

    private void collideEvent(Entity nighter, Entity civilian) {
//        civilian.getComponent(MortalTag.class).setHit(true);
        //todo in NighterXWielder System decrease undead hp. if hp is 0 or lower, set Game Over to be true.
//
//        HealthComponent nighterBattleComponent = nighter.getComponent(HealthComponent.class);
//        nighterBattleComponent.decrementHP(nighterBattleComponent.getHP());//decrements all hp when collide with civilian
//        if(nighterBattleComponent.getHP()<=0)
//        {
//            getEngine().removeEntity(nighter);
//            if(UserData.Player==nighter) {
//                setGameOver();
//            }
//        }

    }

}
