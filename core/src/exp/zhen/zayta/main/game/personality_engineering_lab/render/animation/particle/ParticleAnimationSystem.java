package exp.zhen.zayta.main.game.personality_engineering_lab.render.animation.particle;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import exp.zhen.zayta.main.game.personality_engineering_lab.common.Mappers;
import exp.zhen.zayta.main.game.personality_engineering_lab.render.animation.TextureComponent;

public class ParticleAnimationSystem extends IteratingSystem {

    private static Family FAMILY= Family.all(
            ParticleAnimationComponent.class,
            TextureComponent.class
    ).get();

    public ParticleAnimationSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ParticleAnimationComponent animation = Mappers.PARTICLE_ANIMATION.get(entity);
        animation.updateCurrentTime(deltaTime);
        TextureRegion frame = animation.getFrame();
        TextureComponent texture = Mappers.TEXTURE.get(entity);
        texture.setRegion(frame);

    }
}
