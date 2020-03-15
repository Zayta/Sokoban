package exp.zhen.zayta.main.arcade_style_game.experiment.engine.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Stats {
    private final String name;
    private final TextureRegion [][] frames;
    private final TextureRegion textureRegion;
    private final int hp, atk, def;

    private TextureRegionDrawable avatar;

    public Stats(String name, TextureRegion textureRegion, int hp, int atk, int def) {
        this.name = name;
        this.textureRegion = textureRegion;
        frames = textureRegion.split(textureRegion.getRegionWidth()/4,textureRegion.getRegionHeight()/4);

        avatar = new TextureRegionDrawable(frames[0][0]);
        this.hp = hp;
        this.atk = atk;
        this.def = def;
    }

    public TextureRegionDrawable getAvatar(){
        return avatar;
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
