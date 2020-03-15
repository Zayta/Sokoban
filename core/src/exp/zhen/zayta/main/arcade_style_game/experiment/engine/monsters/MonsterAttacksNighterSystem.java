package exp.zhen.zayta.main.arcade_style_game.experiment.engine.monsters;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;

import java.util.Iterator;

import exp.zhen.zayta.main.arcade_style_game.experiment.common.Mappers;
import exp.zhen.zayta.main.arcade_style_game.experiment.engine.entity.id_tags.NighterTag;
import exp.zhen.zayta.main.arcade_style_game.experiment.engine.entity.components.properties.AttackComponent;
import exp.zhen.zayta.main.arcade_style_game.experiment.engine.entity.components.properties.HealthComponent;
import exp.zhen.zayta.main.arcade_style_game.experiment.engine.movement.PositionTracker;
import exp.zhen.zayta.main.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.arcade_style_game.experiment.engine.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.util.KeyListMap;

public class MonsterAttacksNighterSystem extends EntitySystem implements Pool.Poolable {

    //todo later add in wielder x mortal in this same class and rename class to undead x mortal collision system
    private static final Logger log = new Logger(MonsterAttacksNighterSystem.class.getName(),Logger.DEBUG);
    //families are entities that can collide
    private final Family NIGHTERS = Family.all(
            NighterTag.class,
            PositionTrackerComponent.class,
            RectangularBoundsComponent.class,
            HealthComponent.class
    ).get();

    private KeyListMap<Entity,Entity> currentFighters; //key is nighter, value is monster


    public MonsterAttacksNighterSystem(){
        currentFighters = new KeyListMap<Entity, Entity>();
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> nighters = getEngine().getEntitiesFor(NIGHTERS);

        for(Entity nighter: nighters) {
//            int key = PositionTracker.PositionKeyListMap.nightersKeyListMap.getKeyListMap().getKey(nighter);
            int key = Mappers.POSITION_TRACKER.get(nighter).getPositionKeyListMap().getKey(nighter);
            int keyAbove = key+PositionTracker.n;
            int keyBelow = key-PositionTracker.n;
            int [] keys = {keyAbove-1,keyAbove,keyAbove+1,
                    key-1, key, key+1,
                    keyBelow-1, keyBelow, keyBelow+1};
            checkCollision(nighter,keys);
            updateCurrentBattles(nighter);
        }
        //////log.debug("NumNighters are "+nighters.size());
        //////log.debug("\nCurrentFighters are: "+ currentFighters);
    }

    private void checkCollision(Entity nighter, int [] keys){
        for (int key: keys) {
            Entity monster = PositionTracker.PositionKeyListMap.monstersKeyListMap.getKeyListMap().get(key);

            if (monster != null) {
                if (checkCollisionBetween(nighter, monster)) {
                    if(collisionUnhandled(nighter,monster)) {
                        collideEvent(nighter, monster);
//                        currentFighters.put(nighter,monster);
                        currentFighters.put(nighter,monster);
                        break;
                    }
                }
            }
        }
    }
    private boolean checkCollisionBetween(Entity nighter, Entity monster)
    {
        RectangularBoundsComponent playerBounds = Mappers.RECTANGULAR_BOUNDS.get(nighter);
        RectangularBoundsComponent obstacleBounds = Mappers.RECTANGULAR_BOUNDS.get(monster);

//        //////log.debug("playerBounds is "+playerBounds);
//        //////log.debug("obstacleBounds is "+obstacleBounds);

        return Intersector.overlaps(playerBounds.getBounds(),obstacleBounds.getBounds());
    }
    private boolean collisionUnhandled(Entity nighter, Entity monster){
        if(currentFighters.getList(nighter)==null)
            return true;
        return !(currentFighters.getList(nighter).contains(monster));
    }

//    private boolean collisionUnhandled(Entity nighter, Entity monster){
//        return !(currentFighters.get(nighter) == monster) && !(currentFighters.get(monster) == nighter);
//    }

    private void collideEvent(Entity nighter, Entity monster) {
        HealthComponent nighterHp = Mappers.HEALTH.get(nighter);
        AttackComponent monsterAtk = Mappers.ATK.get(monster);
//        HealthComponent nighterHp = nighter.getComponent(HealthComponent.class);
//        AttackComponent monsterAtk = monster.getComponent(AttackComponent.class);

        nighterHp.decrement(monsterAtk.getAtk());
//        //////log.debug("Monster attackedd Nighter");
//        if(nighterHp.getHp()<=0){
//            setGameOver();
//        }
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

    private void updateCurrentBattles(Entity entity){
        if(currentFighters.getList(entity)!=null){
            for(Iterator<Entity> itr = currentFighters.getList(entity).iterator(); itr.hasNext();)
            {
                Entity monster = itr.next();
                if(!checkCollisionBetween(entity, monster)){
                    // listOfPhones.remove(phone);  // wrong again
                    itr.remove(); // right call
                }
            }
//            for(Entity lantern:currentFighters.getList(entity)) {
//                if(!checkCollisionBetween(entity, lantern))
//                    currentFighters.remove(entity,lantern);
//            }
        }

//        currentFighters.removeKey(lantern);
//        currentFighters.remove(lantern);
//        //log.debug("updateCurrentBattles of Lantern alks");
    }
//    private void updateCurrentBattles(Entity nighter,Entity monster){
////        if(currentFighters.containsKey(nighter)) {
////            if (!checkCollisionBetween(nighter, currentFighters.get(nighter))) {
////                currentFighters.removeKey(nighter);
////            }
////        }
//        currentFighters.remove(monster,nighter);
////        currentFighters.removeKey(nighter);
////        currentFighters.removeKey(monster);
//    }

    @Override
    public void reset() {
        currentFighters.clear();
    }
}
