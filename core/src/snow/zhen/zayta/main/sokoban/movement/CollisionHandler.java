package snow.zhen.zayta.main.sokoban.movement;

import snow.zhen.zayta.main.sokoban.entity.EntityBase;

public interface CollisionHandler {
    void handleCollision(EntityBase collidedEntity);
}
