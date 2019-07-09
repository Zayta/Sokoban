package exp.zhen.zayta.main.game.wake.entity.components.properties.explosion;

import com.badlogic.ashley.core.Component;

public class ExplosiveComponent implements Component {

    private int power;

    public void setPower(int power) {
        this.power = power;
    }

    public int getPower() {
        return power;
    }
}
