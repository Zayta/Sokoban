package snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common;

import com.badlogic.ashley.core.ComponentMapper;

import snow.zhen.zayta.versions_unused.MissionComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.NameTag;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.labels.PlayerTag;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.AttackComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.ColorComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.HealthComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.explosion.ExplosiveComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.lanterns.LanternTag;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.CircularBoundsComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.DimensionComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.PositionTrackerComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.WorldWrapComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.TextureComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.particle.ParticleAnimationComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.sprite.SpriteAnimationComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.movable_items.components.MovableTag;
import snow.zhen.zayta.versions_unused.arcade_style_game.movable_items.components.PushComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.movable_items.locker.LockerComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent;

public class Mappers {
    public static final ComponentMapper<PlayerTag> PLAYER = ComponentMapper.getFor(PlayerTag.class);

    public static final ComponentMapper<CircularBoundsComponent> CIRCULAR_BOUNDS = ComponentMapper.getFor(CircularBoundsComponent.class);
    public static final ComponentMapper<RectangularBoundsComponent> RECTANGULAR_BOUNDS = ComponentMapper.getFor(RectangularBoundsComponent.class);

    public static final ComponentMapper<WorldWrapComponent> WORLD_WRAP = ComponentMapper.getFor(WorldWrapComponent.class);


    public static final ComponentMapper<VelocityComponent> MOVEMENT = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent> MOVEMENT_LIMITATION = ComponentMapper.getFor(MovementLimitationComponent.class);

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

    public static final ComponentMapper<LanternTag> LANTERN = ComponentMapper.getFor(LanternTag.class);

    private Mappers(){}
}
