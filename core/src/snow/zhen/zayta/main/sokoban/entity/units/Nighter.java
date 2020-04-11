package snow.zhen.zayta.main.sokoban.entity.units;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;


import snow.zhen.zayta.main.GameConfig;
import snow.zhen.zayta.main.sokoban.entity.EntityType;
import snow.zhen.zayta.main.sokoban.entity.components.AnimationComponent;
import snow.zhen.zayta.main.sokoban.entity.templates.MoveableEntity;
import snow.zhen.zayta.main.sokoban.Updateable;

import static snow.zhen.zayta.main.sokoban.movement.Direction.none;

//the character that the player controls
public class Nighter extends MoveableEntity implements Updateable {

    private AnimationComponent animationComponent;
    public Nighter(Array<TextureAtlas.AtlasRegion> textureRegions) {
        animationComponent = new AnimationComponent(textureRegions);
        animationComponent.makeAnimation(textureRegions);
    }
    public void initPos(float x, float y){
        setPosition(x,y);
        setTargetPosition(x,y);
    }

    //do the animation in update
    @Override
    public void update(float delta) {
        animationComponent.update(delta,getDirection());
        move(delta);
    }
    private void move(float delta){
        if (getDirection() != none) {
            float progress = delta * GameConfig.MOVING_SPEED;
            position.lerp(getTargetPosition(), progress);

            //if character pos and target pos are almost the same
            if (position.dst(getTargetPosition()) < 0.001f) {
                move(none);
            }
        }
    }

    @Override
    public TextureRegion getTextureRegion() {
        return animationComponent.getTextureRegion();
    }

    @Override
    public boolean is(EntityType entityType) {
        return entityType==EntityType.CHARACTER;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.CHARACTER;
    }

}
