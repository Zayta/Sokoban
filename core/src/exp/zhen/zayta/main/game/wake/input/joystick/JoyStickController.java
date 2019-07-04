package exp.zhen.zayta.main.game.wake.input.joystick;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class JoyStickController extends Touchpad {
    //todo rn it is exactly the same as touchpad
    public JoyStickController(float deadzoneRadius, Skin skin) {
        super(deadzoneRadius, skin);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
            validate();

            Color c = getColor();
            batch.setColor(c.r, c.g, c.b, c.a * parentAlpha);

            float x = getX();
            float y = getY();
            float w = getWidth();
            float h = getHeight();

            final Drawable bg = getStyle().background;
            if (bg != null) bg.draw(batch, x, y, w, h);

            final Drawable knob = getStyle().knob;
            if (knob != null) {

                x += getKnobX() - knob.getMinWidth() / 2f;
                y += getKnobY() - knob.getMinHeight() / 2f;
                knob.draw(batch, x, y, knob.getMinWidth(), knob.getMinHeight());
            }

    }
}
