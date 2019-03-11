package exp.zhen.zayta.game.quest;

import com.badlogic.ashley.core.Entity;

import exp.zhen.zayta.config.SizeManager;
import exp.zhen.zayta.util.BiMap;

public class PositionTracker {


    //todo make enum such that each element in enum points to dif biMap
    public enum PositionBiMap{
        nightersBiMap(PositionTracker.nightersBiMap),
        wieldersBiMap(PositionTracker.wieldersBiMap),
        monstersBiMap(PositionTracker.monstersBiMap),
        civiliansBiMap(PositionTracker.civiliansBiMap),
        objectsBiMap(PositionTracker.objectsBiMap);

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
    private static BiMap<Integer,Entity> objectsBiMap = new BiMap<Integer, Entity>();


    public static void updateBiMap(BiMap<Integer,Entity> biMap,Entity entity, float x, float y){
        biMap.removeKey(entity);
        int key=generateKey(x,y);
        biMap.put(key,entity);
    }

    public static int generateKey(float left, float bottom){
        int i = (int)(bottom/SizeManager.maxObjHeight),j = (int)(left/SizeManager.maxObjWidth)/*, n= mapWidth/maxObjWidth*/;
        return i*(int)SizeManager.maxObjWidth+j;
    }
    public static void reset() {
        nightersBiMap.clear();
        wieldersBiMap.clear();
        monstersBiMap.clear();
        civiliansBiMap.clear();
        objectsBiMap.clear();
    }
}
