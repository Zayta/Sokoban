package exp.zhen.zayta.main.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;


import exp.zhen.zayta.main.sokoban.entity.EntityBase;
import exp.zhen.zayta.main.sokoban.map.Map;
import exp.zhen.zayta.main.sokoban.map.PositionTracker;

public class PlayController implements Updateable {

    //in-game
    private int curLvl=0;
    private Map map;
    private PositionTracker positionTracker;


    public PlayController(Map map){
        this.map = map;
        positionTracker = new PositionTracker(map.getMapWidth());
        Gdx.input.setInputProcessor(new KeyboardController());
    }
    public void initLvl(Map map){
        map.init(curLvl);//need to init map before getting map width
        positionTracker.init(map.getMapWidth());
        positionTracker.updateGoalTracker(map.getGoals());
    }
    public void setLvl(int lvl){
        curLvl = lvl;
    }

    @Override
    public void update(float delta){
        positionTracker.updateGlobalTracker(map.getEntities());
//        positionTracker.updateCharacterTracker();
//        positionTracker.updateCrateTracker();
//        positionTracker.updateWallTracker();
    }

    private class KeyboardController extends InputAdapter{
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


}
