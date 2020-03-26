package exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.explosion;

import com.badlogic.ashley.core.Component;

public class ExplosiveComponent implements Component {

    private int power=10;

    public void setPower(int power) {
        this.power = power;
    }

    public int getPower() {
        return power;
    }
}
