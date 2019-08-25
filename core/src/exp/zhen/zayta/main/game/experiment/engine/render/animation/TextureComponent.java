package exp.zhen.zayta.main.game.experiment.engine.render.animation;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureComponent implements Component{

    private TextureRegion region;
    public TextureRegion getRegion() { return region; }
    public void setRegion(TextureRegion region) { this.region = region; }

}
