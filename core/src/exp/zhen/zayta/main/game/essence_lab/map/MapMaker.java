package exp.zhen.zayta.main.game.essence_lab.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import java.util.Hashtable;

import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.essence_lab.assets.WPAssetDescriptors;
import exp.zhen.zayta.main.game.essence_lab.map.util.Arrangements;
import exp.zhen.zayta.main.game.essence_lab.map.tiled_map.map_generator.TiledMapGenerator;
import exp.zhen.zayta.main.game.essence_lab.map.tiled_map.map_generator.MapType;
import exp.zhen.zayta.util.GdxUtils;

public class MapMaker {


//    private static float mapBoundmaxX = SizeManager.WAKE_WORLD_WIDTH - SizeManager.maxObjWidth,
//                    mapBoundmaxY = SizeManager.WAKE_WORLD_HEIGHT - SizeManager.maxObjHeight;
    private static Rectangle mapBounds = new Rectangle(0,0,SizeManager.WAKE_WORLD_WIDTH - SizeManager.maxObjWidth,SizeManager.WAKE_WORLD_HEIGHT - SizeManager.maxObjHeight);

    private static final Logger log = new Logger(MapMaker.class.getName(),Logger.DEBUG);

    public static final String collisionLayer = "Collision Layer";
    public static final String visibleLayer = "Visible Layer";

    private final TiledMapGenerator tiledMapGenerator;

    private AssetManager assetManager;
//    private TiledMap [] tiledMaps;
    private Hashtable<Map,TiledMap> tiledMaps;
    public enum Map {
        irondale,memLab
    }
    //todo make list of tiled maps in the future
    public MapMaker (AssetManager assetManager){
        this.assetManager = assetManager;
        tiledMapGenerator = new TiledMapGenerator((int)mapBounds.width,(int)mapBounds.height,assetManager.get(WPAssetDescriptors.MAP_GENERATOR),assetManager.get(WPAssetDescriptors.MAP_TILE_STORAGE));
        tiledMaps = new Hashtable<Map, TiledMap>();
        initTiledMaps();
        initMapBounds(0,0,SizeManager.WAKE_WORLD_WIDTH - SizeManager.maxObjWidth,SizeManager.WAKE_WORLD_HEIGHT - SizeManager.maxObjHeight);
    }

    private void initMapBounds(float left, float bottom, float width, float height) {
        MapMaker.mapBounds.set(left,bottom,width,height);
        Arrangements.initAvailableKeys(mapBounds.width,mapBounds.height);
    }

    public TiledMap getTiledMap(Map map) {
        TiledMap tiledMap = tiledMaps.get(map);
        setMapBounds(tiledMap);
//        Arrangements.initAvailableKeys((int)mapBounds.width,(int)mapBounds.height);
        return tiledMap;
    }
    private void initTiledMaps(){
        tiledMaps.put(Map.memLab,assetManager.get(WPAssetDescriptors.MAP_MEMLAB2));
        tiledMaps.put(Map.irondale,assetManager.get(WPAssetDescriptors.MAP_IRONDALE));
    }

    public TiledMap generateTiledMap(){
        TiledMap tiledMap = tiledMapGenerator.generateWorld(MapType.NONE);
        setMapBounds(tiledMap);
        return tiledMap;
    };

    private void setMapBounds(TiledMap tiledMap){
        MapProperties mapProperties = tiledMap.getProperties();
        setMapBoundmaxX(mapProperties.get("width", Integer.class)-SizeManager.maxObjWidth);
        setMapBoundmaxY(mapProperties.get("height", Integer.class)-SizeManager.maxObjHeight);
        Arrangements.initAvailableKeys(mapBounds.width,mapBounds.height);
    }

    private void setMapBoundmaxX(float mapBoundmaxX) {
        mapBounds.width = mapBoundmaxX;
    }

    private void setMapBoundmaxY(float mapBoundmaxY) {
        mapBounds.height = mapBoundmaxY;
    }

    public static float getMapBoundmaxX() {
        return mapBounds.width;
    }

    public static float getMapBoundmaxY() {
        return mapBounds.height;
    }
    public static Rectangle getMapBounds(){
      return mapBounds;
    }



}
