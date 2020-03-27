package snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class HealthComponent implements Component,Pool.Poolable {
    private float hp,full_hp;

    public void init(float hp){
        this.hp = hp;

        this.full_hp=hp;
    }


    public float getHp() {
        return hp;
    }

    public void decrement(float damage){
        hp-=damage;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public float getFull_hp() {
        return full_hp;
    }

    public void setFull_hp(float full_hp) {
        this.full_hp = full_hp;
    }

    @Override
    public void reset() {
        hp = full_hp;
    }
}
