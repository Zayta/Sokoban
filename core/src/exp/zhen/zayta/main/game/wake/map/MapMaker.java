package exp.zhen.zayta.main.game.wake.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;

import java.util.Hashtable;

import exp.zhen.zayta.main.game.wake.assets.WPAssetDescriptors;

public class MapMaker {
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

}
