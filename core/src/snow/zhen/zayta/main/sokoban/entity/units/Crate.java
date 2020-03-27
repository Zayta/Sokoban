package snow.zhen.zayta.main.sokoban.entity.units;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Hashtable;

import snow.zhen.zayta.main.GameConfig;
import snow.zhen.zayta.main.sokoban.Updateable;
import snow.zhen.zayta.main.sokoban.entity.EntityType;
import snow.zhen.zayta.main.sokoban.entity.MoveableEntity;

public class Crate extends MoveableEntity implements Updateable {

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

    public void setState(State state){
        this.state = state;
    }

    public State getState() {
        return state;
    }

    @Override
    public void update(float delta) {
        position.lerp(getTargetPosition(), delta * GameConfig.MOVING_SPEED);//alpha must be the same or greater than nighter speed...
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

    @Override
    public EntityType getEntityType() {
        return EntityType.CRATE;
    }


}
