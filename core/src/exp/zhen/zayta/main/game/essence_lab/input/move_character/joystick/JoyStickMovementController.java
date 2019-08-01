package exp.zhen.zayta.main.game.essence_lab.input.move_character.joystick;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.essence_lab.input.InputSystem;

public class JoyStickMovementController extends Touchpad {
    private static final Logger log = new Logger(InputSystem.class.getName(),Logger.DEBUG);
    //todo rn it is exactly the same as touchpad
    public JoyStickMovementController(float deadzoneRadius, Skin skin) {
        super(deadzoneRadius, skin);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

}
