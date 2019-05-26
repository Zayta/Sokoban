package exp.zhen.zayta.main.game.conquest.tiles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.conquest.soldiers.Soldier;

public abstract class Tile extends Actor {


    private static final Logger log = new Logger(Tile.class.getName(), Logger.DEBUG);

    private Soldier soldier;

    private TextureRegion textureRegion;
    public Tile(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;

    }


    public Soldier getSoldier() {
        return soldier;
    }

    public void setSoldier(Soldier soldier) {
        this.soldier = soldier;
    }

    public abstract void activate();





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
    }

}
