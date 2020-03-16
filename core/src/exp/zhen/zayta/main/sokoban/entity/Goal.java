package exp.zhen.zayta.main.sokoban.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Goal extends EntityBase {
    private TextureRegion textureRegion;
    public Goal(TextureRegion textureRegion,float x, float y) {
        super(x, y);
        this.textureRegion = textureRegion;
    }

    @Override
    public TextureRegion getTextureRegion() {
        return textureRegion;
    }
}
