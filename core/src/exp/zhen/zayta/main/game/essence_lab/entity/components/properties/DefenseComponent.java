package exp.zhen.zayta.main.game.essence_lab.entity.components.properties;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class DefenseComponent implements Component, Pool.Poolable {
    private float def,full_def;

    public void init(float def){
        this.def = def;

        this.full_def=def;
    }

    @Override
    public void reset() {
        def = full_def;
    }
}
