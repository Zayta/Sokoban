package exp.zhen.zayta.main.game.experiment.engine.entity.components.properties;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Pool;

public class ColorComponent implements Component,Pool.Poolable {


    private Color color = Color.WHITE;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void reset() {
        color = Color.WHITE;
    }
}
