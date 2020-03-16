package exp.zhen.zayta.main.sokoban.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Hashtable;

public class Crate extends EntityBase {

    public enum State{
        NORMAL,IN_GOAL,IMMOVABLE,IN_HOLE,
    }
    private State state;
    private Hashtable<State,TextureRegion> textureRegions;
    public Crate(Hashtable<State,TextureRegion> textureRegions, float x, float y) {
        super(x, y);
        state = State.NORMAL;
        this.textureRegions = textureRegions;
    }
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



}
