package exp.zhen.zayta.main.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;


public class TiledMapAssetDescriptors {
    /*Maps*/
    private static final String MAPS_MEMLAB1 = "game/experiment/maps/memLab/memLab1.tmx";
    public static final AssetDescriptor<TiledMap> MAP_MEMLAB1 = new AssetDescriptor<TiledMap>(MAPS_MEMLAB1,TiledMap.class);


    private static final String MAPS_MEMLAB2 = "game/experiment/maps/memLab/memLab2.tmx";
    public static final AssetDescriptor<TiledMap> MAP_MEMLAB2 = new AssetDescriptor<TiledMap>(MAPS_MEMLAB2,TiledMap.class);


    private static final String MAPS_MEMLABBIG = "game/experiment/maps/memLab/memLabBig.tmx";
    public static final AssetDescriptor<TiledMap> MAP_MEMLABBIG = new AssetDescriptor<TiledMap>(MAPS_MEMLABBIG,TiledMap.class);


    private static final String MAPS_IRONDALE = "game/experiment/maps/cities/irondale.tmx";
    public static final AssetDescriptor<TiledMap> MAP_IRONDALE = new AssetDescriptor<TiledMap>(MAPS_IRONDALE,TiledMap.class);

    private static final String MAPS_TILE_STORAGE = "game/experiment/maps/blank_map.tmx";
    public static final AssetDescriptor<TiledMap> MAP_TILE_STORAGE = new AssetDescriptor<TiledMap>(MAPS_TILE_STORAGE,TiledMap.class);



    public static final AssetDescriptor<TextureAtlas> MAP_GENERATOR =
            new AssetDescriptor<TextureAtlas>("game/experiment/maps/generated_map_tiles/generated_map_tiles.atlas", TextureAtlas.class);

}
