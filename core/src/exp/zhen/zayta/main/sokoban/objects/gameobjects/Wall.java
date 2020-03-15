package exp.zhen.zayta.main.sokoban.objects.gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import exp.zhen.zayta.main.sokoban.objects.GameObject;
import exp.zhen.zayta.main.sokoban.objects.GameObjects;

public class Wall extends GameObject {

    public Wall(TextureRegion texture, float x, float y) {
        super(texture, x, y);
        this.type = GameObjects.WALL;
    }
}
