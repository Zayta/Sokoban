package snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

import snow.zhen.zayta.versions_unused.arcade_style_game.config.SpeedManager;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction;

public class VelocityComponent implements Component, Pool.Poolable {
    private snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction direction = Direction.none;
    private snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction prevDirection = Direction.none;
    private float xSpeed = SpeedManager.DEFAULT_SPEED, ySpeed = snow.zhen.zayta.versions_unused.arcade_style_game.config.SpeedManager.DEFAULT_SPEED;

    @Override
    public void reset() {
        direction = Direction.none;
    }
    public void setDirection(snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction direction){
        if(direction!= Direction.none)
            prevDirection = this.direction;
        this.direction = direction;
    }

    public snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction getDirection(){
        return direction;
    }

    public snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction getPrevDirection() {
        return prevDirection;
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
