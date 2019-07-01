package exp.zhen.zayta.main.game.wake.game_mechanics.collision;

import com.badlogic.ashley.core.Entity;

public interface CollisionListener {
    void collideEvent(Entity entity1, Entity entity2);
}
