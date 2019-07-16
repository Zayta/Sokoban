package exp.zhen.zayta.main.game.wake.movement.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

public class RectangularBoundsComponent implements Component {

    private Rectangle bounds = new Rectangle();
//    public void setBounds(float centerX, float centerY, float width, float height){
//        bounds.set(centerX,centerY,width,height);
//    }
    public void setBounds(float x, float y, float width, float height){
        bounds.set(x,y,width,height);
    }
    public void setBounds(float left, float bottom){
        bounds.setPosition(left,bottom);
    }
//    public void setBounds(float centerX, float centerY){
//        bounds.setPosition(centerX-getWidth()/2,centerY-getHeight()/2);
//    }
//    public void setBounds(float left, float bottom, float width, float height){
//        bounds.set(left,bottom,width,height);
//    }
//    public void setBounds(float left, float bottom){
//        bounds.setPosition(left,bottom);
//    }
    public Rectangle getBounds() {
        return bounds;
    }

    //x is left and y is bottom
    public float getX(){return bounds.x;}
    public float getY(){return bounds.y;}

    public float getWidth(){
        return bounds.width;
    }
    public float getHeight(){
        return bounds.height;
    }

    public float getCenterX(){
        return bounds.x+bounds.width/2;
    }
    public float getCenterY(){
        return bounds.y+bounds.height/2;
    }
    public float getLeft(){
        return getX()-bounds.width;
    }
    public float getBottom(){
        return getY()-bounds.height;
    }
    public float getTop(){
        return bounds.y+bounds.height;
    }
    public float getRight(){
        return bounds.x+bounds.width;
    }
}
