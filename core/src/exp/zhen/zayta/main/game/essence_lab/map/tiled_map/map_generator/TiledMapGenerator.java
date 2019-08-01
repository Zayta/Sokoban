package exp.zhen.zayta.main.game.essence_lab.map.tiled_map.map_generator;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.utils.Logger;

import java.util.Hashtable;

import exp.zhen.zayta.main.game.essence_lab.map.MapMaker;
import exp.zhen.zayta.util.GdxUtils;

public class TiledMapGenerator {
    private static final Logger log = new Logger(TiledMapGenerator.class.getName(),Logger.DEBUG);
    private final String COLLISION_LAYER = "collision_tile_set";
    
	private TextureAtlas mapGeneratorAtlas;
	float waterThreshold = 0.2f;
	private int worldWidth,worldHeight;
	private TiledMap tileStorage;//todo currently don't know how to directly load tileSets from code besides putting them in a tiledmap and loading the map itself
    private Hashtable<String,Integer> tileSetOffsets;
	public TiledMapGenerator(int worldWidth, int worldHeight, TextureAtlas mapGeneratorAtlas, TiledMap tileStorage) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.mapGeneratorAtlas = mapGeneratorAtlas;
        this.tileStorage = tileStorage;
        initTileSetOffsets();
        printTileSetNames();
    }

	private void initTileSetOffsets(){
	    tileSetOffsets = new Hashtable<String, Integer>();
        TiledMapTileSets tiledMapTileSets = tileStorage.getTileSets();
        int prevOffset = 0;
        for(TiledMapTileSet tiledMapTileSet: tiledMapTileSets){
            tileSetOffsets.put(tiledMapTileSet.getName(),prevOffset);
            prevOffset+=tiledMapTileSet.size();
        }
    }

    private TiledMapTile getRandomTile(TiledMapTileSet tileSet){

	    String tileSetKey = tileSet.getName();
	    TiledMapTile tile = tileSet.getTile(GdxUtils.RANDOM.nextInt(tileSet.size())+tileSetOffsets.get(tileSetKey)+1);

	    return tile;
    }

    public TiledMap generateWorld(MapType mapType){
        TiledMap tiledMap = new TiledMap();

        tiledMap.getProperties().put("width",worldWidth);
        tiledMap.getProperties().put("height",worldHeight);


        MapLayers layers = tiledMap.getLayers();
        
        if(mapType!=MapType.NONE)
            layers.add(visibleLayer(mapType));
        
        layers.add(collisionLayer());
        
//        boolean [][] landOrWater = getLandOrWaterUnits(worldWidth,worldHeight);//true = land


        return tiledMap;
    }
    
    private MapLayer collisionLayerWithObjects(){
	    MapLayer collisionLayer = new MapLayer();
	    collisionLayer.getObjects().add(new MapObject());

	    return collisionLayer;
    }

    private TiledMapTileLayer collisionLayer(){

        TiledMapTileSet tileSet = tileStorage.getTileSets().getTileSet(COLLISION_LAYER);


        TiledMapTileLayer collisionLayer = new TiledMapTileLayer(worldWidth,worldHeight,16,16);
        collisionLayer.setName(MapMaker.collisionLayer);

        for(int i = 0; i<worldWidth;i++){
            for(int j = 0; j<worldHeight;j++){
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(getRandomTile(tileSet));
                collisionLayer.setCell(i,j,cell);
            }
        }
        return collisionLayer;
    }
    
    //todo in the future refine visibleLayer more. Rn it is made of random tiles, some of which don't make sense together.
    
    private TiledMapTileLayer visibleLayer(MapType mapType){

        TiledMapTileSet tileSet = tileStorage.getTileSets().getTileSet(mapType.getTileSetName());


        TiledMapTileLayer visibleLayer = new TiledMapTileLayer(worldWidth,worldHeight,16,16);
        
        visibleLayer.setName(MapMaker.visibleLayer);
        for(int i = 0; i<worldWidth;i++){
            for(int j = 0; j<worldHeight;j++){
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(getRandomTile(tileSet));
                visibleLayer.setCell(i,j,cell);
            }
        }
        return visibleLayer;
    }

//
//	private boolean [][] getLandOrWaterUnits(int worldWidth, int worldHeight) {
//		// Sometimes simplex noise creates water worlds/land worlds. Make sure there's a bit of both.
//		double percentWater;
//		boolean [][] isLand;
//		do {
//			isLand = generateLandOrWaterUnits(worldWidth,worldHeight);
//			percentWater = calcPercentWater(
//					generateLandOrWaterUnits(worldWidth,worldHeight));
//		} while (percentWater <10 || percentWater > 80);
//		return isLand;
//	}
//
//	private boolean[][] generateLandOrWaterUnits(int worldWidth, int worldHeight) {
//		boolean[][] freeLand; // true = land.
//		// Simplex noise gen
//		int n = GdxUtils.RANDOM.nextInt(3) + 1; //2-7
//
//		float[] freqs = new float[n];
//		float[] amps  = new float[n];
//
//		for (int i = 0; i < n; i++) {
//			freqs[i] = GdxUtils.RANDOM.nextFloat()*15 + 7;
//			amps[i] = GdxUtils.RANDOM.nextFloat()*1000;
//		}
//
//		SimplexNoise simplexNoise = new SimplexNoise (worldWidth, worldHeight,freqs,amps);
//
//		float[][] fDryArea = simplexNoise.getMap();
//
//		freeLand = thresholdDryArea(fDryArea);
//		return freeLand;
//	}
//
//	private boolean[][] thresholdDryArea(float[][] dryArea) {
//		boolean[][] thresholdedDryArea = new boolean[dryArea.length][dryArea[0].length];
//
//		// Use the thresholds to fill in the return dryArea
//		for(int row = 0; row < dryArea.length; row++){
//			for(int col = 0; col < dryArea[row].length; col++){
//				thresholdedDryArea[row][col] = (dryArea[row][col] < waterThreshold);
//			}
//		}
//		return thresholdedDryArea;
//	}
//
//	private double calcPercentWater(boolean [][] dryArea) {
//		double percentWater = 0;
//
//		// Use the thresholds to fill in the return dryArea
//
//		for(int row = 0; row < dryArea.length; row++){
//			for(int col = 0; col < dryArea[row].length; col++){
//				if (!dryArea[row][col]) {
//					percentWater++;
//				}
//			}
//		}
//		percentWater/= (dryArea.length * dryArea[0].length);
//		percentWater *= 100;
//		return percentWater;
//	}

	/*for debug*/
    private void printTileSetNames(){
        log.debug("TileSet Names: ");
        for(TiledMapTileSet tiledMapTileSet: tileStorage.getTileSets()){
            log.debug("\n"+tiledMapTileSet.getName());
        }

    }

}