package exp.zhen.zayta.main.game.wake.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

import java.util.Hashtable;

import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.wake.assets.WPAssetDescriptors;
import exp.zhen.zayta.main.game.wake.assets.WPRegionNames;
import exp.zhen.zayta.main.game.wake.entity.Arrangements;
import exp.zhen.zayta.main.game.wake.movement.PositionTracker;

public class MapMaker {

    public static final String collisionLayer = "Collision Layer";

    private AssetManager assetManager;
//    private TiledMap [] tiledMaps;
    private Hashtable<Map,TiledMap> tiledMaps;
    public enum Map {
        irondale,memLab
    }
    //todo make list of tiled maps in the future
    public MapMaker (AssetManager assetManager){
        this.assetManager = assetManager;
        tiledMaps = new Hashtable<Map, TiledMap>();
        initTiledMaps();
    }

    public TiledMap getTiledMap(Map map) {
        return tiledMaps.get(map);
    }
    private void initTiledMaps(){
        tiledMaps.put(Map.memLab,assetManager.get(WPAssetDescriptors.MAP_MEMLAB2));
        tiledMaps.put(Map.irondale,assetManager.get(WPAssetDescriptors.MAP_IRONDALE));
    }


    public void generateMaze(){
//
//        int numStones = 5;
//        Vector2[] points = Arrangements.circle(numStones,SizeManager.WAKE_WORLD_CENTER_X,SizeManager.WAKE_WORLD_CENTER_Y,SizeManager.WAKE_WORLD_WIDTH/3);
//        for(int i =0; i<numStones; i++)
//        {
//            int key = PositionTracker.generateKey(points[i].x,points[i].y);
//            stonesBiMap.put(key,makeStone(points[i].x,points[i].y, exp.zhen.zayta.main.game.wake.mission.stone_gathering.StoneTag.class,WPRegionNames.STONE));
//        }
    }

}
