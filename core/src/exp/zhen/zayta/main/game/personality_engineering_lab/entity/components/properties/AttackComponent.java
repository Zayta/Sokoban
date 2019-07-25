package exp.zhen.zayta.main.game.personality_engineering_lab.entity.components.properties;

import com.badlogic.ashley.core.Component;

public class AttackComponent implements Component {
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
}
