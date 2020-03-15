package exp.zhen.zayta.main.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import exp.zhen.zayta.main.sokoban.map.Map;

public class PlayController extends InputAdapter {

    private Map map;
    public PlayController(TextureAtlas sokobanAtlas){
        map = new Map(sokobanAtlas);
        Gdx.input.setInputProcessor(this);

    }


    public void update(float delta){

    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode== Input.Keys.LEFT){
            //player moves left
        }
        if(keycode==Input.Keys.RIGHT){
        }
        if(keycode==Input.Keys.UP){
        }
        if(keycode==Input.Keys.DOWN){
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        //player stops moving
        return true;
    }

}
