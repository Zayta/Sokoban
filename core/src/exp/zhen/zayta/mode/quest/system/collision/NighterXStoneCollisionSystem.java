package exp.zhen.zayta.mode.quest.system.collision;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.RPG;
import exp.zhen.zayta.common.Mappers;
import exp.zhen.zayta.mode.quest.entity.gameObjects.StoneTag;
import exp.zhen.zayta.mode.quest.component.properties.movement.CircularBoundsComponent;
import exp.zhen.zayta.mode.quest.component.labels.PlayerTag;


public class NighterXStoneCollisionSystem extends GameControllingSystem implements CollisionListener {

    //todo later add in wielder x mortal in this same class and rename class to undead x mortal collision system
    private static final Logger log = new Logger(NighterXStoneCollisionSystem.class.getName(),Logger.DEBUG);
    //families are entities that can collide
    private final Family NIGHTERS;

    private final Family STONES ;
    private int stonesToGather;

    public NighterXStoneCollisionSystem(RPG game, PooledEngine engine){
        super(game,engine);
        NIGHTERS = Family.all(
                PlayerTag.class,
                CircularBoundsComponent.class
        ).get();

        STONES = Family.all(
                StoneTag.class,
                CircularBoundsComponent.class).get();
        stonesToGather = getEngine().getEntitiesFor(STONES).size();
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> nighters = getEngine().getEntitiesFor(NIGHTERS);//size of  nighters array is always 1 since we only have one nighter instantiated
        ImmutableArray<Entity> stones = getEngine().getEntitiesFor(STONES);
        for(Entity playerEntity: nighters) {
//            int key = Mappers.POSITION.get(playerEntity).getKey();//todo do this after u make a stones BiMap
//            if(stonesBiMap.containsKey(key))
//                if(checkCollision(playerEntity,stonesBiMap.get(key)){
//                    log.debug("collision with stone");
//            }
            for (Entity stoneEntity : stones){
                if(checkCollision(playerEntity,stoneEntity)){
                    log.debug("collision with stone");
                    collideEvent(playerEntity,stoneEntity);
//                    if(stones.size()==0)
//                    {
//                        RPG.userData.setLevelPassed(true);
//                    }
                }
            }
        }


    }
    private boolean checkCollision(Entity nighter, Entity obstacle)
    {
        CircularBoundsComponent playerBounds = Mappers.BOUNDS.get(nighter);
        CircularBoundsComponent obstacleBounds = Mappers.BOUNDS.get(obstacle);

        return Intersector.overlaps(playerBounds.getBounds(),obstacleBounds.getBounds());
    }

    @Override
    public void collideEvent(Entity nighter, Entity stone) {
//       //set stone position, remove stone movement component
//        Position stonePos = Mappers.POSITION.get(stone);
//        stonePos.set(0,0);//later set elsewhere
//        stone.remove(VelocityComponent.class);
        //todo set stone in position at bottom of screen instead
        getEngine().removeEntity(stone);
        stonesToGather--;
        if(stonesToGather==0)
        {
            setNextLevel();
        }

        //update stones to gather
        //decrease number of stones that NUR has to gather

    }

    @Override
    public void reset() {
        stonesToGather = getEngine().getEntitiesFor(STONES).size();
    }
}
