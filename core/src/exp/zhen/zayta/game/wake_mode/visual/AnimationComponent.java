package exp.zhen.zayta.game.wake_mode.visual;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import exp.zhen.zayta.game.wake_mode.movement.Direction;

public class AnimationComponent implements Component {
    // Constant rows and columns of the sprite sheet
    private static final int FRAME_COLS = 4, FRAME_ROWS = 4;
    private TextureRegion srcPng;
    private TextureRegion [] frames = new TextureRegion[FRAME_COLS*FRAME_ROWS];

    private Animation<TextureRegion> currentAnimation; private float currentTime=0;
    private Animation<TextureRegion> [] animations = new Animation[FRAME_ROWS];

    public void init(TextureRegion srcPng) {
        this.srcPng = srcPng;
        TextureRegion [][] tmp = srcPng.split(srcPng.getRegionWidth()/FRAME_COLS,srcPng.getRegionHeight()/FRAME_ROWS);

        for(int i = 0; i<animations.length; i++)
        {
            animations[i]= new Animation<TextureRegion>(0.15f,tmp[i]);
        }

//        int index = 0;
//        for (int i = 0; i < FRAME_ROWS; i++) {
//            for (int j = 0; j < FRAME_COLS; j++) {
//                frames[index++] = tmp[i][j];
//            }
//        }

//        currentAnimation = new Animation<TextureRegion>(0.025f,frames);
        currentAnimation = animations[0];
    }
    public void setFrames(Direction direction){
        //this is based on the spreadsheet I have
        switch (direction) {
            case up:
                currentAnimation = animations[3];
                break;
            case down:
                currentAnimation = animations[0];
                break;
            case left:
                currentAnimation = animations[1];
                break;
            case right:
                currentAnimation = animations[2];
                break;
        }
    }
    public void updateCurrentTime(float delta){
        currentTime+=delta;
    }
    public TextureRegion getFrame(){
        TextureRegion frame = currentAnimation.getKeyFrame(currentTime,true);
        return frame;
    }
    public Animation<TextureRegion> getCurrentAnimation() {
        return currentAnimation;
    }
}
