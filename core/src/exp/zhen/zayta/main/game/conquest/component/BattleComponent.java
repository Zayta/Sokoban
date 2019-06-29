package exp.zhen.zayta.main.game.conquest.component;

import com.badlogic.ashley.core.Component;

public class BattleComponent implements Component {
    private int HP,ATK,DEF;

    public void init(int HP, int ATK, int DEF){
        this.HP=HP;
        this.ATK = ATK;
        this.DEF = DEF;
    }

    public int getHP() {
        return HP;
    }

    public void decrementHP(int damage){
        HP-=damage;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getATK() {
        return ATK;
    }

    public void setATK(int ATK) {
        this.ATK = ATK;
    }

    public int getDEF() {
        return DEF;
    }

    public void setDEF(int DEF) {
        this.DEF = DEF;
    }
}
