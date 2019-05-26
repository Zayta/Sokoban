package exp.zhen.zayta.main.game.conquest.soldiers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Soldier {
    private TextureRegion textureRegion;
    private int hp,atk,def;
    public Soldier (TextureRegion textureRegion, int hp, int atk, int def) {
        this.textureRegion = textureRegion;
    }
    
    public abstract void activateAbility();


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
}

