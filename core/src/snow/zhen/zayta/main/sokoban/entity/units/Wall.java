package snow.zhen.zayta.main.sokoban.entity.units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import snow.zhen.zayta.main.sokoban.entity.EntityType;
import snow.zhen.zayta.main.sokoban.entity.templates.EntityTemplate;

public class Wall extends EntityTemplate {

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

    @Override
    public EntityType getEntityType() {
        return EntityType.WALL;
    }

    public void setTextureRegion(TextureRegion textureRegion){
        this.textureRegion = textureRegion;
    }

}
