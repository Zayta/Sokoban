package exp.zhen.zayta.main.game.wake.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Logger;

import java.util.Hashtable;

import exp.zhen.zayta.main.UIAssetDescriptors;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.wake.assets.WPAssetDescriptors;
import exp.zhen.zayta.main.game.wake.map.my_generated_map.MapGenerator;
//import exp.zhen.zayta.main.game.wake.map.my_generated_map.World;

public class MapMaker {

    private static final Logger log = new Logger(MapMaker.class.getName(),Logger.DEBUG);

    public static final String collisionLayer = "Collision Layer";
    private final MapGenerator mapGenerator;

    private AssetManager assetManager;
//    private TiledMap [] tiledMaps;
    private Hashtable<Map,TiledMap> tiledMaps;
    public enum Map {
        irondale,memLab
    }
    //todo make list of tiled maps in the future
    public MapMaker (AssetManager assetManager){
        this.assetManager = assetManager;
        mapGenerator = new MapGenerator((int)SizeManager.WAKE_WORLD_WIDTH,(int)SizeManager.WAKE_WORLD_HEIGHT,assetManager.get(WPAssetDescriptors.MAP_GENERATOR),assetManager.get(WPAssetDescriptors.MAP_TILE_STORAGE));
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

    public TiledMap generateMap(){
//        return new World((int) SizeManager.WAKE_WORLD_WIDTH,(int) SizeManager.WAKE_WORLD_HEIGHT, assetManager.get(UIAssetDescriptors.MAP_GENERATOR));
        return mapGenerator.generateWorld();
    };

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
