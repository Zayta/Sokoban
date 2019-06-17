package exp.zhen.zayta.main.game.wake.collision;

import com.badlogic.ashley.core.PooledEngine;

import exp.zhen.zayta.RPG;

@Deprecated
public class PositionTrackerCollisionSystem extends GameControllingSystem {
    public PositionTrackerCollisionSystem(RPG game, PooledEngine engine) {
        super(game, engine);
    }

    @Override
    public void reset() {

    }
}
