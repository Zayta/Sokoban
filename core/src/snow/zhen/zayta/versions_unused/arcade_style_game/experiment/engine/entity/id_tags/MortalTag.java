package snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.id_tags;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class MortalTag implements Component,Pool.Poolable {

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
