package exp.zhen.zayta.main.arcade_style_game.experiment.engine.render.animation.sprite;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import exp.zhen.zayta.main.arcade_style_game.experiment.engine.movement.Direction;

public class SpriteAnimationComponent implements Component {
    // Constant rows and columns of the sprite sheet
    private int FRAME_COLS = 4, FRAME_ROWS = 4;
    private Animation<TextureRegion> currentAnimation; private float currentTime=0;
    private Animation<TextureRegion> [] animations = new Animation[FRAME_ROWS];

    private Animation <TextureRegion> leftAnimation,rightAnimation,upAnimation,downAnimation;

    public void init(TextureRegion upFrames,TextureRegion downFrames,TextureRegion leftFrames,TextureRegion rightFrames, int colsPerVerticalFrame, int colsPerHorrizontalFrame){
        upAnimation = makeAnimation(upFrames,colsPerVerticalFrame,1);
        downAnimation = makeAnimation(downFrames,colsPerVerticalFrame,1);
        leftAnimation = makeAnimation(leftFrames,colsPerHorrizontalFrame,1);
        rightAnimation = makeAnimation(rightFrames,colsPerHorrizontalFrame,1);
    }
    private Animation<TextureRegion> makeAnimation(TextureRegion srcPng, int numCols, int numRows){

        TextureRegion [][] tmp = srcPng.split(srcPng.getRegionWidth()/numCols,srcPng.getRegionHeight()/numRows);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] frames = new TextureRegion[numRows*numCols];
        int index = 0;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        return new Animation<TextureRegion>(0.15f,frames);
    }

    public void init(TextureRegion srcPng) {
        TextureRegion [][] tmp = srcPng.split(srcPng.getRegionWidth()/FRAME_COLS,srcPng.getRegionHeight()/FRAME_ROWS);

        for(int i = 0; i<animations.length; i++)
        {
            animations[i]= new Animation<TextureRegion>(0.15f,tmp[i]);
        }
        currentAnimation = animations[0];
        //this is based on sithjester's spritesheet.
        upAnimation = animations[3];
        downAnimation = animations[0];
        leftAnimation = animations[1];
        rightAnimation = animations[2];
    }
    void setFrames(Direction direction){
        //this is based on the spreadsheet I have
        switch (direction) {
            case up:
                currentAnimation = upAnimation;
                break;
            case down:
                currentAnimation = downAnimation;
                break;
            case left:
                currentAnimation = leftAnimation;
                break;
            case right:
                currentAnimation = rightAnimation;
                break;
        }
    }
    void updateCurrentTime(float delta){
        currentTime+=delta;
    }
    TextureRegion getFrame(){
        return currentAnimation.getKeyFrame(currentTime,true);

    }
    public Animation<TextureRegion> getCurrentAnimation() {
        return currentAnimation;
    }
}
