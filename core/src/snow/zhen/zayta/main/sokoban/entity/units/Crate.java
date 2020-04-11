package snow.zhen.zayta.main.sokoban.entity.units;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.Hashtable;

import snow.zhen.zayta.main.GameConfig;
import snow.zhen.zayta.main.sokoban.Updateable;
import snow.zhen.zayta.main.sokoban.entity.EntityType;
import snow.zhen.zayta.main.sokoban.entity.templates.MoveableEntity;

public class Crate extends MoveableEntity implements Updateable {

    public enum State{
        NORMAL,IN_GOAL,IMMOVABLE,IN_HOLE,
    }
    private State state;
    private Hashtable<State,TextureRegion> textureRegions;

    public Crate(Array<TextureAtlas.AtlasRegion> crateRegions, State state, float x, float y) {
        super(x, y);
        if(crateRegions.size==0){
            System.out.println("Error: Crate regions is empty");
        }
        else {
            this.state = state;
            //==IMPORTANT: must be indexed in the order of normal, in-goal, immovable, and inhole==//
            this.textureRegions = new Hashtable<Crate.State, TextureRegion>();

            insertTextureRegion(crateRegions,State.NORMAL,0);
            insertTextureRegion(crateRegions,State.IN_GOAL,1);
            insertTextureRegion(crateRegions,State.IMMOVABLE,2);
            insertTextureRegion(crateRegions,State.IN_HOLE,3);
        }

    }
    private void insertTextureRegion(Array<TextureAtlas.AtlasRegion> crateRegions,State state, int region){
        if (crateRegions.size<=region||crateRegions.get(region)==null) {//if region doesnt exist
            textureRegions.put(state, crateRegions.get(0));
        } else {
            textureRegions.put(state, crateRegions.get(region));
        }

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
