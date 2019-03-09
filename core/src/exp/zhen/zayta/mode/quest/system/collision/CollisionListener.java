package exp.zhen.zayta.mode.quest.system.collision;

import com.badlogic.ashley.core.Entity;

public interface CollisionListener {
    void collideEvent(Entity entity1, Entity entity2);
}
