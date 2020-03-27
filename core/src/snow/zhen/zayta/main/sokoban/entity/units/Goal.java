package snow.zhen.zayta.main.sokoban.entity.units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import snow.zhen.zayta.main.sokoban.entity.EntityBase;
import snow.zhen.zayta.main.sokoban.entity.EntityType;

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

    @Override
    public boolean is(snow.zhen.zayta.main.sokoban.entity.EntityType entityType) {
        return entityType== snow.zhen.zayta.main.sokoban.entity.EntityType.GOAL;
    }

    @Override
    public snow.zhen.zayta.main.sokoban.entity.EntityType getEntityType() {
        return EntityType.GOAL;
    }
}
