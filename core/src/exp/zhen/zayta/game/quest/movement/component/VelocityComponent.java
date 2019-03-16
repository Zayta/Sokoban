package exp.zhen.zayta.game.quest.movement.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

import exp.zhen.zayta.game.quest.movement.Direction;
import exp.zhen.zayta.config.SpeedManager;

public class VelocityComponent implements Component, Pool.Poolable {
    private Direction direction = Direction.none;
    private float xSpeed = SpeedManager.DEFAULT_SPEED, ySpeed = SpeedManager.DEFAULT_SPEED;

    @Override
    public void reset() {
        direction = Direction.none;
    }
    public void setDirection(Direction direction){
        this.direction = direction;
    }
    public Direction getDirection(){
        return direction;
    }
    public float getVelX(){return direction.directionX*xSpeed;}
    public float getVelY(){return  direction.directionY*ySpeed;}

    public void setSpeed(float xSpeed,float ySpeed)
    {
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
//    public void setxSpeed(float xSpeed) {
//        this.xSpeed = xSpeed;
//    }
//
//    public void setySpeed(float ySpeed) {
//        this.ySpeed = ySpeed;
//    }
    //    private float velX, velY;
//
//    @Override
//    public void reset() {
//        velX = 0;
//        velY = 0;
//    }
//
//    public float getVelY() {
//        return velY;
//    }
//
//    public void setVelY(float velY) {
//        this.velY = velY;
//    }
//
//    public float getVelX() {
//        return velX;
//    }
//
//    public void setVelX(float velX) {
//        this.velX = velX;
//    }


}
