package snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.particle;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.TextureComponent;

public class ParticleAnimationSystem extends IteratingSystem {

    private static Family FAMILY= Family.all(
            ParticleAnimationComponent.class,
            snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.TextureComponent.class
    ).get();

    public ParticleAnimationSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ParticleAnimationComponent animation = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.PARTICLE_ANIMATION.get(entity);
        animation.updateCurrentTime(deltaTime);
        TextureRegion frame = animation.getFrame();
        TextureComponent texture = Mappers.TEXTURE.get(entity);
        texture.setRegion(frame);

    }
}
