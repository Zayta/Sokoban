package exp.zhen.zayta.main.sokoban.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import exp.zhen.zayta.main.sokoban.Updateable;

//the character that the player controls
public class Character extends EntityBase implements Updateable {

    private TextureRegion textureRegion;
    public Character(TextureRegion textureRegion,float x, float y) {
        super(x, y);
        this.textureRegion = textureRegion;
    }

    //do the animation in update
    @Override
    public void update(float delta) {

    }

    @Override
    public TextureRegion getTextureRegion() {
        return textureRegion;
    }
}
