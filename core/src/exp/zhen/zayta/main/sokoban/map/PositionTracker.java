package exp.zhen.zayta.main.sokoban.map;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import exp.zhen.zayta.main.GameConfig;
import exp.zhen.zayta.main.sokoban.entity.EntityBase;
import exp.zhen.zayta.main.sokoban.entity.units.Goal;
import exp.zhen.zayta.util.BiMap;

public class PositionTracker {
    private int mapWidth;
    private BiMap<Integer, EntityBase> globalTracker;

//    private BiMap<Integer,Nighter> nighterTracker;
//    private BiMap<Integer, Crate> crateTracker;
//    private BiMap<Integer, Wall> wallTracker;
    private BiMap<Integer, Goal> goalTracker;


    public PositionTracker(int mapWidth){
        globalTracker = new BiMap<Integer, EntityBase>();
        goalTracker = new BiMap<Integer, Goal>();
        this.mapWidth = mapWidth;
    }
    public void init(int mapWidth){
        globalTracker.clear();
//        nighterTracker.clear();
//        crateTracker.clear();
//        wallTracker.clear();
//        goalTracker.clear();
        this.mapWidth = mapWidth;
    }
    private int generateKey(float x, float y)
    {
        int n = mapWidth/ GameConfig.ENTITY_SIZE;
        return Math.round(x)*n+Math.round(y);
    }

    public void updateGlobalTracker(ArrayList<EntityBase> entities){
        for(EntityBase entity:entities)
            updateGlobalTracker(entity, entity.getX(),entity.getY());
    }
    public void updateGlobalTracker(EntityBase entity, float x, float y) {

        globalTracker.removeKey(entity);
        int key=generateKey(x,y);

        globalTracker.put(key,entity);
    }


    public EntityBase getEntityAtPos(float x, float y){
        return globalTracker.get(generateKey(x,y));
    }
    public int getKeyForEntity(EntityBase entityBase){
        return globalTracker.getKey(entityBase);
    }

    public EntityBase getEntityAtPos(Vector2 pos){
        return globalTracker.get(generateKey(pos.x,pos.y));
    }

    private void clear(){
        globalTracker.clear();
    }

//    //updates all character positions
//    public void updateCharacterTracker(ArrayList<Nighter> characters){
//        for(Nighter character: characters)
//            updateCharacterPosition(character,character.getX(),character.getY());
//    }
//    private void updateCharacterPosition(Nighter character, float x, float y) {
//        int key=generateKey(x,y);
//        nighterTracker.removeKey(entity);
//        nighterTracker.put(key,character);
//        updateGlobalTracker(character,x,y);
//    }
//
//    //updates all crate positions
//    public void updateCrateTracker(ArrayList<Crate> crates){
//        for(Crate crate: crates)
//            updateCratePosition(crate,crate.getX(),crate.getY());
//    }
//    private void updateCratePosition(Crate crate, float x, float y) {
//        int key=generateKey(x,y);
//        crateTracker.removeKey(entity);
//        crateTracker.put(key,crate);
//        updateGlobalTracker(crate,x,y);
//    }
//
    public void updateGoalTracker(ArrayList<Goal> goals){
        for(Goal goal: goals)
            updateGoalPosition(goal,goal.getX(),goal.getY());
    }
    private void updateGoalPosition(Goal goal, float x, float y) {
        int key=generateKey(x,y);
        goalTracker.removeKey(goal);
        goalTracker.put(key,goal);
        updateGlobalTracker(goal,x,y);
    }
//    private void updateWallPosition(Wall wall, float x, float y) {
//        int key=generateKey(x,y);
//        wallTracker.removeKey(entity);
//        wallTracker.put(key,wall);
//        updateGlobalTracker(wall,x,y);
//    }

    @Override
    public String toString(){
        return globalTracker.toString();
    }
    

}
