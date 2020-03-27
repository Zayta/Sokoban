package snow.zhen.zayta.versions_unused.conquest.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class RangeComponent implements Component, Pool.Poolable {
    private int range = 0;

    public void setRange(int range) {
        this.range = range;
    }

    public int getRange() {
        return range;
    }

    @Override
    public void reset() {
        range=0;
    }
}
