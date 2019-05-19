package exp.zhen.zayta.main.game.wake.collision;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.RPG;
import exp.zhen.zayta.main.game.wake.movement.PositionTracker;
//import exp.zhen.zayta.main.game.conquest.battle.BattleComponent;
import exp.zhen.zayta.main.game.wake.component.labels.UndeadTag;
import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.movement.component.CircularBoundsComponent;
import exp.zhen.zayta.UserData;
import exp.zhen.zayta.main.game.wake.movement.component.PositionTrackerComponent;


public class UndeadXCivilianCollisionSystem extends GameControllingSystem implements CollisionListener {

    //todo later add in wielder x mortal in this same class and rename class to undead x mortal collision system
    private static final Logger log = new Logger(UndeadXCivilianCollisionSystem.class.getName(),Logger.DEBUG);
    //families are entities that can collide
    private final Family UNDEADS;



    public UndeadXCivilianCollisionSystem(RPG game, PooledEngine engine){
        super(game,engine);
        UNDEADS = Family.all(
                UndeadTag.class,
                PositionTrackerComponent.class,
                CircularBoundsComponent.class
        ).get();
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> nighters = getEngine().getEntitiesFor(UNDEADS);

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
                    log.debug("NighterXCivilian collide");
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

    @Override
    public void collideEvent(Entity nighter, Entity civilian) {
//        civilian.getComponent(MortalTag.class).setHit(true);
        //todo in NighterXWielder System decrease undead HP. if HP is 0 or lower, set Game Over to be true.
//
//        BattleComponent nighterBattleComponent = nighter.getComponent(BattleComponent.class);
//        nighterBattleComponent.decrementHP(nighterBattleComponent.getHP());//decrements all HP when collide with civilian
//        if(nighterBattleComponent.getHP()<=0)
//        {
//            getEngine().removeEntity(nighter);
//            if(UserData.Player==nighter) {
//                setGameOver();
//            }
//        }

    }

    @Override
    public void reset() {

    }

}
