package exp.zhen.zayta.main.sokoban.movement;

import exp.zhen.zayta.main.sokoban.entity.EntityBase;

public interface CollisionHandler {
    void handleCollision(EntityBase collidedEntity);
}
