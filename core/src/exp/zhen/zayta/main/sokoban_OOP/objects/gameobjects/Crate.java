package exp.zhen.zayta.main.sokoban_OOP.objects.gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import exp.zhen.zayta.main.GameConfig;
import exp.zhen.zayta.main.sokoban_OOP.Move;
import exp.zhen.zayta.main.sokoban_OOP.objects.GameObject;
import exp.zhen.zayta.main.sokoban_OOP.objects.GameObjects;

public class Crate extends GameObject {

    private final Array<TextureRegion> crateTexture;
    private final Vector2 targetPosition = new Vector2();

    private Place place;

    public Crate(Array<TextureRegion> textures, float x, float y) {
        super(textures.get(0), x, y);
        this.type = GameObjects.CRATE;

        crateTexture = textures;

        targetPosition.set(position);
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(targetPosition);
    }

    public void move(Move move) {
        targetPosition.add(move.get());
    }

    @Override
    public void update(float delta) {
        position.lerp(targetPosition, delta / GameConfig.MOVING_SPEED);
    }

    public void bindPlace(Place place) {
        unbindOldPlace();
        bindNewPlace(place);
        changeTexture();
    }

    private void unbindOldPlace() {
        if (place != null) {
            place.bindCrate(null); // unbind last place first
        }
    }

    private void bindNewPlace(Place place) {
        if (place != null) {
            place.bindCrate(this);
        }
        this.place = place;
    }

    private void changeTexture() {
        texture = (place == null) ? crateTexture.get(0) : crateTexture.get(1);
    }

}
