package exp.zhen.zayta.game.quest.collision;

import com.badlogic.ashley.core.Entity;

public interface CollisionListener {
    void collideEvent(Entity entity1, Entity entity2);
}
