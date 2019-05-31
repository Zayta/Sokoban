package exp.zhen.zayta.main.game.conquest.soldiers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.Align;

import exp.zhen.zayta.assets.AssetPaths;
import exp.zhen.zayta.main.game.config.SizeManager;

public class Stats extends Label {

//    public Stats (Skin skin,String name, int hp, int atk, int def){
//        super(skin);
//        add(name);
//        row();
//        add("HP: "+hp);
//        row();
//        add("ATK: "+atk);
//        row();
//        add("DEF: "+def);
//        pack();
//    }

    private static BitmapFont bitmapFont = new BitmapFont();

    public Stats(String stats) {
        super(stats, new LabelStyle(bitmapFont,Color.RED));

        bitmapFont.setUseIntegerPositions(false);
        
        setFontScaleX(SizeManager.CQ_WORLD_WIDTH/SizeManager.WIDTH);
        setFontScaleY(SizeManager.CQ_WORLD_HEIGHT/SizeManager.HEIGHT);
//        setPosition(0,0,Align.topLeft);
        setAlignment(Align.topLeft);
    }

    //    private String stats;
//
//    public Stats(String stats){
//        this.stats = stats;
//        bitmapFont.setColor(Color.RED);
//    }
//
//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        bitmapFont.draw(batch, "Score: 0" + stats, 0, 0);
//    }


//    public Stats (String name, int hp, int atk, int def){
//
////        super(skin);
//        Label.LabelStyle labelStyle = new Label.LabelStyle(bitmapFont,Color.CYAN);
////        bitmapFont.getData().setScale(0.02f);
//        add(new Label(name,labelStyle));
//        row();
//        add(new Label("HP: "+hp,labelStyle));
////        row();
////        add("ATK: "+atk);
////        row();
////        add("DEF: "+def);
//        pack();
//    }
//
//    public void update(int hp, int atk, int def){
//        clear();
//        add("HP: "+hp);
//        row();
//        add("ATK: "+atk);
//        row();
//        add("DEF: "+def);
//    }
}
