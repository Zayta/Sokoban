package exp.zhen.zayta.main.game.wake.common;

import com.badlogic.ashley.core.ComponentMapper;

import exp.zhen.zayta.main.game.wake.entity.components.NameTag;
import exp.zhen.zayta.main.game.wake.entity.components.labels.PlayerTag;
import exp.zhen.zayta.main.game.wake.entity.components.properties.explosion.ExplosiveComponent;
import exp.zhen.zayta.main.game.wake.entity.id_tags.MortalTag;
import exp.zhen.zayta.main.game.wake.entity.components.properties.HealthComponent;
import exp.zhen.zayta.main.game.wake.game_mechanics.mission.MissionComponent;
import exp.zhen.zayta.main.game.wake.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.main.game.wake.movement.component.Position;
import exp.zhen.zayta.main.game.wake.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.wake.game_mechanics.mission.stone_gathering.StoneTag;
import exp.zhen.zayta.main.game.wake.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.wake.render.animation.particle.ParticleAnimationComponent;
import exp.zhen.zayta.main.game.wake.render.animation.sprite.SpriteAnimationComponent;
import exp.zhen.zayta.main.game.wake.movement.component.CircularBoundsComponent;
import exp.zhen.zayta.main.game.wake.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.wake.movement.component.VelocityComponent;
import exp.zhen.zayta.main.game.wake.entity.components.properties.ObstacleComponent;
import exp.zhen.zayta.main.game.wake.render.animation.TextureComponent;

public class Mappers {
    public static final ComponentMapper<PlayerTag> PLAYER = ComponentMapper.getFor(PlayerTag.class);

    public static final ComponentMapper<CircularBoundsComponent> CIRCULAR_BOUNDS = ComponentMapper.getFor(CircularBoundsComponent.class);
    public static final ComponentMapper<RectangularBoundsComponent> RECTANGULAR_BOUNDS = ComponentMapper.getFor(RectangularBoundsComponent.class);

    public static final ComponentMapper<VelocityComponent> MOVEMENT = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<MovementLimitationComponent> MOVEMENT_LIMITATION = ComponentMapper.getFor(MovementLimitationComponent.class);

    public static final ComponentMapper<Position> POSITION = ComponentMapper.getFor(Position.class);
    public static final ComponentMapper<MortalTag> MORTAL =
            ComponentMapper.getFor(MortalTag.class);

    public static final ComponentMapper<StoneTag> STONE = ComponentMapper.getFor(StoneTag.class);

    public static final ComponentMapper<ObstacleComponent> OBSTACLE =
ComponentMapper.getFor(ObstacleComponent.class);

    public static final ComponentMapper <TextureComponent> TEXTURE = ComponentMapper.getFor(TextureComponent.class);
    public static final ComponentMapper <SpriteAnimationComponent> SPRITE_ANIMATION = ComponentMapper.getFor(SpriteAnimationComponent.class);
    public static final ComponentMapper <ParticleAnimationComponent> PARTICLE_ANIMATION = ComponentMapper.getFor(ParticleAnimationComponent.class);

    public static final ComponentMapper<DimensionComponent> DIMENSION = ComponentMapper.getFor(DimensionComponent.class);

    public static final ComponentMapper<PositionTrackerComponent> POSITION_TRACKER = ComponentMapper.getFor(PositionTrackerComponent.class);

    public static final ComponentMapper<HealthComponent> HEALTH = ComponentMapper.getFor(HealthComponent.class);

    public static final ComponentMapper<NameTag> NAMETAG = ComponentMapper.getFor(NameTag.class);

    public static final ComponentMapper<MissionComponent> MISSION = ComponentMapper.getFor(MissionComponent.class);

    public static final ComponentMapper<ExplosiveComponent> EXPLOSIVE = ComponentMapper.getFor(ExplosiveComponent.class);


    private Mappers(){}
}
