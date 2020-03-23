package exp.zhen.zayta.main.sokoban.entity.units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Hashtable;

import exp.zhen.zayta.main.sokoban.entity.EntityBase;
import exp.zhen.zayta.main.sokoban.entity.EntityType;
import exp.zhen.zayta.main.sokoban.entity.MoveableEntity;

public class Crate extends MoveableEntity {

    public enum State{
        NORMAL,IN_GOAL,IMMOVABLE,IN_HOLE,
    }
    private State state;
    private Hashtable<State,TextureRegion> textureRegions;

    public Crate(Hashtable<State,TextureRegion> textureRegions, State state, float x, float y) {
        super(x, y);
        this.state = state;
        this.textureRegions = textureRegions;
    }

    @Override
    public TextureRegion getTextureRegion() {
        TextureRegion region = textureRegions.get(state);
        if(region==null)
            System.out.println("WARNING: CRATE REGION IS NULL");
        return region;
    }

    @Override
    public boolean is(EntityType entityType) {
        return entityType==EntityType.CRATE;
    }


}
