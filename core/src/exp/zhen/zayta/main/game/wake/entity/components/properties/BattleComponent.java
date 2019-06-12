package exp.zhen.zayta.main.game.wake.entity.components.properties;

import com.badlogic.ashley.core.Component;

public class BattleComponent implements Component {
    private int hp,atk,def;
    private int full_hp, full_atk, full_def;

    public void init(int hp, int atk, int def){
        this.hp=hp;
        this.atk = atk;
        this.def = def;

        this.full_hp=hp;
        this.full_atk = atk;
        this.full_def = def;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getFull_hp() {
        return full_hp;
    }

    public void setFull_hp(int full_hp) {
        this.full_hp = full_hp;
    }

    public int getFull_atk() {
        return full_atk;
    }

    public void setFull_atk(int full_atk) {
        this.full_atk = full_atk;
    }

    public int getFull_def() {
        return full_def;
    }

    public void setFull_def(int full_def) {
        this.full_def = full_def;
    }
}
