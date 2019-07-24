package exp.zhen.zayta.main.game.wake.render.mono_color;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

public class MonoColorComponent implements Component {
//    private Color color = new Color(255,165,0,1);
    private Color color = Color.ORANGE;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
