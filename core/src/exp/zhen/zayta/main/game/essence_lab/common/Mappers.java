package exp.zhen.zayta.main.game.essence_lab.common;

import com.badlogic.ashley.core.ComponentMapper;

import exp.zhen.zayta.main.game.essence_lab.entity.components.NameTag;
import exp.zhen.zayta.main.game.essence_lab.entity.components.labels.PlayerTag;
import exp.zhen.zayta.main.game.essence_lab.entity.components.properties.AttackComponent;
import exp.zhen.zayta.main.game.essence_lab.entity.components.properties.ColorComponent;
import exp.zhen.zayta.main.game.essence_lab.entity.components.properties.explosion.ExplosiveComponent;
import exp.zhen.zayta.main.game.essence_lab.entity.components.properties.HealthComponent;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.MissionComponent;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.movable_items.locker.LockerComponent;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.movable_items.components.MovableTag;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.movable_items.components.PushComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.Position;
import exp.zhen.zayta.main.game.essence_lab.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.WorldWrapComponent;
import exp.zhen.zayta.main.game.essence_lab.render.animation.particle.ParticleAnimationComponent;
import exp.zhen.zayta.main.game.essence_lab.render.animation.sprite.SpriteAnimationComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.CircularBoundsComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.VelocityComponent;
import exp.zhen.zayta.main.game.essence_lab.render.animation.TextureComponent;

public class Mappers {
    public static final ComponentMapper<PlayerTag> PLAYER = ComponentMapper.getFor(PlayerTag.class);

    public static final ComponentMapper<CircularBoundsComponent> CIRCULAR_BOUNDS = ComponentMapper.getFor(CircularBoundsComponent.class);
    public static final ComponentMapper<RectangularBoundsComponent> RECTANGULAR_BOUNDS = ComponentMapper.getFor(RectangularBoundsComponent.class);

    public static final ComponentMapper<WorldWrapComponent> WORLD_WRAP = ComponentMapper.getFor(WorldWrapComponent.class);


    public static final ComponentMapper<VelocityComponent> MOVEMENT = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<MovementLimitationComponent> MOVEMENT_LIMITATION = ComponentMapper.getFor(MovementLimitationComponent.class);

    public static final ComponentMapper<Position> POSITION = ComponentMapper.getFor(Position.class);


    public static final ComponentMapper <TextureComponent> TEXTURE = ComponentMapper.getFor(TextureComponent.class);
    public static final ComponentMapper <SpriteAnimationComponent> SPRITE_ANIMATION = ComponentMapper.getFor(SpriteAnimationComponent.class);
    public static final ComponentMapper <ParticleAnimationComponent> PARTICLE_ANIMATION = ComponentMapper.getFor(ParticleAnimationComponent.class);

    public static final ComponentMapper<DimensionComponent> DIMENSION = ComponentMapper.getFor(DimensionComponent.class);

    public static final ComponentMapper<PositionTrackerComponent> POSITION_TRACKER = ComponentMapper.getFor(PositionTrackerComponent.class);

    public static final ComponentMapper<HealthComponent> HEALTH = ComponentMapper.getFor(HealthComponent.class);
    public static final ComponentMapper<AttackComponent> ATK = ComponentMapper.getFor(AttackComponent.class);

    public static final ComponentMapper<NameTag> NAMETAG = ComponentMapper.getFor(NameTag.class);

    public static final ComponentMapper<MissionComponent> MISSION = ComponentMapper.getFor(MissionComponent.class);

    public static final ComponentMapper<ExplosiveComponent> EXPLOSIVE = ComponentMapper.getFor(ExplosiveComponent.class);

    public static final ComponentMapper<PushComponent> POCKET = ComponentMapper.getFor(PushComponent.class);
    public static final ComponentMapper<MovableTag> ITEM_SHOVE = ComponentMapper.getFor(MovableTag.class);
    public static final ComponentMapper<ColorComponent> COLOR = ComponentMapper.getFor(ColorComponent.class);
    public static final ComponentMapper<LockerComponent> LOCKER = ComponentMapper.getFor(LockerComponent.class);

    private Mappers(){}
}
