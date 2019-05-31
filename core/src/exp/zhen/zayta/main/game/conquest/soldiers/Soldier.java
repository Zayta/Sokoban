package exp.zhen.zayta.main.game.conquest.soldiers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Logger;

import java.util.List;

import exp.zhen.zayta.main.game.conquest.Territory;

public abstract class Soldier {
    private final String name;
    private TextureRegion textureRegion;
    private int hp,atk,def;

    private Stats stats;
    private boolean triggeredAbility = false;

    private static final Logger log = new Logger(Soldier.class.getName(), Logger.DEBUG);

    public Soldier (String name, TextureRegion textureRegion, int hp, int atk, int def) {
        this.name = name;
        this.textureRegion = textureRegion;
        this.hp = hp; this.atk = atk; this.def=def;
        stats = new Stats(toString());

    }
    public Soldier (TextureRegion textureRegion, int hp, int atk, int def) {
        this.name = "";
        this.textureRegion = textureRegion;
        this.hp = hp; this.atk = atk; this.def=def;
        stats = new Stats(toString());
    }

    public Stats getStats() {
        return stats;
    }

    public void activateAbility(List<Soldier> targets){
        for(Soldier target: targets)
            activateAbility(target);
    }
    public abstract void activateAbility(Soldier target);
    public boolean hasTriggeredAbility() {
        return triggeredAbility;
    }

    public void setTriggeredAbility(boolean triggeredAbility) {
        this.triggeredAbility = triggeredAbility;
    }

    public void changeTriggeredAbility(){
        triggeredAbility = !triggeredAbility;
    }

    public void attack(Soldier target){
        target.decrementHp(this.atk);
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public void decrementHp(int damage){
        hp-=damage;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getName() {
        return name;
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

    @Override
    public String toString() {
        return name+"\nHP:"+hp+"\nATK:"+atk+"\nDEF:"+def;
    }
}

