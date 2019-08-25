package exp.zhen.zayta.main.game.experiment.engine.movement.component;

import com.badlogic.ashley.core.Component;

public class Position implements Component{
    //x and y are centerX and centerY??
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

    public void set(float centerX, float centerY)
    {
        this.x = centerX;
        this.y = centerY;
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
