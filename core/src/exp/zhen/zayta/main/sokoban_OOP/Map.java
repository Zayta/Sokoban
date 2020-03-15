package exp.zhen.zayta.main.sokoban_OOP;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.Arrays;

import exp.zhen.zayta.main.assets.RegionNames;
import exp.zhen.zayta.main.sokoban_OOP.objects.GameObject;
import exp.zhen.zayta.main.sokoban_OOP.objects.gameobjects.Crate;
import exp.zhen.zayta.main.sokoban_OOP.objects.gameobjects.Floor;
import exp.zhen.zayta.main.sokoban_OOP.objects.gameobjects.Place;
import exp.zhen.zayta.main.sokoban_OOP.objects.gameobjects.Sokoban;
import exp.zhen.zayta.main.sokoban_OOP.objects.gameobjects.Wall;

import static exp.zhen.zayta.main.GameConfig.TILE_SIZE;

public class Map {

    private Levels levels;

    //number of layers in map
    private final int LAYERS = 5;

    //this is specific to the map being parsed
    private final char FLOOR_ID     = ' ';
    private final char WALL_ID      = '#';
    private final char PLACE_ID     = '.';
    private final char CRATE_ID     = '$';
    private final char SOKOBAN_ID   = '@';
    private final char CRATE_ON_PLACE_ID = '*';
    private final char SOKOBAN_ON_PLACE_ID = '+';

    private int mapWidth, mapHeight;

    private final Array<MapLayer> mapLayers;

    private TextureAtlas sokobanAtlas;
    private TextureRegion spriteRegion;
    public Map(TextureAtlas sokobanAtlas){
        this.sokobanAtlas = sokobanAtlas;
        levels = new Levels();

        this.mapLayers = new Array<MapLayer>(LAYERS);

        for(int i = 0; i<LAYERS; i++){
            this.mapLayers.add(new MapLayer());
        }
        spriteRegion = sokobanAtlas.findRegion(RegionNames.LORALE);
        System.out.println("SpriteRegion is "+spriteRegion);

    }

    public void create(int lvl){
        Levels.Level level = levels.getLevel(lvl);
        mapWidth = level.getWidth();
        mapHeight = level.getHeight();


        char[] mapData = level.getLvlData().toCharArray();
        System.out.println("Map data is "+Arrays.toString(mapData));
        System.out.println("Map data length is "+mapData.length);
        System.out.println("Map width is "+mapWidth);
        System.out.println("Map height is "+mapHeight);
        createLayers(mapData);


    }
    private void createLayers(char [] mapData){
        for(int layer = 0; layer < LAYERS; layer++){
            for(int i = 0; i < mapWidth; i++){
                for(int j = 0; j<mapHeight; j++){
                    char id = mapData[i+j*mapWidth];
                    float x = i * TILE_SIZE;
                    float y = j * TILE_SIZE;
                    addObject(layer,id,x,y);
                }
            }
        }
    }
    //adds an object with given id to a specific layer at pos (x,y)
    private void addObject(int layer, char id, float x, float y){
        if (layer == MapLayer.FLOOR) {
            addToLayer(MapLayer.FLOOR, new Floor(sokobanAtlas.findRegion(RegionNames.FLOOR_STONE), x, y));

        } else if (layer == MapLayer.WALL && id == WALL_ID) {
            addToLayer(MapLayer.WALL, new Wall(sokobanAtlas.findRegion(RegionNames.BRICK_STONE_CRATE), x, y));

        } else if (layer == MapLayer.PLACE && id == PLACE_ID) {
            addToLayer(MapLayer.PLACE, new Place(sokobanAtlas.findRegion(RegionNames.OVERLAY[2]), x, y));

        } else if (layer == MapLayer.CRATE && id == CRATE_ID) {
            addToLayer(MapLayer.CRATE, new Crate(
                    new Array<TextureRegion>(new TextureRegion[]{
                            sokobanAtlas.findRegion(RegionNames.CRATE_BLUE_NORMAL),
                            sokobanAtlas.findRegion(RegionNames.CRATE_BLUE_SOLVED)
                    }), x, y));

        }
        else if (layer == MapLayer.SOKOBAN && id == SOKOBAN_ID) {
            addToLayer(MapLayer.SOKOBAN, new Sokoban(spriteRegion,x, y));

        }
        else if (layer == MapLayer.CRATE && id == CRATE_ON_PLACE_ID) {
            addToLayer(MapLayer.PLACE, new Place(sokobanAtlas.findRegion(RegionNames.OVERLAY[3]), x, y));
            addToLayer(MapLayer.CRATE, new Crate(
                    new Array<TextureRegion>(new TextureRegion[]{
                        sokobanAtlas.findRegion(RegionNames.CRATE_BLUE_NORMAL),
                        sokobanAtlas.findRegion(RegionNames.CRATE_BLUE_SOLVED)
                    })
                    , x, y));
        } else if (layer == MapLayer.SOKOBAN && id == SOKOBAN_ON_PLACE_ID) {
            addToLayer(MapLayer.PLACE, new Place(sokobanAtlas.findRegion(RegionNames.OVERLAY[3]), x, y));
            addToLayer(MapLayer.SOKOBAN, new Sokoban(spriteRegion,x, y));
        }
    }

    private void addToLayer(int layerId, GameObject object) {
        MapLayer layer = getLayer(layerId);
        layer.addObject(object);
    }



    /*getters and setters*/
    public MapLayer getLayer(int id) {
        return mapLayers.get(id);
    }

    public Array<MapLayer> getMapLayers() {
        return mapLayers;
    }



    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

}
