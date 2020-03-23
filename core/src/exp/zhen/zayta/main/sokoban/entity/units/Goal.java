package exp.zhen.zayta.main.sokoban.entity.units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import exp.zhen.zayta.main.sokoban.entity.EntityBase;
import exp.zhen.zayta.main.sokoban.entity.EntityType;

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
    public boolean is(EntityType entityType) {
        return entityType==EntityType.GOAL;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.GOAL;
    }
}
