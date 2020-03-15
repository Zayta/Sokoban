package exp.zhen.zayta.main.arcade_style_game.experiment.engine.render.animation.particle;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ParticleAnimationComponent implements Component {
    // Constant rows and columns of the sprite sheet
    private int FRAME_COLS = 5, FRAME_ROWS = 1;
    private Animation<TextureRegion> currentAnimation; private float currentTime=0;
    private Animation<TextureRegion> [] animations = new Animation[FRAME_ROWS];

    public void init(TextureRegion srcPng, int FRAME_COLS, int FRAME_ROWS) {
        this.FRAME_COLS = FRAME_COLS; this.FRAME_ROWS = FRAME_ROWS;
        TextureRegion [][] tmp = srcPng.split(srcPng.getRegionWidth()/FRAME_COLS,srcPng.getRegionHeight()/FRAME_ROWS);
        for(int i = 0; i<animations.length; i++)
        {
            animations[i]= new Animation<TextureRegion>(0.15f,tmp[i]);
        }
        currentAnimation = animations[0];
    }

    public void setFrame(int row){
        int index = row % FRAME_ROWS;
        currentAnimation = animations[index];
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
