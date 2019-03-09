package exp.zhen.zayta.mode.quest.component.properties.movement;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import exp.zhen.zayta.mode.quest.PositionTracker;

public class Position implements Component{

    private final Entity entity;
    private float x,y;
//    private int key =0;

    public Position(Entity entity){
        this.entity = entity;
    }
    public Position(Entity entity, int initX, int initY){
        this.entity = entity;
        this.x = initX;
        this.y = initY;
    }
//    public void setEntity(Entity entity) {
//        this.entity = entity;
//    }

    public void update(float vx, float vy){
        x+=vx;
        y+=vy;
//        PositionTracker.updateBiMap(entity,x,y);
    }

    public void set(float x, float y)
    {
        this.x = x;
        this.y = y;
//        PositionTracker.updateBiMap(entity,x,y);
    }
//    private void updateBiMap()
//    {
//        PositionTracker.gameObjectBiMap.removeKey(entity);//todo since they updating the same biMap, cannot be used to detect collision.
//        key=Arrangements.generateKey(x,y);
//        PositionTracker.gameObjectBiMap.put(key,entity);
//    }

//    public int getKey(){return key;}

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
//        PositionTracker.updateBiMap(entity,x,y);
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
//        PositionTracker.updateBiMap(entity,x,y);
    }
}
