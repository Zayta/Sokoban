package exp.zhen.zayta.main.game.personality_engineering_lab.movement;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.config.SizeManager;
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

    public static int n = (int)(SizeManager.WAKE_WORLD_WIDTH/SizeManager.maxObjWidth);


    public static int generateKey(float left, float bottom/*, int maxObjWidth, int maxObjHeight*/)
    {//todo this may cause error as i*maxObjHeight might be bigger than screenHeight
        float top = bottom+SizeManager.maxObjHeight;
        int i = (int)(top/SizeManager.maxObjHeight),j = (int)(left/SizeManager.maxObjWidth)/*, n= mapWidth/maxObjWidth*/;
        return i*n+j;
    }


    public static void reset() {
        nightersBiMap.clear();
        wieldersBiMap.clear();
        monstersBiMap.clear();
        civiliansBiMap.clear();
    }
}
