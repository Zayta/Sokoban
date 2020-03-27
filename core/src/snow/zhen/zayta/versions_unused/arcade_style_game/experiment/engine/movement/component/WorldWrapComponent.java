package snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;


public class WorldWrapComponent implements Component {
    private Rectangle boundsOfMovement = new Rectangle();

    public void setBoundsOfMovement(float left, float bottom, float width, float height) {
        this.boundsOfMovement.set(left,bottom,width,height);
    }

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
