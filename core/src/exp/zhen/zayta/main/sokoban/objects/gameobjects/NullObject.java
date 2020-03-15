package exp.zhen.zayta.main.sokoban.objects.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import exp.zhen.zayta.main.sokoban.objects.GameObject;
import exp.zhen.zayta.main.sokoban.objects.GameObjects;

public class NullObject extends GameObject {

    public static final NullObject instance = new NullObject(null, 0, 0);

    public NullObject(TextureRegion texture, float x, float y) {
        super(texture, x, y);
        this.type = GameObjects.NULL;
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(SpriteBatch batch) {
    }

    @Override
    public void dispose() {
    }

}
