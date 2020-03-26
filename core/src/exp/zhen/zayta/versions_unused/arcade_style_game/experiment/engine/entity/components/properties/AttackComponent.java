package exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class AttackComponent implements Component, Pool.Poolable {
    private float atk,full_atk;

    public void init(float atk){
        this.atk = atk;

        this.full_atk=atk;
    }

    public float getAtk() {
        return atk;
    }

    public void setAtk(float atk) {
        this.atk = atk;
    }

    public float getFull_atk() {
        return full_atk;
    }

    public void setFull_atk(float full_atk) {
        this.full_atk = full_atk;
    }

    @Override
    public void reset() {
        atk = full_atk;
    }
}
