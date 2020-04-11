package snow.zhen.zayta.main.sokoban.entity.components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import snow.zhen.zayta.main.GameConfig;
import snow.zhen.zayta.main.sokoban.movement.Direction;

import static snow.zhen.zayta.main.sokoban.movement.Direction.none;

public class AnimationComponent {
    //animation
    private TextureRegion textureRegion;
    // Constant rows and columns of the sprite sheet
    private int FRAME_COLS = 3, FRAME_ROWS = 4;
    private float animationStateTime = 0;
    //    private Animation<TextureRegion> currentAnimation; private float currentTime=0;
    private Animation<TextureRegion>[] animations;
    private Animation <TextureRegion> leftAnimation,rightAnimation,upAnimation,downAnimation;

    public AnimationComponent(Array<TextureAtlas.AtlasRegion> textureRegions){
        makeAnimation(textureRegions);
    }
    public void update(float delta, Direction direction) {
        animationStateTime+=delta;
        textureRegion = getFrame(animationStateTime,direction);
    }


    public void makeAnimation(Array<TextureAtlas.AtlasRegion> textureRegions){
        animations = new Animation[FRAME_ROWS];
        for(int i = 0; i<animations.length; i++)
        {
            int index = i*FRAME_COLS;
            Array<TextureRegion> frames = new Array<TextureRegion>(new TextureRegion[]{
                    textureRegions.get(index),textureRegions.get(index+1),textureRegions.get(index+2)});
            animations[i]= new Animation<TextureRegion>(0.8f,frames, Animation.PlayMode.LOOP);
        }
        //this is based on sithjester's spritesheet.
        upAnimation = animations[3];
        downAnimation = animations[0];
        leftAnimation = animations[1];
        rightAnimation = animations[2];
        textureRegion=animations[0].getKeyFrame(0);
    }

    private TextureRegion getFrame(float time, Direction direction){
        switch (direction) {
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

    public TextureRegion getTextureRegion(){
        return textureRegion;
    }

}
