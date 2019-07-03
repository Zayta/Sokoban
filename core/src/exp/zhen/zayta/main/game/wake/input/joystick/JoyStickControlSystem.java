package exp.zhen.zayta.main.game.wake.input.joystick;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.main.game.config.SizeManager;

public class JoyStickControlSystem extends EntitySystem {
    private PooledEngine engine;

    private Stage stage;
    private Touchpad touchpad;
    private TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;


    public JoyStickControlSystem(PooledEngine engine, Viewport viewport, Batch batch,Skin skin){
        this.engine = engine;

        stage = new Stage(viewport,new SpriteBatch());
        initTouchpad(skin);
        stage.addActor(touchpad);

        Gdx.input.setInputProcessor(stage);

    }
    private void initTouchpad(Skin skin){
        touchpad = new Touchpad(10, skin);
        touchpad.setBounds(0, 0, 150, 150);

        touchpad.addListener(new JoyStickController(engine));

    }

    @Override
    public void update(float deltaTime) {
        stage.act(deltaTime);

        stage.draw();
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        stage.dispose();
    }
}
