package exp.zhen.zayta.main.game.wake.input.standard;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;

import exp.zhen.zayta.main.game.wake.input.standard.controllers.FlingController;
import exp.zhen.zayta.main.game.wake.input.standard.controllers.KeyboardController;

public class StandardInputSystem extends EntitySystem {

    public StandardInputSystem(PooledEngine engine){
        setControllers(engine);
    }

    private void setControllers(PooledEngine engine){
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        if(Gdx.app.getType()==Application.ApplicationType.Desktop){
            inputMultiplexer.addProcessor(new KeyboardController(engine));
        }

        inputMultiplexer.addProcessor(new GestureDetector(new FlingController(engine)));

        Gdx.input.setInputProcessor(inputMultiplexer);
    }


}
