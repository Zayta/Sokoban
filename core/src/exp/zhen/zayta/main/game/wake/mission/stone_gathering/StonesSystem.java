package exp.zhen.zayta.main.game.wake.mission.stone_gathering;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.wake.assets.WakePlayRegionNames;
import exp.zhen.zayta.main.game.wake.movement.Direction;
import exp.zhen.zayta.RPG;
import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.wake.movement.PositionTracker;
import exp.zhen.zayta.main.game.wake.WakeMode;
import exp.zhen.zayta.main.game.wake.entity.Arrangements;
import exp.zhen.zayta.main.game.wake.movement.component.CircularBoundsComponent;
import exp.zhen.zayta.main.game.wake.component.labels.PlayerTag;
import exp.zhen.zayta.main.game.wake.collision.CollisionListener;
import exp.zhen.zayta.main.game.wake.collision.GameControllingSystem;
import exp.zhen.zayta.util.BiMap;


public class StonesSystem extends GameControllingSystem implements CollisionListener {

    //todo later add in wielder x mortal in this same class and rename class to undead x mortal collision system
    private static final Logger log = new Logger(StonesSystem.class.getName(),Logger.DEBUG);

    private BiMap<Integer,Entity> stonesBiMap;
    //families are entities that can collide
    private final Family NIGHTERS;

    public StonesSystem(RPG game, PooledEngine engine){
        super(game,engine);
        NIGHTERS = Family.all(
                PlayerTag.class,
                CircularBoundsComponent.class
        ).get();


        stonesBiMap = new BiMap<Integer, Entity>();
        initStones();
    }

    private void initStones(){
        int numStones = 5;
        Vector2[] points = Arrangements.circle(numStones,SizeManager.WAKE_WORLD_CENTER_X,SizeManager.WAKE_WORLD_CENTER_Y,SizeManager.WAKE_WORLD_WIDTH/3);
        for(int i =0; i<numStones; i++)
        {
            int key = PositionTracker.generateKey(points[i].x,points[i].y);
            stonesBiMap.put(key,
                    WakeMode.manufacturer.makeEntityInPos(points[i].x,points[i].y,StoneTag.class,WakePlayRegionNames.STONE));
//            log.debug("iteration "+i+", pointsx: "+points[i].x+", points y: "+points[i].y+"\n"
//            +stonesBiMap.get(key));
        }
//        log.debug("stoneBiMap: "+stonesBiMap);
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> nighters = getEngine().getEntitiesFor(NIGHTERS);
        
        for(Entity nighter: nighters) {

            Direction direction = Mappers.MOVEMENT.get(nighter).getDirection();
            int [] keys = new int [6];

            int key = PositionTracker.PositionBiMap.nightersBiMap.getBiMap().getKey(nighter);
            int keyAbove = key+PositionTracker.n;
            int keyBelow = key-PositionTracker.n;
            switch (direction){
                case none:
                    Entity stone = stonesBiMap.get(key);
                    if(stone!=null)
                        checkCollisionBetween(nighter,stone);
                    continue;
                case up:
                    keys[0]= keyAbove;
                    keys[1]= keyAbove+1;
                    keys[2]= keyAbove-1;
                    keys[3] = key-1;
                    keys[4] = key+1;
                    break;
                case down:
                    keys[0]= keyBelow;
                    keys[1]= keyBelow+1;
                    keys[2]= keyBelow-1;
                    keys[3] = key-1;
                    keys[4] = key+1;
                    break;
                case left:
                    keys[0]= keyAbove-1;
                    keys[1]= key-1;
                    keys[2]= keyBelow-1;
                    keys[3] = keyAbove;
                    keys[4] = keyBelow;
                    break;
                case right:
                    keys[0]= keyAbove+1;
                    keys[1]= key+1;
                    keys[2]= keyBelow+1;
                    keys[3] = keyAbove;
                    keys[4] = keyBelow;
                    break;
            }
            keys[5] = key;
            checkCollision(nighter,keys);


//            int key = PositionTracker.PositionBiMap.nightersBiMap.getBiMap().getKey(nighter);
//            int keyAbove = key+PositionTracker.n;
//            int keyBelow = key-PositionTracker.n;
//            int [] keys = {keyAbove-1,keyAbove,keyAbove+1,
//                    key-1, key, key+1,
//                    keyBelow-1, keyBelow, keyBelow+1};
//            checkCollision(nighter,keys);
        }
    }
    private void checkCollision(Entity nighter, int [] keys){
        for (int key: keys) {
            Entity stone = stonesBiMap.get(key);

            if (stone != null) {
                if (checkCollisionBetween(nighter, stone)) {
                    log.debug("Stone collide");
                    collideEvent(nighter, stone);
                }
            }
        }
    }
    private boolean checkCollisionBetween(Entity nighter, Entity stone)
    {
        CircularBoundsComponent playerBounds = Mappers.BOUNDS.get(nighter);
        CircularBoundsComponent stoneBounds = Mappers.BOUNDS.get(stone);

        return Intersector.overlaps(playerBounds.getBounds(),stoneBounds.getBounds());
    }

    @Override
    public void collideEvent(Entity nighter, Entity stone) {
//       //set stone position, remove stone movement component
//        Position stonePos = Mappers.POSITION.get(stone);
//        stonePos.set(0,0);//later set elsewhere
//        stone.remove(VelocityComponent.class);
        //todo set stone in position at bottom of screen instead
        getEngine().removeEntity(stone);
        stonesBiMap.removeKey(stone);
        if(stonesBiMap.size()==0){
            setNextLevel();
        }

    }

    @Override
    public void reset() {
        stonesBiMap.clear();
    }


}
