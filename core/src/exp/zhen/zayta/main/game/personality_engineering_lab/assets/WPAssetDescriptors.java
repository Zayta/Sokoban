package exp.zhen.zayta.main.game.personality_engineering_lab.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;


public class WPAssetDescriptors {
    /*Maps*/
    private static final String MAPS_MEMLAB1 = "gameplay/personality_engineering_lab/maps/memLab/memLab1.tmx";
    public static final AssetDescriptor<TiledMap> MAP_MEMLAB1 = new AssetDescriptor<TiledMap>(MAPS_MEMLAB1,TiledMap.class);


    private static final String MAPS_MEMLAB2 = "gameplay/personality_engineering_lab/maps/memLab/memLab2.tmx";
    public static final AssetDescriptor<TiledMap> MAP_MEMLAB2 = new AssetDescriptor<TiledMap>(MAPS_MEMLAB2,TiledMap.class);


    private static final String MAPS_MEMLABBIG = "gameplay/personality_engineering_lab/maps/memLab/memLabBig.tmx";
    public static final AssetDescriptor<TiledMap> MAP_MEMLABBIG = new AssetDescriptor<TiledMap>(MAPS_MEMLABBIG,TiledMap.class);


    private static final String MAPS_IRONDALE = "gameplay/personality_engineering_lab/maps/cities/irondale.tmx";
    public static final AssetDescriptor<TiledMap> MAP_IRONDALE = new AssetDescriptor<TiledMap>(MAPS_IRONDALE,TiledMap.class);

    private static final String MAPS_TILE_STORAGE = "gameplay/personality_engineering_lab/maps/blank_map.tmx";
    public static final AssetDescriptor<TiledMap> MAP_TILE_STORAGE = new AssetDescriptor<TiledMap>(MAPS_TILE_STORAGE,TiledMap.class);



    public static final AssetDescriptor<TextureAtlas> MAP_GENERATOR =
            new AssetDescriptor<TextureAtlas>("gameplay/personality_engineering_lab/maps/generated_map_tiles/generated_map_tiles.atlas", TextureAtlas.class);

}
