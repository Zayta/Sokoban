package exp.zhen.zayta.main.game.wake.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Fighter {
    private final String name;
    private final TextureRegion textureRegion;
    private final int hp, atk, def;

    public Fighter(String name, TextureRegion textureRegion, int hp, int atk, int def) {
        this.name = name;
        this.textureRegion = textureRegion;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
    }

    public String getName() {
        return name;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public int getHp() {
        return hp;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }
}
