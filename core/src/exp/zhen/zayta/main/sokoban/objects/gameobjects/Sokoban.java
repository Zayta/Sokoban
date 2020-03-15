package exp.zhen.zayta.main.sokoban.objects.gameobjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import exp.zhen.zayta.main.sokoban.objects.GameObject;
import exp.zhen.zayta.main.sokoban.objects.GameObjects;

public class Sokoban extends GameObject {
//TODO implement sokoban
    private enum State {
        MOVE_UP,
        MOVE_DOWN,
        MOVE_LEFT,
        MOVE_RIGHT,
        IDLE,
    }

    private Animation<TextureRegion> walkUpAnimation;
    private Animation<TextureRegion> walkDownAnimation;
    private Animation<TextureRegion> walkLeftAnimation;
    private Animation<TextureRegion> walkRightAnimation;
    private float animationStateTime = 0.f;

    private final Vector2 targetPosition = new Vector2();
    private State currentState = State.IDLE;

    public Sokoban(float x, float y) {
        super(null, x, y);
    }


}
