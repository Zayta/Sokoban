package exp.zhen.zayta.main.game.wake.input;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;

import exp.zhen.zayta.main.game.wake.input.input_listeners.GestureInputHandler;
import exp.zhen.zayta.main.game.wake.input.input_listeners.KeyboardInputHandler;

public class InputSystem extends EntitySystem {

    public InputSystem(PooledEngine engine){
        setControllers(engine);
    }

    private void setControllers(PooledEngine engine){
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        if(Gdx.app.getType()==Application.ApplicationType.Desktop){
            inputMultiplexer.addProcessor(new KeyboardInputHandler(engine));
        }

        inputMultiplexer.addProcessor(new GestureDetector(new GestureInputHandler(engine)));

        Gdx.input.setInputProcessor(inputMultiplexer);
    }
}
