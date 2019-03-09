package exp.zhen.zayta.mode.quest.component.properties.movement;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Circle;

public class CircularBoundsComponent implements Component {

    private Circle bounds = new Circle();

    public void setBounds(float x, float y, float radius){
        bounds.set(x,y,radius);
    }
    public void setBounds(float x, float y){
        bounds.setPosition(x,y);
    }
    public Circle getBounds() {
        return bounds;
    }

    public float getRadius(){
        return bounds.radius;
    }

    //    private Ellipse bounds = new Ellipse();
//
//    public void setBounds(float x, float y, float width, float height){
//        bounds.set(x,y,width,height);
//    }
//    public void setBounds(float x, float y){
//        bounds.setPosition(x,y);
//    }
    public float getX(){return bounds.x;}
    public float getY(){return bounds.y;}
//
//    public Ellipse getBounds() {
//        return bounds;
//    }
//    public float getWidth(){
//        return bounds.width;
//    }
//    public float getHeight(){
//        return bounds.height;
//    }
//    public void setBounds(Ellipse bounds) {
//        this.bounds = bounds;
//    }
}
