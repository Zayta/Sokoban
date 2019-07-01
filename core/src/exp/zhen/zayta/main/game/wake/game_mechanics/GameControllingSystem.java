package exp.zhen.zayta.main.game.wake.game_mechanics;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool;

import exp.zhen.zayta.RPG;
import exp.zhen.zayta.main.menu.MenuScreen;
import exp.zhen.zayta.main.game.wake.movement.PositionTracker;

public abstract class GameControllingSystem extends EntitySystem implements Pool.Poolable {
    private RPG game; private PooledEngine engine;

    public GameControllingSystem(RPG game, PooledEngine engine) {
        this.game = game;
        this.engine = engine;
    }

    @Override
    public PooledEngine getEngine() {
        return engine;
    }

    public void setNextLevel(){
//        RPG.userData.updateLevel();
//        getEngine().removeAllEntities();
//        game.setScreen(new MenuScreen(game));
        goToMenu();
    }

    public void setGameOver(){
//        RPG.userData.reset();
//        getEngine().removeAllEntities();
//        game.setScreen(new MenuScreen(game));
        goToMenu();
    }
    private void goToMenu(){
        PositionTracker.reset();
        getEngine().removeAllEntities();
        game.setScreen(new MenuScreen(game));
    }
}
