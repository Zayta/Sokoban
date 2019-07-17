package exp.zhen.zayta.main.game.wake.input;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.wake.game_mechanics.collision_mechanics.bomb_trigger.LandmineMaker;
import exp.zhen.zayta.main.game.wake.input.move_character.joystick.JoyStickMovementController;
import exp.zhen.zayta.main.game.wake.input.move_character.joystick.JoyStickMovementListener;
import exp.zhen.zayta.main.game.wake.input.move_character.FlingController;
import exp.zhen.zayta.main.game.wake.input.move_character.KeyboardController;

public class InputSystem extends EntitySystem {
    private static final Logger log = new Logger(InputSystem.class.getName(),Logger.DEBUG);

    private PooledEngine engine;
    private Viewport viewport;
    //joystick
    private Skin skin; private boolean enableJoystick = false;//todo joystick controller is a mess
    private Stage stage;
    public InputSystem(PooledEngine engine, Viewport viewport, Skin skin, TextureAtlas wakePlayAtlas){
        this.engine = engine;
        this.viewport = viewport;
        this.skin = skin;


        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        if(Gdx.app.getType()==Application.ApplicationType.Desktop){
            inputMultiplexer.addProcessor(new KeyboardController(engine,wakePlayAtlas));
//            /*for debug*/
//
//            stage = new Stage(viewport,new SpriteBatch());
//            stage.addActor(inputTable(skin,wakePlayAtlas));
//            inputMultiplexer.addProcessor(stage);
        }
        else {
            /**Movement**/
            if(enableJoystick) {
                stage = new Stage(viewport,new SpriteBatch());
                stage.addActor(joyStickMovementController(skin));
                inputMultiplexer.addProcessor(stage);
            }
            else {
                inputMultiplexer.addProcessor(new GestureDetector(new FlingController(engine)));
                //todo long hold for bomb.
            }
        }

        Gdx.input.setInputProcessor(inputMultiplexer);
    }



//    private Table inputTable(Skin skin, TextureAtlas wakePlayAtlas){
//        Table table = new Table(skin);
//        table.align(Align.bottomLeft);
//        table.setBounds(0, 0, viewport.getWorldWidth(), SizeManager.CONTROLLER_DIAMETER);
//        //movement
//        table.add(joyStickMovementController(skin));
//        /**Bomb planting**/
//        table.add(landmineButton(engine,wakePlayAtlas)).align(Align.right);
//        return table;
//    }

    private Button landmineButton(PooledEngine engine, TextureAtlas wakePlayAtlas){
        Button landmineButton = new Button(skin);
        landmineButton.setBounds(SizeManager.CONTROLLER_DIAMETER,0,SizeManager.CONTROLLER_DIAMETER,SizeManager.CONTROLLER_DIAMETER);
        landmineButton.addListener(new LandmineMaker(engine,wakePlayAtlas));
        return landmineButton;
    }

    private JoyStickMovementController joyStickMovementController(Skin skin){
        float backgroundDiameter = SizeManager.CONTROLLER_DIAMETER*0.9f;
        float knobToBackgroundRatio =0.5f;

        JoyStickMovementController joyStickMovementController = new JoyStickMovementController(20, skin);
//        joyStickMovementController.setBounds(0, 0, SizeManager.CONTROLLER_DIAMETER, SizeManager.CONTROLLER_DIAMETER);
        Touchpad.TouchpadStyle touchpadStyle = joyStickMovementController.getStyle();
        touchpadStyle.background.setRightWidth(backgroundDiameter);
        touchpadStyle.background.setTopHeight(backgroundDiameter);

        touchpadStyle.knob.setMinWidth(backgroundDiameter*knobToBackgroundRatio);
        touchpadStyle.knob.setMinHeight(backgroundDiameter*knobToBackgroundRatio);
        joyStickMovementController.addListener(new JoyStickMovementListener(engine));

        return joyStickMovementController;
    }








    @Override
    public void update(float deltaTime) {
        if(stage!=null) {
            stage.act(deltaTime);
            stage.getViewport().apply();
            stage.draw();
        }
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        if(stage!=null)
            stage.dispose();
    }




}
