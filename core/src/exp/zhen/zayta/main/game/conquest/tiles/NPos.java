package exp.zhen.zayta.main.game.conquest.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.conquest.soldiers.nur.Nighter;

public class NPos extends Tile {
    private static final Logger log = new Logger(NPos.class.getName(), Logger.DEBUG);

    public NPos(TextureRegion textureRegion, Nighter nighter) {
        super(textureRegion);
        setSoldier(nighter);
        this.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                log.debug("Clicked on npos "+this);
                activate();
            }
        });
        System.out.println("NPOS Solider: "+getSoldier());
    }

    @Override
    public void activate() {
        //other soldier.decrememtHP(getSoldier().getAtk());
        //
    }




}
