package exp.zhen.zayta.main.game.wake.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;

import exp.zhen.zayta.assets.AssetDescriptors;

public class MapMaker {
    private AssetManager assetManager;
    private TiledMap [] tiledMaps;
    //todo make list of tiled maps in the future
    public MapMaker (AssetManager assetManager){
        this.assetManager = assetManager;
        tiledMaps = new TiledMap[30];
        tiledMaps[0] = assetManager.get(AssetDescriptors.MAP_MEMLAB2);
    }

    public TiledMap getTiledMap(int lvl) {
        if(lvl<tiledMaps.length)
            return tiledMaps[lvl];
        else
            return tiledMaps[tiledMaps.length-1];
    }
}
