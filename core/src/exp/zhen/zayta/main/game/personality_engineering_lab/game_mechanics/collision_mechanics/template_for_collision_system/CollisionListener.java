package exp.zhen.zayta.main.game.personality_engineering_lab.game_mechanics.collision_mechanics.template_for_collision_system;

import com.badlogic.ashley.core.Entity;

public interface CollisionListener {
    void collideEvent(Entity entity1, Entity entity2);
}
