package exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.mind_growing;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;


import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.entity.components.labels.NPCTag;
import exp.zhen.zayta.main.game.essence_lab.entity.components.properties.ColorComponent;
import exp.zhen.zayta.main.game.essence_lab.entity.id_tags.NighterTag;
import exp.zhen.zayta.main.game.essence_lab.entity.components.properties.HealthComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.PositionTracker;
import exp.zhen.zayta.main.game.essence_lab.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.VelocityComponent;
import exp.zhen.zayta.util.KeyListMap;

public class LanternSystem extends IteratingSystem implements Pool.Poolable {
    private static final Logger log = new Logger(LanternSystem.class.getName(),Logger.DEBUG);
    //families are entities that can collide
    private PooledEngine engine;

    private final KeyListMap<Integer,Entity> lanternsKeyListMap;
    private KeyListMap<Entity,Entity> currentFighters; //list of currently colliding entities, key is entity, value is list of lanterns it is colliding with


    LanternSystem(PooledEngine engine, KeyListMap<Integer,Entity> lanternsKeyListMap)
    {
        super(Family.all(
                ColorComponent.class,
                PositionTrackerComponent.class,
                RectangularBoundsComponent.class,
                HealthComponent.class
        ).one(NPCTag.class,NighterTag.class).exclude(LanternTag.class).get());
//        super(game,engine);
//        addMission();
        this.engine = engine;
        this.lanternsKeyListMap= lanternsKeyListMap;
        currentFighters = new KeyListMap<Entity, Entity>();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        int key = Mappers.POSITION_TRACKER.get(entity).getPositionKeyListMap().getKey(entity);
        int keyAbove = key+PositionTracker.n;
        int keyBelow = key-PositionTracker.n;
        int [] keys = {keyAbove-1,keyAbove,keyAbove+1,
                key-1, key, key+1,
                keyBelow-1, keyBelow, keyBelow+1};
        checkCollision(entity,keys);
    }

    private void checkCollision(Entity entity, int [] keys){
        for (int key: keys) {
            Entity lantern = lanternsKeyListMap.get(key);

            if (lantern != null) {
                if (checkCollisionBetween(entity, lantern)) {
                    if(collisionUnhandled(entity,lantern)) {
                        collideEvent(entity, lantern);
                        currentFighters.put(entity,lantern);
//                        currentFighters.put(lantern,entity);
                        break;
                    }
                }
                else{
                    updateCurrentBattles(entity,lantern);
                }
            }
        }
    }
    private boolean checkCollisionBetween(Entity entity, Entity lantern)
    {
        RectangularBoundsComponent playerBounds = Mappers.RECTANGULAR_BOUNDS.get(entity);
        RectangularBoundsComponent obstacleBounds = Mappers.RECTANGULAR_BOUNDS.get(lantern);

        return Intersector.overlaps(playerBounds.getBounds(),obstacleBounds.getBounds());
    }

//    private boolean collisionUnhandled(Entity entity, Entity lantern){
//        return !(currentFighters.get(entity) == lantern) && !(currentFighters.get(lantern) == entity);
//    }
    private boolean collisionUnhandled(Entity entity, Entity lantern){
        if(currentFighters.getList(entity)==null)
            return true;
        return !(currentFighters.getList(entity).contains(lantern));
    }

    private void collideEvent(Entity entity, Entity lantern) {
        //do NOT do "Color.set", make sure it is ColorComponent.setColor
        //sets lantern color to entity color
        ColorComponent entityColor = Mappers.COLOR.get(entity);

        ColorComponent lanternColor = Mappers.COLOR.get(lantern);
//        Mappers.COLOR.get(lantern).setColor(Mappers.COLOR.get(entity).getColor());
        LanternTag lanternTag = Mappers.LANTERN.get(lantern);

        log.debug("Before Lantern System Collide Event\nLantern color is "+lanternColor+"\nNighter color is "+entityColor+"\nLantern State is "+lanternTag.getState()+"\nlanternColor==entityColor? "+(lanternColor.equals(entityColor)));
        if(lanternTag.getState()==LanternTag.State.DORMANT){
            if(lanternColor.getColor().equals(entityColor.getColor())){
                lanternTag.setState(LanternTag.State.FLARE);
                log.debug("state should be set to flare n vel component removed");
                lantern.remove(VelocityComponent.class);
                //add particle animation
//                ParticleAnimationComponent particleAnimationComponent = engine.createComponent(ParticleAnimationComponent.class);
//                particleAnimationComponent.init(WPRegionNames.FIRE_BLOB_PREFLARE,6,1);
            }
            else
            {
                lanternColor.setColor(entityColor.getColor());
            }
        }
        else if(lanternTag.getState()==LanternTag.State.FLARE){
            if(lanternColor.getColor().equals(entityColor.getColor())){
                lanternTag.setState(LanternTag.State.DORMANT);
                lantern.add(engine.createComponent(VelocityComponent.class));
            }
            else
            {
                Mappers.HEALTH.get(entity).decrement(Mappers.EXPLOSIVE.get(lantern).getPower());
            }
        }
        log.debug("After Lantern System Collide Event\nLantern color is "+lanternColor+"\nNighter color is "+entityColor+"\nLantern State is "+lanternTag.getState());

    }
    private void updateCurrentBattles(Entity entity,Entity lantern){
        currentFighters.removeKey(entity);
        currentFighters.removeKey(lantern);
//        currentFighters.remove(lantern);
        log.debug("updateCurrentBattles of Lantern alks");
    }

    @Override
    public void reset() {
        currentFighters.clear();
    }

}
