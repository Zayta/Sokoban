package exp.zhen.zayta.main.game.wake.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

    private static final String MAPS_TILE_STORAGE = "gameplay/wakeplay/maps/blank_map.tmx";
    public static final AssetDescriptor<TiledMap> MAP_TILE_STORAGE = new AssetDescriptor<TiledMap>(MAPS_TILE_STORAGE,TiledMap.class);



    public static final AssetDescriptor<TextureAtlas> MAP_GENERATOR =
            new AssetDescriptor<TextureAtlas>("gameplay/wakeplay/maps/generated_map_tiles/generated_map_tiles.atlas", TextureAtlas.class);

}
