package snow.zhen.zayta.main.sokoban.movement;

import snow.zhen.zayta.main.sokoban.entity.templates.EntityTemplate;

public interface CollisionHandler {
    void handleCollision(EntityTemplate collidedEntity);
}
