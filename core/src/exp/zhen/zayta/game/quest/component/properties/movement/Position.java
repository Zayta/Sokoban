package exp.zhen.zayta.game.quest.component.properties.movement;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import exp.zhen.zayta.game.quest.PositionTracker;

public class Position implements Component{

    private float x,y;
//    public Position(float initX, float initY){
//        this.x = initX;
//        this.y = initY;
//
//    }

    public void update(float vx, float vy){
        x+=vx;
        y+=vy;
    }

    public void set(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
