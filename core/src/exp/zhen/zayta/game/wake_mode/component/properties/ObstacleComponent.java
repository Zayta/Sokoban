package exp.zhen.zayta.game.wake_mode.component.properties;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class ObstacleComponent implements Component,Pool.Poolable {

    private boolean hit;

    @Override
    public void reset() {
        hit = false;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

}
