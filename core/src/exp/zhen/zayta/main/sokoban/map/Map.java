package exp.zhen.zayta.main.sokoban.map;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;
import java.util.Arrays;

import exp.zhen.zayta.main.sokoban.entity.Crate;
import exp.zhen.zayta.main.sokoban.entity.Wall;

import static exp.zhen.zayta.main.GameConfig.TILE_SIZE;


public class Map {


    private Levels levels;
    private int curLvl =0;
    //entities
    private ArrayList<Character> characters;
    private ArrayList<Wall> walls;
    private ArrayList<Crate> crates;
    private ArrayList<Crate> goals;
    //textures
    private EntityBuilder entityBuilder;



    //this is specific to the map being parsed
    private final char FLOOR_ID     = ' ';
    private final char WALL_ID      = '#';
    private final char GOAL_ID     = '.';
    private final char CRATE_ID     = '$';
    private final char SOKOBAN_ID   = '@';
    private final char CRATE_ON_GOAL_ID = '*';
    private final char SOKOBAN_ON_GOAL_ID = '+';

    private int mapWidth,mapHeight;
    
    public Map(TextureAtlas sokobanAtlas){
        this.levels = new Levels();

        entityBuilder = new EntityBuilder(sokobanAtlas);
    }

    public void init(int lvl){
        //clean previous lvl data
        characters.clear();
        walls.clear();
        crates.clear();
        goals.clear();

        //set new lvl data
        curLvl = lvl;
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
                walls.add(entityBuilder.buildWall(x,y,curLvl));
                break;
            case GOAL_ID:
                break;
            case CRATE_ID:
                crates.add(entityBuilder.buildCrate(x,y,curLvl));
                break;
            case SOKOBAN_ID:
                break;
            case CRATE_ON_GOAL_ID:
                break;
            case SOKOBAN_ON_GOAL_ID:
                break;
        }
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }


    public ArrayList<Wall> getWalls() {
        return walls;
    }


    public ArrayList<Crate> getCrates() {
        return crates;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }
}
