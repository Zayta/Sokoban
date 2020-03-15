package exp.zhen.zayta.main.arcade_style_game.movable_items.locker;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class LockerComponent implements Component,Pool.Poolable {
    private int numRequiredKeys =1;

    public void decreaseNumRequiredKeys(int num){
        numRequiredKeys-=num;
    }
    public int getNumRequiredKeys() {
        return numRequiredKeys;
    }

    public void setNumRequiredKeys(int numRequiredKeys) {
        this.numRequiredKeys = numRequiredKeys;
    }

    @Override
    public void reset() {
        numRequiredKeys=1;
    }
}
