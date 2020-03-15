package exp.zhen.zayta.main.sokoban.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class GameObject
        implements Disposable {

    protected TextureRegion texture;
    protected GameObjects type = GameObjects.NULL;
    protected Vector2 position = new Vector2();

    public GameObject(TextureRegion texture, float x, float y) {
        this.texture = texture;
        this.position.set(x, y);
    }

    public boolean isType(GameObjects type) {
        return this.type == type;
    }

    public GameObjects getType() {
        return type;
    }

    public Vector2 getPosition() {
        return new Vector2(position);
    }

    public void update(float delta) {
    }

    public void render(SpriteBatch batch) {
        render(batch, 1.0f);
    }

    public void render(SpriteBatch batch, float scale) {
//        System.out.println("Texture is "+texture);
        if(texture!=null)
        batch.draw(texture, position.x * scale, position.y * scale,
                   texture.getRegionWidth() * scale, texture.getRegionHeight() * scale);
        else{
            System.out.println("Texture is null at "+this.toString());
        }
    }

    @Override
    public void dispose() {
        texture = null;
    }

}
