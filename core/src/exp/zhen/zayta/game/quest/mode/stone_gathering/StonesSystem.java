package exp.zhen.zayta.game.quest.mode.stone_gathering;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.RPG;
import exp.zhen.zayta.assets.AssetDescriptors;
import exp.zhen.zayta.common.Mappers;
import exp.zhen.zayta.config.SizeManager;
import exp.zhen.zayta.game.quest.PositionTracker;
import exp.zhen.zayta.game.quest.entity.Arrangements;
import exp.zhen.zayta.game.quest.mode.stone_gathering.gameObjects.StoneBase;
import exp.zhen.zayta.game.quest.mode.stone_gathering.gameObjects.StoneTag;
import exp.zhen.zayta.game.quest.component.properties.movement.CircularBoundsComponent;
import exp.zhen.zayta.game.quest.component.labels.PlayerTag;
import exp.zhen.zayta.game.quest.system.collision.CollisionListener;
import exp.zhen.zayta.game.quest.system.collision.GameControllingSystem;
import exp.zhen.zayta.util.BiMap;


public class StonesSystem extends GameControllingSystem implements CollisionListener {

    //todo later add in wielder x mortal in this same class and rename class to undead x mortal collision system
    private static final Logger log = new Logger(StonesSystem.class.getName(),Logger.DEBUG);

    private StoneBase stoneBase;
    private BiMap<Integer,Entity> stonesBiMap;
    //families are entities that can collide
    private final Family NIGHTERS;

    private final Family STONES ;
//    private int stonesToGather;

    public StonesSystem(RPG game, PooledEngine engine){
        super(game,engine);
        NIGHTERS = Family.all(
                PlayerTag.class,
                CircularBoundsComponent.class
        ).get();

        STONES = Family.all(
                StoneTag.class,
                CircularBoundsComponent.class).get();

        stoneBase = new StoneBase(game.getAssetManager().get(AssetDescriptors.GAME_PLAY),getEngine());
        stonesBiMap = new BiMap<Integer, Entity>();
        initStones();
    }

    private void initStones(){
        int numStones = 5;
        Vector2[] points = Arrangements.circle(numStones,SizeManager.WORLD_CENTER_X,SizeManager.WORLD_CENTER_Y,SizeManager.WORLD_WIDTH/3);
        for(int i =0; i<numStones; i++)
        {
            int key = PositionTracker.generateKey(points[i].x,points[i].y);
            stonesBiMap.put(key,
                    stoneBase.makeEntityInPos(points[i].x,points[i].y));
            log.debug("iteration "+i+", pointsx: "+points[i].x+", points y: "+points[i].y+"\n"
            +stonesBiMap.get(key));
        }
        log.debug("stoneBiMap: "+stonesBiMap);
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
//        stonesToGather--;
//        if(stonesToGather==0)
//        {
//            setNextLevel();
//        }

        //update stones to gather
        //decrease number of stones that NUR has to gather

    }

    @Override
    public void reset() {
        stonesBiMap.clear();
//        stonesToGather = getEngine().getEntitiesFor(STONES).size();
    }


    //
//    @Override
//    public void update(float deltaTime) {
//        ImmutableArray<Entity> nighters = getEngine().getEntitiesFor(NIGHTERS);//size of  nighters array is always 1 since we only have one nighter instantiated
//        ImmutableArray<Entity> stones = getEngine().getEntitiesFor(STONES);
//        for(Entity playerEntity: nighters) {
////            int key = Mappers.POSITION.get(playerEntity).getKey();//todo do this after u make a stones BiMap
////            if(stonesBiMap.containsKey(key))
////                if(checkCollision(playerEntity,stonesBiMap.get(key)){
////                    log.debug("collision with stone");
////            }
//            for (Entity stoneEntity : stones){
//                if(checkCollision(playerEntity,stoneEntity)){
//                    log.debug("collision with stone");
//                    collideEvent(playerEntity,stoneEntity);
////                    if(stones.size()==0)
////                    {
////                        RPG.userData.setLevelPassed(true);
////                    }
//                }
//            }
//        }
//
//
//    }
}
