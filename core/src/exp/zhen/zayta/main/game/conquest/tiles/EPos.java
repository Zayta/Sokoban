package exp.zhen.zayta.main.game.conquest.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.conquest.soldiers.Soldier;

public class EPos extends Tile {
    private static final Logger log = new Logger(EPos.class.getName(), Logger.DEBUG);
    public EPos(TextureRegion textureRegion) {
        super(textureRegion);

    }
    public EPos(TextureRegion textureRegion, Soldier soldier) {
        super(textureRegion);
        this.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                log.debug("Clicked on npos ");
                activate();
            }
        });
        setSoldier(soldier);
    }

    @Override
    public void activate() {

    }
}
