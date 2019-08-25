package exp.zhen.zayta.main.game.experiment.engine.movement;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.experiment.engine.map.MapMaker;
import exp.zhen.zayta.util.KeyListMap;

public class PositionTracker {
    //warning: all objects in project must be same size.


    public static final KeyListMap<Integer,Entity> globalTracker = new KeyListMap<Integer, Entity>();

    private static final Logger log = new Logger(PositionTracker.class.getName(),Logger.DEBUG);
    //todo make enum such that each element in enum points to dif KeyListMap
    public enum PositionKeyListMap{
        nightersKeyListMap(PositionTracker.nightersKeyListMap),
        wieldersKeyListMap(PositionTracker.wieldersKeyListMap),
        monstersKeyListMap(PositionTracker.monstersKeyListMap),
        civiliansKeyListMap(PositionTracker.civiliansKeyListMap);

        private KeyListMap <Integer, Entity> KeyListMap;
        PositionKeyListMap(KeyListMap<Integer,Entity> KeyListMap){
            this.KeyListMap = KeyListMap;
        }
        public KeyListMap<Integer,Entity> getKeyListMap(){
            return KeyListMap;
        }
    }
    private static KeyListMap<Integer,Entity> nightersKeyListMap = new KeyListMap<Integer, Entity>();
    private static KeyListMap<Integer,Entity> wieldersKeyListMap = new KeyListMap<Integer, Entity>();
    private static KeyListMap<Integer,Entity> monstersKeyListMap = new KeyListMap<Integer, Entity>();
    private static KeyListMap<Integer,Entity> civiliansKeyListMap = new KeyListMap<Integer, Entity>();


    public static void updateKeyListMap(KeyListMap<Integer,Entity> biMap,Entity entity, float x, float y){

        int key=generateKey(x,y);

        biMap.remove(key,entity);
        biMap.put(key,entity);

        globalTracker.remove(key,entity);
        globalTracker.put(key,entity);
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
        nightersKeyListMap.clear();
        wieldersKeyListMap.clear();
        monstersKeyListMap.clear();
        civiliansKeyListMap.clear();
    }
}
