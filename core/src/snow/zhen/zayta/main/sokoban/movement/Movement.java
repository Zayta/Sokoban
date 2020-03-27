package snow.zhen.zayta.main.sokoban.movement;

import com.badlogic.gdx.math.Vector2;

public interface Movement {
    void move(Direction direction);
    Direction getDirection();
    Vector2 getTargetPosition();

}
