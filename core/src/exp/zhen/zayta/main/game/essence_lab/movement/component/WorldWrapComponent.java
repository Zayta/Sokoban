package exp.zhen.zayta.main.game.essence_lab.movement.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;


public class WorldWrapComponent implements Component {
    private Rectangle boundsOfMovement = new Rectangle();

    public void setBoundsOfMovement(Rectangle boundsOfMovement) {
        this.boundsOfMovement.set(boundsOfMovement);
    }
    public float getTop(){
        return boundsOfMovement.y+boundsOfMovement.height;
    }
    public float getBottom(){return boundsOfMovement.y;}
    public float getLeft(){
        return boundsOfMovement.x;
    }
    public float getRight(){
        return boundsOfMovement.x+boundsOfMovement.width;
    }

}
