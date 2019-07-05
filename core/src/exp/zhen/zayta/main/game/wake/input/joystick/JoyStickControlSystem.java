package exp.zhen.zayta.main.game.wake.input.joystick;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.main.game.config.SizeManager;

public class JoyStickControlSystem extends EntitySystem {
    private PooledEngine engine;

    private Stage stage;
    private JoyStickController joyStickController;


    public JoyStickControlSystem(PooledEngine engine, Viewport viewport, Batch batch,Skin skin){
        this.engine = engine;
        stage = new Stage(viewport,new SpriteBatch());
        initTouchpad(skin);
        stage.addActor(joyStickController);

        Gdx.input.setInputProcessor(stage);

    }
    private void initTouchpad(Skin skin){
        float backgroundDiameter = SizeManager.CONTROLLER_DIAMETER*0.9f;
        float knobToBackgroundRatio =0.5f;

        joyStickController = new JoyStickController(20, skin);
        joyStickController.setBounds(0, 0, SizeManager.CONTROLLER_DIAMETER, SizeManager.CONTROLLER_DIAMETER);
        Touchpad.TouchpadStyle touchpadStyle = joyStickController.getStyle();
        touchpadStyle.background.setRightWidth(backgroundDiameter);
        touchpadStyle.background.setTopHeight(backgroundDiameter);

        touchpadStyle.knob.setMinWidth(backgroundDiameter*knobToBackgroundRatio);
        touchpadStyle.knob.setMinHeight(backgroundDiameter*knobToBackgroundRatio);
        joyStickController.addListener(new JoyStickListener(engine));

    }

    @Override
    public void update(float deltaTime) {
        stage.act(deltaTime);
        stage.getViewport().apply();
        stage.draw();
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        stage.dispose();
    }
}
