package exp.zhen.zayta.main.game.conquest.tiles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Logger;

import java.awt.Font;

import exp.zhen.zayta.main.game.conquest.soldiers.Soldier;

public class Tile extends Actor {


    private static final Logger log = new Logger(Tile.class.getName(), Logger.DEBUG);

    private BitmapFont font;
    private Soldier soldier;

    private TextureRegion textureRegion;
    public Tile(TextureRegion textureRegion, BitmapFont font,Soldier soldier) {
        this.textureRegion = textureRegion;
        this.font = font;
        this.soldier = soldier;
    }

    public void removeSoldier(){
        soldier = null;
    }

    public Soldier getSoldier() {
        return soldier;
    }

    public void setSoldier(Soldier soldier) {
        this.soldier = soldier;
    }






    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(textureRegion == null) {
            log.error("Region not set on Actor " + getClass().getName());
            return;
        }

        batch.draw(textureRegion,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation()
        );

        if(soldier!=null) {
            batch.draw(soldier.getTextureRegion(), getX(), getY(),
                    getOriginX(), getOriginY(),
                    getWidth(), getHeight(),
                    getScaleX(), getScaleY(),
                    getRotation());

        }
        if(font!=null){
            font.draw(batch,soldier.toString(),getX(),getY());
        }
    }

}
