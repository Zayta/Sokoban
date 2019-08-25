package exp.zhen.zayta.main.game.experiment.engine.movement.component;

import com.badlogic.ashley.core.Component;

public class DimensionComponent implements Component {
    private float width,height;

    public void set(float width, float height)
    {this.width = width;this.height = height;}

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
