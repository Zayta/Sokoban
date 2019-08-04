package exp.zhen.zayta.main.game.essence_lab.movement;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.essence_lab.map.MapMaker;
import exp.zhen.zayta.util.BiMap;

public class PositionTracker {
    //warning: all objects in project must be same size.


    private static final Logger log = new Logger(PositionTracker.class.getName(),Logger.DEBUG);
    //todo make enum such that each element in enum points to dif biMap
    public enum PositionBiMap{
        nightersBiMap(PositionTracker.nightersBiMap),
        wieldersBiMap(PositionTracker.wieldersBiMap),
        monstersBiMap(PositionTracker.monstersBiMap),
        civiliansBiMap(PositionTracker.civiliansBiMap);

        private BiMap <Integer, Entity> biMap;
        PositionBiMap(BiMap<Integer,Entity> biMap){
            this.biMap = biMap;
        }
        public BiMap<Integer,Entity> getBiMap(){
            return biMap;
        }
    }
    private static BiMap<Integer,Entity> nightersBiMap = new BiMap<Integer, Entity>();
    private static BiMap<Integer,Entity> wieldersBiMap = new BiMap<Integer, Entity>();
    private static BiMap<Integer,Entity> monstersBiMap = new BiMap<Integer, Entity>();
    private static BiMap<Integer,Entity> civiliansBiMap = new BiMap<Integer, Entity>();


    public static void updateBiMap(BiMap<Integer,Entity> biMap,Entity entity, float x, float y){
        biMap.removeKey(entity);
        int key=generateKey(x,y);
        biMap.put(key,entity);
    }

//    public static int n = (int)(SizeManager.WAKE_WORLD_WIDTH/SizeManager.maxObjWidth);

    public static int n = (int)((MapMaker.getMapBounds().getWidth())/SizeManager.maxObjWidth);

    //todo when modifything generateKey, make sure to modify getPositionFromKey too
    public static int generateKey(float left, float bottom/*, int maxObjWidth, int maxObjHeight*/)
    {//todo this may cause error as i*maxObjHeight might be bigger than screenHeight
        int i = (int)(bottom/SizeManager.maxObjHeight),j = (int)(left/SizeManager.maxObjWidth)/*, n= mapWidth/maxObjWidth*/;
        return i*n+j;
    }
    /*returns bottom left corner of entity from its key*/
    public static Vector2 getPositionFromKey(int key){
        return new Vector2(
                (key%PositionTracker.n)*SizeManager.maxObjWidth,
                (key/PositionTracker.n)*SizeManager.maxObjHeight);
    }


    public static void reset() {
        nightersBiMap.clear();
        wieldersBiMap.clear();
        monstersBiMap.clear();
        civiliansBiMap.clear();
    }
}
