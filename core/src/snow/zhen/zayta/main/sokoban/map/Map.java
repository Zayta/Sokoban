package snow.zhen.zayta.main.sokoban.map;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;
import java.util.Arrays;

import snow.zhen.zayta.main.sokoban.entity.CharacterName;
import snow.zhen.zayta.main.sokoban.entity.units.Crate;
import snow.zhen.zayta.main.sokoban.entity.EntityBase;
import snow.zhen.zayta.main.sokoban.entity.EntityBuilder;
import snow.zhen.zayta.main.sokoban.entity.units.Goal;
import snow.zhen.zayta.main.sokoban.entity.units.Wall;
import snow.zhen.zayta.main.sokoban.entity.units.Nighter;

import static snow.zhen.zayta.main.GameConfig.VIRTUAL_HEIGHT;
import static snow.zhen.zayta.main.GameConfig.VIRTUAL_WIDTH;


public class Map {

    //temporarily only Tenyu is available
    private CharacterName characterName = CharacterName.TENYU;

    private snow.zhen.zayta.main.sokoban.map.Levels levels;
    private int curLvl =0;
    //entities
    private ArrayList<Nighter> nighters;
    private ArrayList<Wall> walls;
    private ArrayList<Crate> crates;
    private ArrayList<Goal> goals;
    private ArrayList<EntityBase> entities;
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
        this.levels = new snow.zhen.zayta.main.sokoban.map.Levels();
        nighters = new ArrayList<Nighter>();
        crates = new ArrayList<Crate>();
        goals = new ArrayList<Goal>();
        walls = new ArrayList<Wall>();
        entities = new ArrayList<EntityBase>();
        entityBuilder = new EntityBuilder(sokobanAtlas);
    }

    public void init(int lvl){
        //clean previous lvl data
        nighters.clear();
        walls.clear();
        crates.clear();
        goals.clear();
        entities.clear();

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
        System.out.println("num Map goals = "+getNumGoals());
    }

    private void createEntities(char [] mapData){
        for(int i = 0; i < mapWidth; i++){
            for(int j = 0; j<mapHeight; j++){
                char id = mapData[i+j*mapWidth];
                float x = i+(VIRTUAL_WIDTH-mapWidth)/2;
                float y = j+(VIRTUAL_HEIGHT-mapHeight+1)/2;
                addEntity(id,x,y);
            }
        }
        entities.addAll(goals);//goals should be added first cuz render bottom
        entities.addAll(nighters);
        entities.addAll(crates);
        entities.addAll(walls);

    }
    private void addEntity(char id, float x, float y){
        switch (id){
            case FLOOR_ID:
                break;
            case WALL_ID:
                walls.add(entityBuilder.buildWall(x,y,curLvl));
                break;
            case GOAL_ID:
                goals.add(entityBuilder.buildGoal(x,y));
                break;
            case CRATE_ID:
                crates.add(entityBuilder.buildCrate(x,y, Crate.State.NORMAL,curLvl));
                break;
            case SOKOBAN_ID:
                nighters.add(entityBuilder.getCharacter(characterName,x,y));
//                System.out.println("At map, nighter x y were "+x+", "+y);
                break;
            case CRATE_ON_GOAL_ID:
                goals.add(entityBuilder.buildGoal(x,y));
                crates.add(entityBuilder.buildCrate(x,y, Crate.State.IN_GOAL,curLvl));
                break;
            case SOKOBAN_ON_GOAL_ID:
                goals.add(entityBuilder.buildGoal(x,y));
                nighters.add(entityBuilder.getCharacter(characterName,x,y));
                break;
        }
    }

    public int getNumGoals(){
        return goals.size();
    }
    public ArrayList<EntityBase> getEntities(){
        return entities;
    }

    public ArrayList<Nighter> getNighters() {
        return nighters;
    }


    public ArrayList<Wall> getWalls() {
        return walls;
    }


    public ArrayList<Crate> getCrates() {
        return crates;
    }
    public ArrayList<Goal> getGoals() {
        return goals;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

}
