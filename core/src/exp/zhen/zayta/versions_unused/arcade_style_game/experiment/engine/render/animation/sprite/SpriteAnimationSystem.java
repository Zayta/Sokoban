package exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.sprite;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.TextureComponent;

public class SpriteAnimationSystem extends IteratingSystem {

    private static Family FAMILY= Family.all(
            exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.sprite.SpriteAnimationComponent.class,
            exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.TextureComponent.class,
            VelocityComponent.class
    ).get();
    public SpriteAnimationSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        VelocityComponent movement = Mappers.MOVEMENT.get(entity);//todo figure out how to animate without polling for direction each time.
        Direction direction = movement.getDirection();
        SpriteAnimationComponent animation = Mappers.SPRITE_ANIMATION.get(entity);
        if(direction!=Direction.none) {
            animation.updateCurrentTime(deltaTime);
        }
        animation.setFrames(movement.getDirection());

        TextureRegion frame = animation.getFrame();
        TextureComponent texture = Mappers.TEXTURE.get(entity);
        texture.setRegion(frame);


    }
}
