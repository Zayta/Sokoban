package exp.zhen.zayta.main.sokoban.objects.gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import exp.zhen.zayta.main.sokoban.objects.GameObject;
import exp.zhen.zayta.main.sokoban.objects.GameObjects;

public class Place extends GameObject {

    private Crate crate;

    public Place(TextureRegion texture, float x, float y) {
        super(texture, x, y);
        this.type = GameObjects.PLACE;
    }

    @Override
    public void dispose() {

    }

    public void bindCrate(Crate crate) {
        this.crate = crate;
    }

    public boolean haveCrate() {
        return crate != null;
    }

    public Crate getCrate() {
        return crate;
    }

}
