package snow.zhen.zayta.main.sokoban.movement;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import snow.zhen.zayta.main.GameConfig;
import snow.zhen.zayta.main.sokoban.entity.EntityType;
import snow.zhen.zayta.main.sokoban.entity.templates.EntityTemplate;
import snow.zhen.zayta.util.BiMap;

public class PositionTracker {
    private int mapDimension;
    private BiMap<Integer, EntityTemplate> globalTracker;

//    private BiMap<Integer,Nighter> nighterTracker;
//    private BiMap<Integer, Crate> crateTracker;
//    private BiMap<Integer, Wall> wallTracker;
//    private BiMap<Integer, snow.zhen.zayta.main.sokoban.entity.units.Goal> goalTracker;
    private BiMap<EntityType,BiMap<Integer, EntityTemplate>> entityTracker;


    public PositionTracker(int mapDimension){
        globalTracker = new BiMap<Integer, EntityTemplate>();
//        goalTracker = new BiMap<Integer, snow.zhen.zayta.main.sokoban.entity.units.Goal>();
        entityTracker = new BiMap<EntityType, BiMap<Integer, EntityTemplate>>();
        EntityType types[] = EntityType.values();
        System.out.println("Contents of the enum are: ");
        //Iterating enum using the for loop
        for(EntityType type: types) {
            entityTracker.put(type,new BiMap<Integer, EntityTemplate>());
        }
        this.mapDimension = mapDimension;
    }
    public void init(int mapDimension){
        clear();
//        nighterTracker.clear();
//        crateTracker.clear();
//        wallTracker.clear();
//        goalTracker.clear();

        this.mapDimension = mapDimension;
    }
    private int generateKey(float x, float y)
    {
        int n = mapDimension/ GameConfig.ENTITY_SIZE;
        return Math.round(x)*n+Math.round(y);
    }


    public void updateGlobalTracker(ArrayList<EntityTemplate> entities){
        for(EntityTemplate entity:entities) {
            updateGlobalTracker(entity, entity.getX(), entity.getY());
            updateEntityTracker(entity,entity.getX(),entity.getY());
        }
    }
    private void updateGlobalTracker(EntityTemplate entity, float x, float y) {

        globalTracker.removeKey(entity);
        int key=generateKey(x,y);

        globalTracker.put(key,entity);
    }
    private void updateEntityTracker(EntityTemplate entity, float x, float y) {

        entityTracker.get(entity.getEntityType()).removeKey(entity);
        int key=generateKey(x,y);

        entityTracker.get(entity.getEntityType()).put(key,entity);
    }


    public EntityTemplate getEntityAtPos(float x, float y){
        return globalTracker.get(generateKey(x,y));
    }
    public boolean isGoalPos(float x, float y){
        return entityTracker.get(EntityType.GOAL).get(generateKey(x,y))!=null;
    }

    public int getKeyForEntity(EntityTemplate entityTemplate){
        return globalTracker.getKey(entityTemplate);
    }

    public EntityTemplate getEntityAtPos(Vector2 pos){
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
