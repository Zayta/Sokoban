package exp.zhen.zayta.mode.quest.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import exp.zhen.zayta.Direction;
import exp.zhen.zayta.common.Mappers;
import exp.zhen.zayta.mode.quest.component.properties.visual.AnimationComponent;
import exp.zhen.zayta.mode.quest.component.properties.movement.VelocityComponent;
import exp.zhen.zayta.mode.quest.component.properties.visual.TextureComponent;

public class AnimationSystem extends IteratingSystem {

    private static Family FAMILY= Family.all(
            AnimationComponent.class,
            TextureComponent.class,
            VelocityComponent.class
    ).get();
    public AnimationSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        VelocityComponent movement = Mappers.MOVEMENT.get(entity);//todo figure out how to animate without polling for direction each time.
        Direction direction = movement.getDirection();
        AnimationComponent animation = Mappers.ANIMATION.get(entity);
        if(direction!=Direction.none) {
            animation.updateCurrentTime(deltaTime);
        }
        animation.setFrames(movement.getDirection());

        TextureRegion frame = animation.getFrame();
        TextureComponent texture = Mappers.TEXTURE.get(entity);
        texture.setRegion(frame);


    }
}
