package exp.zhen.zayta.main.game.essence_lab.game_mechanics.collision_mechanics.bomb_trigger;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.essence_lab.entity.components.properties.HealthComponent;
import exp.zhen.zayta.main.game.essence_lab.entity.components.properties.explosion.ExplosiveComponent;
import exp.zhen.zayta.main.game.essence_lab.entity.id_tags.MonsterTag;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.collision_mechanics.template_for_collision_system.CollisionSystemTemplate;
import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.movement.PositionTracker;
import exp.zhen.zayta.main.game.essence_lab.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.util.BiMap;

public class LandmineExplosionSystem extends EntitySystem {
    private static final Logger log = new Logger(CollisionSystemTemplate.class.getName(),Logger.DEBUG);
    //families are entities that can collide
    private final Family MONSTERS;
    
    public static BiMap<Integer,Entity> landmineBiMap = new BiMap<Integer, Entity>();

    private BiMap<Entity,Entity> currentCollisions;

    public LandmineExplosionSystem(){
        currentCollisions = new BiMap<Entity, Entity>();
        MONSTERS = Family.all(
                MonsterTag.class,
                PositionTrackerComponent.class,
                HealthComponent.class,
                RectangularBoundsComponent.class
        ).get();
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> monsters = getEngine().getEntitiesFor(MONSTERS);

        for(Entity monster: monsters) {
            int key = PositionTracker.PositionBiMap.monstersBiMap.getBiMap().getKey(monster);
            int keyAbove = key+PositionTracker.n;
            int keyBelow = key-PositionTracker.n;
            int [] keys = {keyAbove-1,keyAbove,keyAbove+1,
                    key-1, key, key+1,
                    keyBelow-1, keyBelow, keyBelow+1};
            checkCollision(monster,keys);
        }
    }

    private void checkCollision(Entity monster, int [] keys){
        for (int key: keys) {
            Entity landmine = landmineBiMap.get(key);

            if (landmine != null) {
                if (checkCollisionBetween(monster, landmine)) {
                    if(collisionUnhandled(monster,landmine)) {
                        collideEvent(monster, landmine);
                        currentCollisions.put(monster,landmine);
                        //todo engine.add(new ParticleAnimationSystem()) //store particleAnimationSystem as a variable. make entity w animation and position and texture and dimension components.

                        break;
                    }
                    break;
                }

                else{
                    updateCurrentCollision(monster,landmine);
                    //todo engine.remove(ParticleAnimationSystem)
                }
            }
        }
    }
    private boolean checkCollisionBetween(Entity entity, Entity obstacle)
    {
        RectangularBoundsComponent playerBounds = Mappers.RECTANGULAR_BOUNDS.get(entity);
        RectangularBoundsComponent obstacleBounds = Mappers.RECTANGULAR_BOUNDS.get(obstacle);

        return Intersector.overlaps(playerBounds.getBounds(),obstacleBounds.getBounds());
    }

    private boolean collisionUnhandled(Entity nighter, Entity monster){
        return !(currentCollisions.get(nighter) == monster) && !(currentCollisions.get(monster) == nighter);
    }

    private void collideEvent(Entity monster, Entity landmine) {
        //implement what happens during collision
//        //log.debug("mosnter collided w landmine");
        HealthComponent monsterHp = Mappers.HEALTH.get(monster);
        ExplosiveComponent explosiveComponent = Mappers.EXPLOSIVE.get(landmine);
        if(explosiveComponent!=null)
            monsterHp.decrement(explosiveComponent.getPower());
    }


    private void updateCurrentCollision(Entity monster,Entity landmine){
//        if(currentFighters.containsKey(nighter)) {
//            if (!checkCollisionBetween(nighter, currentFighters.get(nighter))) {
//                currentFighters.removeKey(nighter);
//            }
//        }
        currentCollisions.removeKey(landmine);
        currentCollisions.removeKey(monster);
    }

    public void reset() {
        currentCollisions.clear();
    }
}
