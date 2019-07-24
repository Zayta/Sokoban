package exp.zhen.zayta.main.game.wake.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import java.util.Hashtable;

import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.wake.assets.WPAssetDescriptors;
import exp.zhen.zayta.main.game.wake.map.util.Arrangements;
import exp.zhen.zayta.main.game.wake.map.tiled_map.map_generator.MapGenerator;
import exp.zhen.zayta.main.game.wake.map.tiled_map.map_generator.MapType;
import exp.zhen.zayta.util.GdxUtils;

public class MapMaker {


//    private static float mapBoundmaxX = SizeManager.WAKE_WORLD_WIDTH - SizeManager.maxObjWidth,
//                    mapBoundmaxY = SizeManager.WAKE_WORLD_HEIGHT - SizeManager.maxObjHeight;
    private static Rectangle mapBounds = new Rectangle(0,0,SizeManager.WAKE_WORLD_WIDTH - SizeManager.maxObjWidth,SizeManager.WAKE_WORLD_HEIGHT - SizeManager.maxObjHeight);

    private static final Logger log = new Logger(MapMaker.class.getName(),Logger.DEBUG);

    public static final String collisionLayer = "Collision Layer";
    public static final String visibleLayer = "Visible Layer";

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
        mapGenerator = new MapGenerator((int)mapBounds.width,(int)mapBounds.height,assetManager.get(WPAssetDescriptors.MAP_GENERATOR),assetManager.get(WPAssetDescriptors.MAP_TILE_STORAGE));
        tiledMaps = new Hashtable<Map, TiledMap>();
        initTiledMaps();
    }

    public TiledMap getTiledMap(Map map) {
        TiledMap tiledMap = tiledMaps.get(map);
        setMapBounds(tiledMap);
        return tiledMap;
    }
    private void initTiledMaps(){
        tiledMaps.put(Map.memLab,assetManager.get(WPAssetDescriptors.MAP_MEMLAB2));
        tiledMaps.put(Map.irondale,assetManager.get(WPAssetDescriptors.MAP_IRONDALE));
    }

    public TiledMap generateMap(){
//        return new World((int) SizeManager.WAKE_WORLD_WIDTH,(int) SizeManager.WAKE_WORLD_HEIGHT, assetManager.get(UIAssetDescriptors.MAP_GENERATOR));
        TiledMap tiledMap = mapGenerator.generateWorld(MapType.NONE);
        setMapBounds(tiledMap);
        return tiledMap;
    };

    private void setMapBounds(TiledMap tiledMap){
        MapProperties mapProperties = tiledMap.getProperties();
        setMapBoundmaxX(mapProperties.get("width", Integer.class)-SizeManager.maxObjWidth);
        setMapBoundmaxY(mapProperties.get("height", Integer.class)-SizeManager.maxObjHeight);
    }

    private void setMapBoundmaxX(float mapBoundmaxX) {
        mapBounds.width = mapBoundmaxX;
    }

    private void setMapBoundmaxY(float mapBoundmaxY) {
        mapBounds.height = mapBoundmaxY;
    }

    public float getMapBoundmaxX() {
        return mapBounds.width;
    }

    public float getMapBoundmaxY() {
        return mapBounds.height;
    }

    public static Vector2[] generateRandomCoordinates(int numCoordinates){

        int maxX = (int)(mapBounds.width);
        int maxY = (int)(mapBounds.height);

        Circle circle = new Circle();
        float minDim = Math.min(SizeManager.maxObjWidth,SizeManager.maxObjHeight);

        do {
            float centerX = GdxUtils.RANDOM.nextInt(maxX)+GdxUtils.RANDOM.nextFloat();
            float centerY = GdxUtils.RANDOM.nextInt(maxY)+GdxUtils.RANDOM.nextFloat();
            float radius = GdxUtils.RANDOM.nextInt(Math.min(maxX, maxY))+GdxUtils.RANDOM.nextFloat();
            circle.set(centerX,centerY,radius);

        }while(!mapBounds.contains(circle)&&circle.radius<=minDim);


        Vector2[] points = Arrangements.circle(numCoordinates,circle.x,circle.y,circle.radius);
        return points;
    }

}
