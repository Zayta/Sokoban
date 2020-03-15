package exp.zhen.zayta.main.sokoban.map;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.Arrays;

import exp.zhen.zayta.main.assets.RegionNames;
import exp.zhen.zayta.main.sokoban.entity.EntityBase;
import exp.zhen.zayta.main.sokoban.entity.Wall;

import static exp.zhen.zayta.main.GameConfig.TILE_SIZE;


public class Map {


    private Levels levels;
    private ArrayList<EntityBase> entities;
    private ArrayList<Character> characters;
    private ArrayList<Wall> walls;

    // bullet pool.
    private final Pool<EntityBase> bulletPool = new Pool<EntityBase>() {

        @Override
        protected EntityBase newObject() {
            return null;
        }
    };

    //this is specific to the map being parsed
    private final char FLOOR_ID     = ' ';
    private final char WALL_ID      = '#';
    private final char GOAL_ID     = '.';
    private final char CRATE_ID     = '$';
    private final char SOKOBAN_ID   = '@';
    private final char CRATE_ON_GOAL_ID = '*';
    private final char SOKOBAN_ON_GOAL_ID = '+';

    private int mapWidth, mapHeight;

    private TextureAtlas sokobanAtlas;
    private TextureRegion spriteRegion;
    public Map(TextureAtlas sokobanAtlas){
        this.sokobanAtlas = sokobanAtlas;
        this.levels = new Levels();

        spriteRegion = sokobanAtlas.findRegion(RegionNames.LORALE);
        System.out.println("SpriteRegion is "+spriteRegion);

    }

    public void init(int lvl){
        Levels.Level level = levels.getLevel(lvl);
        mapWidth = level.getWidth();
        mapHeight = level.getHeight();


        char[] mapData = level.getLvlData().toCharArray();
        System.out.println("Map data is "+ Arrays.toString(mapData));
        System.out.println("Map data length is "+mapData.length);
        System.out.println("Map width is "+mapWidth);
        System.out.println("Map height is "+mapHeight);
        createEntities(mapData);
    }

    private void createEntities(char [] mapData){
        for(int i = 0; i < mapWidth; i++){
            for(int j = 0; j<mapHeight; j++){
                char id = mapData[i+j*mapWidth];
                float x = i * TILE_SIZE;
                float y = j * TILE_SIZE;
                addEntity(id,x,y);
            }
        }

    }
    private void addEntity(char id, float x, float y){
        switch (id){
            case FLOOR_ID:
                break;
            case WALL_ID:
                break;
            case GOAL_ID:
                break;
            case CRATE_ID:
                break;
            case SOKOBAN_ID:
                break;
            case CRATE_ON_GOAL_ID:
                break;
            case SOKOBAN_ON_GOAL_ID:
                break;
        }
    }

}
