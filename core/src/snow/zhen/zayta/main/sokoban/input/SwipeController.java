package snow.zhen.zayta.main.sokoban.input;

import com.badlogic.gdx.input.GestureDetector;

import snow.zhen.zayta.main.sokoban.PlayController;
import snow.zhen.zayta.main.sokoban.movement.Direction;

public class SwipeController extends GestureDetector.GestureAdapter {
    private PlayController playController;
    public SwipeController(PlayController playController){
        this.playController = playController;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        //velX and velY are in pixels
        ////log.debug("fling occurred gestureinputhandler");
        if(Math.abs(velocityX)>Math.abs(velocityY)){
            if(velocityX>0){
                playController.moveNighters(Direction.right);
            }else {
                playController.moveNighters(Direction.left);
            }
        }else{
            if(velocityY>0) {
                playController.moveNighters(Direction.down);
            }
            else {
                playController.moveNighters(Direction.up);
            }
        }
        return false;//key! return false, else stage wont work.
    }

}
