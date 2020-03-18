package exp.zhen.zayta.main.sokoban.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Wall extends EntityBase {

    private TextureRegion textureRegion;
    public Wall(TextureRegion textureRegion,float x, float y) {
        super(x, y);
        this.textureRegion = textureRegion;
    }

    @Override
    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    @Override
    public boolean is(EntityType entityType) {
        return entityType==EntityType.WALL;
    }

    public void setTextureRegion(TextureRegion textureRegion){
        this.textureRegion = textureRegion;
    }

}
