package exp.zhen.zayta.main.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;


import exp.zhen.zayta.main.sokoban.entity.units.Nighter;
import exp.zhen.zayta.main.sokoban.map.Map;
import exp.zhen.zayta.main.sokoban.map.PositionTracker;
import exp.zhen.zayta.main.sokoban.movement.Direction;

public class PlayController implements Updateable {

    //in-game
    private int curLvl=0;
    private Map map;
    private PositionTracker positionTracker;
    private KeyboardController keyboardController;


    public PlayController(Map map){
        this.map = map;
        this.keyboardController = new KeyboardController();
        positionTracker = new PositionTracker(map.getMapWidth());

    }
    public void initLvl(Map map){
        Gdx.input.setInputProcessor(keyboardController);
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
        for(Nighter n: map.getNighters())
            n.update(delta);
    }

    private class KeyboardController extends InputAdapter{

        @Override
        public boolean keyDown(int keycode) {
            System.out.println("Key is pressed");
            if(keycode== Input.Keys.LEFT){
                moveNighters(Direction.left);
            }
            if(keycode==Input.Keys.RIGHT){
                moveNighters(Direction.right);
            }
            if(keycode==Input.Keys.UP){
                moveNighters(Direction.up);
            }
            if(keycode==Input.Keys.DOWN){
                moveNighters(Direction.down);
            }
            return true;
        }

        @Override
        public boolean keyUp(int keycode) {
            //player stops moving
            return true;
        }
        private void moveNighters(Direction direction){
            for(Nighter nighter: map.getNighters()){
                System.out.println("Nighter px b4: "+nighter.getX()+", "+nighter.getY());
                nighter.move(direction);

                System.out.println("Nighter px aft: "+nighter.getX()+", "+nighter.getY());

            }
        }
    }



}
