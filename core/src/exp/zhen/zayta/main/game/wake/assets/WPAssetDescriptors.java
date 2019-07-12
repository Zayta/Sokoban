package exp.zhen.zayta.main.game.wake.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;


public class WPAssetDescriptors {
    /*Maps*/
    private static final String MAPS_MEMLAB1 = "gameplay/wakeplay/maps/memLab/memLab1.tmx";
    public static final AssetDescriptor<TiledMap> MAP_MEMLAB1 = new AssetDescriptor<TiledMap>(MAPS_MEMLAB1,TiledMap.class);


    private static final String MAPS_MEMLAB2 = "gameplay/wakeplay/maps/memLab/memLab2.tmx";
    public static final AssetDescriptor<TiledMap> MAP_MEMLAB2 = new AssetDescriptor<TiledMap>(MAPS_MEMLAB2,TiledMap.class);


    private static final String MAPS_MEMLABBIG = "gameplay/wakeplay/maps/memLab/memLabBig.tmx";
    public static final AssetDescriptor<TiledMap> MAP_MEMLABBIG = new AssetDescriptor<TiledMap>(MAPS_MEMLABBIG,TiledMap.class);


    private static final String MAPS_IRONDALE = "gameplay/wakeplay/maps/cities/irondale.tmx";
    public static final AssetDescriptor<TiledMap> MAP_IRONDALE = new AssetDescriptor<TiledMap>(MAPS_IRONDALE,TiledMap.class);


    /*TileSets*/
    private static final String TILESET_MAP_GENERATOR_PATH = "gameplay/wakeplay/maps/generator_tile_set.tsx";
    public static final AssetDescriptor<TiledMapTileSet> TILESET_MAP_GENERATOR = new AssetDescriptor<TiledMapTileSet>(TILESET_MAP_GENERATOR_PATH,TiledMapTileSet.class);

}
