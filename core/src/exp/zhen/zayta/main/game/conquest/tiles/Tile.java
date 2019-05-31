package exp.zhen.zayta.main.game.conquest.tiles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Scaling;

import java.awt.Font;

import exp.zhen.zayta.main.game.conquest.soldiers.Soldier;

public class Tile extends Table {


    private static final Logger log = new Logger(Tile.class.getName(), Logger.DEBUG);

    private Soldier soldier;

    private TextureRegion textureRegion;
    public Tile(TextureRegion textureRegion, Soldier soldier) {
        this.textureRegion = textureRegion;
        this.soldier = soldier;
        setTouchable(Touchable.enabled);
//        soldier.setBounds(0,0,getWidth(),getHeight());
//        soldier.setOrigin(Align.center);
        init();
    }
    private void init(){
        setBackground(new TextureRegionDrawable(textureRegion));
        Image image = new Image (soldier.getTextureRegion());
        image.setScaling(Scaling.fit);
        add(image).expand();
        row();
        add(soldier.getStats());
        center();
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



//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        if(textureRegion == null) {
//            log.error("Region not set on Actor " + getClass().getName());
//            return;
//        }
//
//        batch.draw(textureRegion,
//                getX(), getY(),
//                getOriginX(), getOriginY(),
//                getWidth(), getHeight(),
//                getScaleX(), getScaleY(),
//                getRotation()
//        );
//
//        if(soldier!=null) {
//            batch.draw(soldier.getTextureRegion(), getX(), getY(),
//                    getOriginX(), getOriginY(),
//                    getWidth(), getHeight(),
//                    getScaleX(), getScaleY(),
//                    getRotation());
//
//        }
//        super.draw(batch,parentAlpha);
////        if(font!=null){
////            font.draw(batch,soldier.toString(),getX(),getY());
////        }
//    }

}
