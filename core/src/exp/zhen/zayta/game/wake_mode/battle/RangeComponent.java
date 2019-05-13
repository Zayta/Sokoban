package exp.zhen.zayta.game.wake_mode.battle;

import com.badlogic.ashley.core.Component;

public class RangeComponent implements Component {
    private int range = 0;

    public void setRange(int range) {
        this.range = range;
    }

    public int getRange() {
        return range;
    }
}
