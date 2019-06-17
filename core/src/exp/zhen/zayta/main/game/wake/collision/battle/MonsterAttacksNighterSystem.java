package exp.zhen.zayta.main.game.wake.collision.battle;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Logger;

import java.util.Set;

import exp.zhen.zayta.RPG;
import exp.zhen.zayta.main.game.wake.collision.GameControllingSystem;
import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.entity.components.labels.id_tags.NighterTag;
import exp.zhen.zayta.main.game.wake.entity.components.properties.AttackComponent;
import exp.zhen.zayta.main.game.wake.entity.components.properties.HealthComponent;
import exp.zhen.zayta.main.game.wake.movement.PositionTracker;
import exp.zhen.zayta.main.game.wake.movement.component.CircularBoundsComponent;
import exp.zhen.zayta.main.game.wake.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.util.BiMap;

public class MonsterAttacksNighterSystem extends GameControllingSystem {

    //todo later add in wielder x mortal in this same class and rename class to undead x mortal collision system
    private static final Logger log = new Logger(MonsterAttacksNighterSystem.class.getName(),Logger.DEBUG);
    //families are entities that can collide
    private final Family NIGHTERS = Family.all(
            NighterTag.class,
            PositionTrackerComponent.class,
            CircularBoundsComponent.class,
            HealthComponent.class
    ).get();

    private BiMap<Entity,Entity> currentFighters;



    public MonsterAttacksNighterSystem(RPG game, PooledEngine engine){
        super(game,engine);
        currentFighters = new BiMap<Entity, Entity>();
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> nighters = getEngine().getEntitiesFor(NIGHTERS);

        for(Entity nighter: nighters) {
            int key = PositionTracker.PositionBiMap.nightersBiMap.getBiMap().getKey(nighter);
            int keyAbove = key+PositionTracker.n;
            int keyBelow = key-PositionTracker.n;
            int [] keys = {keyAbove-1,keyAbove,keyAbove+1,
                    key-1, key, key+1,
                    keyBelow-1, keyBelow, keyBelow+1};
            checkCollision(nighter,keys);
//            updateCurrentBattles(nighter);
        }
    }

    private void checkCollision(Entity nighter, int [] keys){
        for (int key: keys) {
            Entity monster = PositionTracker.PositionBiMap.monstersBiMap.getBiMap().get(key);

            if (monster != null) {
                if (checkCollisionBetween(nighter, monster)) {
//                    log.debug("NighterXCivilian collide");
                    if(collisionUnhandled(nighter,monster)) {
                        collideEvent(nighter, monster);
                        currentFighters.put(nighter,monster);
                        currentFighters.put(monster,nighter);
                        break;
                    }
                }
                else{
                    updateCurrentBattles(nighter,monster);
                }
            }
        }
    }
    private boolean checkCollisionBetween(Entity nighter, Entity monster)
    {
        CircularBoundsComponent playerBounds = Mappers.BOUNDS.get(nighter);
        CircularBoundsComponent obstacleBounds = Mappers.BOUNDS.get(monster);

        return Intersector.overlaps(playerBounds.getBounds(),obstacleBounds.getBounds());
    }

    private boolean collisionUnhandled(Entity nighter, Entity monster){
        return !(currentFighters.get(nighter) == monster) && !(currentFighters.get(monster) == nighter);
    }

    private void collideEvent(Entity nighter, Entity monster) {

        HealthComponent nighterHp = nighter.getComponent(HealthComponent.class);
        AttackComponent monsterAtk = monster.getComponent(AttackComponent.class);

        nighterHp.decrement(monsterAtk.getAtk());
        log.debug("Monster attackedd Nighter");
        if(nighterHp.getHp()<=0){
            setGameOver();
        }
//        monster.getComponent(MortalTag.class).setHit(true);
        //todo in NighterXWielder System decrease undead hp. if hp is 0 or lower, set Game Over to be true.

//        HealthComponent nighterBattleComponent = nighter.getComponent(HealthComponent.class);
//        nighterBattleComponent.decrementHP(nighterBattleComponent.getHP());//decrements all hp when collide with monster
//        if(nighterBattleComponent.getHP()<=0)
//        {
//            getEngine().removeEntity(nighter);
//            if(UserData.Player==nighter) {
//                setGameOver();
//            }
//        }

    }
    private void updateCurrentBattles(Entity nighter,Entity monster){
//        if(currentFighters.containsKey(nighter)) {
//            if (!checkCollisionBetween(nighter, currentFighters.get(nighter))) {
//                currentFighters.removeKey(nighter);
//            }
//        }
        currentFighters.removeKey(nighter);
        currentFighters.removeKey(monster);
    }

    @Override
    public void reset() {
        currentFighters.clear();
    }
}
