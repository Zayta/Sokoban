package snow.zhen.zayta.main.sokoban.movement;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import snow.zhen.zayta.main.GameConfig;
import snow.zhen.zayta.main.sokoban.entity.EntityType;
import snow.zhen.zayta.util.BiMap;
import snow.zhen.zayta.main.sokoban.entity.EntityBase;
import snow.zhen.zayta.main.sokoban.entity.units.Goal;

public class PositionTracker {
    private int mapWidth;
    private BiMap<Integer, snow.zhen.zayta.main.sokoban.entity.EntityBase> globalTracker;

//    private BiMap<Integer,Nighter> nighterTracker;
//    private BiMap<Integer, Crate> crateTracker;
//    private BiMap<Integer, Wall> wallTracker;
//    private BiMap<Integer, snow.zhen.zayta.main.sokoban.entity.units.Goal> goalTracker;
    private BiMap<EntityType,BiMap<Integer,EntityBase>> entityTracker;


    public PositionTracker(int mapWidth){
        globalTracker = new BiMap<Integer, snow.zhen.zayta.main.sokoban.entity.EntityBase>();
//        goalTracker = new BiMap<Integer, snow.zhen.zayta.main.sokoban.entity.units.Goal>();
        entityTracker = new BiMap<EntityType, BiMap<Integer,EntityBase>>();
        EntityType types[] = EntityType.values();
        System.out.println("Contents of the enum are: ");
        //Iterating enum using the for loop
        for(EntityType type: types) {
            entityTracker.put(type,new BiMap<Integer, EntityBase>());
        }
        this.mapWidth = mapWidth;
    }
    public void init(int mapWidth){
        clear();
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


    public void updateGlobalTracker(ArrayList<snow.zhen.zayta.main.sokoban.entity.EntityBase> entities){
        for(EntityBase entity:entities) {
            updateGlobalTracker(entity, entity.getX(), entity.getY());
            updateEntityTracker(entity,entity.getX(),entity.getY());
        }
    }
    private void updateGlobalTracker(snow.zhen.zayta.main.sokoban.entity.EntityBase entity, float x, float y) {

        globalTracker.removeKey(entity);
        int key=generateKey(x,y);

        globalTracker.put(key,entity);
    }
    private void updateEntityTracker(snow.zhen.zayta.main.sokoban.entity.EntityBase entity, float x, float y) {

        entityTracker.get(entity.getEntityType()).removeKey(entity);
        int key=generateKey(x,y);

        entityTracker.get(entity.getEntityType()).put(key,entity);
    }


    public snow.zhen.zayta.main.sokoban.entity.EntityBase getEntityAtPos(float x, float y){
        return globalTracker.get(generateKey(x,y));
    }
    public boolean isGoalPos(float x, float y){
        return entityTracker.get(EntityType.GOAL).get(generateKey(x,y))!=null;
    }

    public int getKeyForEntity(snow.zhen.zayta.main.sokoban.entity.EntityBase entityBase){
        return globalTracker.getKey(entityBase);
    }

    public EntityBase getEntityAtPos(Vector2 pos){
        return globalTracker.get(generateKey(pos.x,pos.y));
    }

    private void clear(){
        globalTracker.clear();
        for(EntityType entityType: entityTracker.keySet()){
            entityTracker.get(entityType).clear();
        }

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
//    public void updateGoalTracker(ArrayList<snow.zhen.zayta.main.sokoban.entity.units.Goal> goals){
//        for(snow.zhen.zayta.main.sokoban.entity.units.Goal goal: goals)
//            updateGoalPosition(goal,goal.getX(),goal.getY());
//    }
//    private void updateGoalPosition(Goal goal, float x, float y) {
//        int key=generateKey(x,y);
//        goalTracker.removeKey(goal);
//        goalTracker.put(key,goal);
//        updateGlobalTracker(goal,x,y);
//    }
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
