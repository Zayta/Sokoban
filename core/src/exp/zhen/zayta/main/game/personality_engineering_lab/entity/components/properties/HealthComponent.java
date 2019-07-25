package exp.zhen.zayta.main.game.personality_engineering_lab.entity.components.properties;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component {
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
}
