package exp.zhen.zayta.main.game.conquest;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Territory extends Table {

    enum Terrain{
        LAB
    }
    private TextureAtlas conquestAtlas;
    private int numNighters;
    public Territory (TextureAtlas conquestAtlas){
        this.conquestAtlas = conquestAtlas;
        create();
    }

    private void create(){
        setTerrain(Terrain.LAB);
        selectNighters();

    }

    private void selectNighters(){

    }

    private void setTerrain(Terrain terrain){
        TextureRegion backgroundRegion;
        switch (terrain){
            case LAB:
                backgroundRegion = conquestAtlas.findRegion("backgrounds/fullscanner");
                break;
            default:
                backgroundRegion =conquestAtlas.findRegion("backgrounds/fullscanner");
                break;
        }
//        backgroundRegion.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        setBackground(new TextureRegionDrawable(backgroundRegion));
    }

    private Card makeCard(TextureRegion region, float left, float top, float width, float height){
        Card card = new Card(region);
        card.setBounds(left, top, left+width,top+height);

        card.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clicked");
            }
        });

        return card;
    }


}




