package snow.zhen.zayta.main.sokoban.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import snow.zhen.zayta.main.sokoban.PlayController;
import snow.zhen.zayta.main.sokoban.movement.Direction;

public class KeyboardController extends InputAdapter {//controls keyboard input controls in the gameplay
    private PlayController entityController;
    public KeyboardController(PlayController entityController){
        this.entityController = entityController;
    }
    @Override
    public boolean keyDown(int keycode) {
        if(keycode== Input.Keys.LEFT){
            entityController.moveNighters(Direction.left);
        }
        if(keycode==Input.Keys.RIGHT){
            entityController.moveNighters(Direction.right);
        }
        if(keycode==Input.Keys.UP){
            entityController.moveNighters(Direction.up);
        }
        if(keycode==Input.Keys.DOWN){
            entityController.moveNighters(Direction.down);
        }
        if(keycode==Input.Keys.Z)
        {
            entityController.undoMove();
        }
        if(keycode==Input.Keys.E)
        {
            entityController.debug();
        }
        if(keycode==Input.Keys.R)
        {
            entityController.restart();
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        //player stops moving
        return true;
    }

}
