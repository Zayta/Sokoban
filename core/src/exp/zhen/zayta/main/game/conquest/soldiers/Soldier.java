package exp.zhen.zayta.main.game.conquest.soldiers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;

import java.util.List;

import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.conquest.Territory;

public abstract class Soldier {
    private final String name;
    private TextureRegion textureRegion;
    private Image image;
    private int hp,atk,def;
    private final int FullHP, FullATK, FullDEF;

    private Label stats;
    private static BitmapFont statsFont = new BitmapFont();
    private boolean triggeredAbility = false;

    private static final Logger log = new Logger(Soldier.class.getName(), Logger.DEBUG);

    public Soldier (String name, TextureRegion textureRegion, int hp, int atk, int def) {
        this.name = name;
        this.textureRegion = textureRegion; this.image = new Image(textureRegion);
        this.hp = hp; this.atk = atk; this.def=def;
        this.FullHP = hp; this.FullATK = atk; this.FullDEF = def;
        initStatsLabel();
    }
    public Soldier (TextureRegion textureRegion, int hp, int atk, int def) {
        this.name = "M";
        this.textureRegion = textureRegion;this.image = new Image(textureRegion);
        this.hp = hp; this.atk = atk; this.def=def;
        this.FullHP = hp; this.FullATK = atk; this.FullDEF = def;
        initStatsLabel();
    }
    public void initStatsLabel() {
        stats = new Label(toString(), new Label.LabelStyle(statsFont,Color.RED));
        statsFont.setUseIntegerPositions(false);

        stats.setFontScaleX(SizeManager.CQ_WORLD_WIDTH/SizeManager.WIDTH);
        stats.setFontScaleY(SizeManager.CQ_WORLD_HEIGHT/SizeManager.HEIGHT);
    }

    public abstract void activateAbility(Soldier target);

    public void attack(Soldier target){
        target.decrementHp(this.atk);
    }
    public void decrementHp(int damage){
        hp-=damage;
        updateStats();
    }
    private void updateStats(){
        stats.setText(toString());
    }

    public boolean isKnockedOut(){
        return hp<=0;
    }

    /*Setters and getters*/
    public String getName() {
        return name;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
        updateStats();
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
        updateStats();
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
        updateStats();
    }

    public int getFullHP() {
        return FullHP;
    }

    public int getFullATK() {
        return FullATK;
    }

    public int getFullDEF() {
        return FullDEF;
    }

    public Label getStats() {
        return stats;
    }


    public BitmapFont getBitmapFont() {
        return statsFont;
    }

    public void setBitmapFont(BitmapFont statsFont) {
        this.statsFont = statsFont;
    }

    public boolean isTriggeredAbility() {
        return triggeredAbility;
    }

    public BitmapFont getStatsFont() {
        return statsFont;
    }

    public Image getImage() {
        return image;
    }

    @Override
    public String toString() {
//        return name+"\nHP:"+hp+"\nATK:"+atk+"\nDEF:"+def;
        return name+" HP:"+hp+" ATK:"+atk+" DEF:"+def;
    }

}

