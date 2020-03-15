package exp.zhen.zayta.main.sokoban.objects.gameobjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import exp.zhen.zayta.main.SizeManager;
import exp.zhen.zayta.main.sokoban.Move;
import exp.zhen.zayta.main.sokoban.objects.GameObject;
import exp.zhen.zayta.main.sokoban.objects.GameObjects;

public class Sokoban extends GameObject {
    //animation class
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

    // Constant rows and columns of the sprite sheet
    private int FRAME_COLS = 4, FRAME_ROWS = 4;
    private Animation<TextureRegion> [] animations = new Animation[FRAME_ROWS];

    public Sokoban(TextureRegion textureRegion,float x, float y) {
        super(textureRegion, x, y);
        this.type = GameObjects.SOKOBAN;

        loadAnimations(textureRegion);

        targetPosition.set(x, y);
    }

    private void loadAnimations(TextureRegion srcPng) {
        TextureRegion [][] tmp = srcPng.split(srcPng.getRegionWidth()/FRAME_COLS,srcPng.getRegionHeight()/FRAME_ROWS);

        for(int i = 0; i<animations.length; i++)
        {
            animations[i]= new Animation<TextureRegion>(0.15f,tmp[i]);
        }

        walkUpAnimation = animations[3];
        walkDownAnimation = animations[0];
        walkLeftAnimation = animations[1];
        walkRightAnimation = animations[2];
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(targetPosition);
    }

    public void move(Move move) {
        switch (move) {
            case UP:
                currentState = State.MOVE_UP;
                break;
            case DOWN:
                currentState = State.MOVE_DOWN;
                break;
            case LEFT:
                currentState = State.MOVE_LEFT;
                break;
            case RIGHT:
                currentState = State.MOVE_RIGHT;
                break;
        }
        targetPosition.add(move.get());
    }

    @Override
    public void update(float delta) {
        animationStateTime += delta;
        texture = getNextFrame(animationStateTime);
        updateMovement(delta);
    }

    private void updateMovement(float delta) {
        if (currentState != State.IDLE) {
            float progress = delta / SizeManager.MOVING_SPEED;
            position.lerp(targetPosition, progress);

            if (position.dst(targetPosition) < 1.0f) {
                currentState = State.IDLE;
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        walkDownAnimation = null;
        walkLeftAnimation = null;
        walkRightAnimation = null;
        walkUpAnimation = null;
    }

    private TextureRegion getNextFrame(float time) {
        switch (currentState) {
            case MOVE_UP:
                return walkUpAnimation.getKeyFrame(time);

            case MOVE_DOWN:
                return walkDownAnimation.getKeyFrame(time);

            case MOVE_LEFT:
                return walkLeftAnimation.getKeyFrame(time);

            case MOVE_RIGHT:
                return walkRightAnimation.getKeyFrame(time);

            default:
            case IDLE:
                return walkDownAnimation.getKeyFrame(0);
        }
    }
}
