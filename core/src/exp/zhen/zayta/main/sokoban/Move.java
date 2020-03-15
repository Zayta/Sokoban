package exp.zhen.zayta.main.sokoban;

import com.badlogic.gdx.math.Vector2;

import exp.zhen.zayta.main.SizeManager;

public enum Move {

    NONE(0, 0),
    UP(0, SizeManager.TILE_SIZE),
    DOWN(0, -SizeManager.TILE_SIZE),
    LEFT(-SizeManager.TILE_SIZE, 0),
    RIGHT(SizeManager.TILE_SIZE, 0);

    private final Vector2 vector = new Vector2();

    Move(float x, float y) {
        vector.set(x, y);
    }

    public static Move invert(Move move) {
        switch (move) {
            case UP:
                return Move.DOWN;
            case DOWN:
                return Move.UP;
            case LEFT:
                return Move.RIGHT;
            case RIGHT:
                return Move.LEFT;
            default:
            case NONE:
                return Move.NONE;
        }
    }

    public Vector2 get() {
        return vector;
    }
}

