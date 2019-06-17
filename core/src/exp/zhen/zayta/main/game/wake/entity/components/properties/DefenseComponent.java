package exp.zhen.zayta.main.game.wake.entity.components.properties;

import com.badlogic.ashley.core.Component;

public class DefenseComponent implements Component {
    private float def,full_def;

    public void init(float def){
        this.def = def;

        this.full_def=def;
    }
}
