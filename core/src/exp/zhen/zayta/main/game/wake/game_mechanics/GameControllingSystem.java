package exp.zhen.zayta.main.game.wake.game_mechanics;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;

import exp.zhen.zayta.RPG;
import exp.zhen.zayta.main.game.wake.WakeMode;
import exp.zhen.zayta.main.menu.MenuScreen;
import exp.zhen.zayta.main.game.wake.movement.PositionTracker;

public abstract class GameControllingSystem extends EntitySystem implements Pool.Poolable {
    private RPG game; private PooledEngine engine;
    private static int numMissions=0;

    private static final Logger log = new Logger(GameControllingSystem.class.getName(),Logger.DEBUG);
    public GameControllingSystem(RPG game, PooledEngine engine) {
        this.game = game;
        this.engine = engine;
//        log.debug("numMissionsLeft:"+numMissions);
    }

    @Override
    public PooledEngine getEngine() {
        return engine;
    }

    public void addMission(){
        numMissions++;
    }

//    public int getNumMissions() {
//        return numMissions;
//    }

//    public void setNumMissions(int numMissions) {
//        this.numMissions = numMissions;
//    }
    public void completeMission(){
        numMissions--;
        if(numMissions<=0){
            setNextLevel();
        }
    };

    private void setNextLevel(){
        RPG.userData.unlockScene();
        goToMenu();
    }

    public void setGameOver(){
        goToMenu();
    }
    private void goToMenu(){
        PositionTracker.reset();
        getEngine().removeAllEntities();
        game.setScreen(new MenuScreen(game));
    }
}
