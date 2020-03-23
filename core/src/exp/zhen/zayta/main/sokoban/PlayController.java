package exp.zhen.zayta.main.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;


import exp.zhen.zayta.main.sokoban.entity.EntityBase;
import exp.zhen.zayta.main.sokoban.entity.EntityType;
import exp.zhen.zayta.main.sokoban.entity.units.Crate;
import exp.zhen.zayta.main.sokoban.entity.units.Nighter;
import exp.zhen.zayta.main.sokoban.map.Map;
import exp.zhen.zayta.main.sokoban.map.PositionTracker;
import exp.zhen.zayta.main.sokoban.movement.Direction;

public class PlayController implements Updateable {

    //in-game
    private Map map;
    private PositionTracker positionTracker;
    private KeyboardController keyboardController;

    //for current lvl
    private int curLvl=0;
    private int placedCrates=0;
//    private boolean isComplete = false;


    public PlayController(Map map){
        this.map = map;
        this.keyboardController = new KeyboardController();
        positionTracker = new PositionTracker(map.getMapWidth());

    }
    public void initLvl(Map map){
        Gdx.input.setInputProcessor(keyboardController);
        map.init(curLvl);//need to init map before getting map width
        positionTracker.init(map.getMapWidth());
        placedCrates=0;
//        isComplete = false;
//        positionTracker.updateGoalTracker(map.getGoals());
//        positionTracker.updateWallTracker();
    }
    public void setLvl(int lvl){
        curLvl = lvl;
    }

    @Override
    public void update(float delta){
        positionTracker.updateGlobalTracker(map.getEntities());
//        positionTracker.updateCharacterTracker();
//        positionTracker.updateCrateTracker();
        for(Nighter n: map.getNighters())
            n.update(delta);
        for(Crate c: map.getCrates())
            c.update(delta);


    }

    private class KeyboardController extends InputAdapter{

        @Override
        public boolean keyDown(int keycode) {
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

    }
    private void moveNighters(Direction direction){
        for(Nighter nighter: map.getNighters()){

            //check for collision
            EntityBase entityToBeCollidedWith = collision(nighter, direction);
            if(entityToBeCollidedWith==null){//if there's no collision, move Nighters
                nighter.move(direction);
                return;
            }

            switch (entityToBeCollidedWith.getEntityType()){
                case WALL:
                    break;
                case GOAL:
                    nighter.move(direction);
                    break;
                case CRATE:
                    handleCrateCollision((Crate)entityToBeCollidedWith, nighter, direction);

                    break;
                case CHARACTER:
                    break;
            }
        }
    }

    //returns the Entity that the moveableEntity will collide with, if it moves in the specified direction
    private EntityBase collision(EntityBase moveableEntity, Direction direction){
        float x = moveableEntity.getX()+direction.directionX,
                y = moveableEntity.getY()+direction.directionY;
        EntityBase entityBase = positionTracker.getEntityAtPos(x,y);
        return entityBase;
    }

    private void handleCrateCollision(Crate crate, Nighter nighter, Direction direction){

        EntityBase crateCollider = collision(crate,direction);
        if(crateCollider==null) //if crate no collide, move crate, then move nighter
        {
            moveCrate(crate, direction, Crate.State.NORMAL);
            nighter.move(direction);
        }
        else if(crateCollider.is(EntityType.GOAL)){
            moveCrate(crate,direction, Crate.State.IN_GOAL);
            nighter.move(direction);
            placedCrates++;
//            if(placedCrates==map.getNumGoals()){
//                //finish
//                isComplete = true;
//            }
        }
    }
    private void moveCrate(Crate crate, Direction direction, Crate.State newState){
        //if crate was in goal but is now moved out of goal
        if(crate.getState()== Crate.State.IN_GOAL){//must be before crate setting newstate
            placedCrates--;
        }
        crate.move(direction);
        crate.setState(newState);
    }

    public boolean isComplete(){
        System.out.println("Placed crates: "+placedCrates+", numGoals: "+map.getNumGoals());
        return placedCrates>=map.getNumGoals();
    }



}
