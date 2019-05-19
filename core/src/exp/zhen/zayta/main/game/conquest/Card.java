package exp.zhen.zayta.main.game.conquest;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import exp.zhen.zayta.assets.RegionNames;

public class Card extends Actor {

    TextureRegion textureRegion;
    public Card (TextureRegion textureRegion) {
        this.textureRegion = textureRegion;

    }

    public void setTexturetextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        Color color = getColor();
        System.out.println(textureRegion.getRegionWidth()+":"+textureRegion.getRegionHeight());
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(),
                textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
