package snow.zhen.zayta.main.sokoban.entity.units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import snow.zhen.zayta.main.GameConfig;
import snow.zhen.zayta.main.sokoban.entity.EntityType;
import snow.zhen.zayta.main.sokoban.entity.MoveableEntity;
import snow.zhen.zayta.main.sokoban.Updateable;
import snow.zhen.zayta.main.sokoban.movement.Direction;

import static snow.zhen.zayta.main.sokoban.movement.Direction.none;

//the character that the player controls
public class Nighter extends MoveableEntity implements Updateable {

    //animation
    private TextureRegion textureRegion;
    // Constant rows and columns of the sprite sheet
    private int FRAME_COLS = 4, FRAME_ROWS = 4; private float animationStateTime = 0;
//    private Animation<TextureRegion> currentAnimation; private float currentTime=0;
    private Animation<TextureRegion> [] animations = new Animation[FRAME_ROWS];
    private Animation <TextureRegion> leftAnimation,rightAnimation,upAnimation,downAnimation;

    public Nighter(TextureRegion textureRegion) {
        makeAnimation(textureRegion);
    }
    public void initPos(float x, float y){
        setPosition(x,y);
        setTargetPosition(x,y);
    }

    //do the animation in update
    @Override
    public void update(float delta) {
        animationStateTime+=delta;
        textureRegion = getFrame(animationStateTime);
        animateMovement(delta);
    }
    private void animateMovement(float delta) {

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
        return textureRegion;
    }

    @Override
    public boolean is(EntityType entityType) {
        return entityType==EntityType.CHARACTER;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.CHARACTER;
    }

    private void makeAnimation(TextureRegion srcPng){
        TextureRegion [][] tmp = srcPng.split(srcPng.getRegionWidth()/FRAME_COLS,srcPng.getRegionHeight()/FRAME_ROWS);

        for(int i = 0; i<animations.length; i++)
        {
            animations[i]= new Animation<TextureRegion>(0.1f,tmp[i]);
        }
        //this is based on sithjester's spritesheet.
        upAnimation = animations[3];
        downAnimation = animations[0];
        leftAnimation = animations[1];
        rightAnimation = animations[2];
        textureRegion=animations[0].getKeyFrame(0);
    }
    private TextureRegion getFrame(float time){
        switch (getDirection()) {
            case up:
                return upAnimation.getKeyFrame(time);

            case down:
                return downAnimation.getKeyFrame(time);

            case left:
                return leftAnimation.getKeyFrame(time);

            case right:
                return rightAnimation.getKeyFrame(time);

            case none:
            default:
                return downAnimation.getKeyFrame(0);
        }
    }

}
