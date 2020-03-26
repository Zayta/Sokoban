package exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common;

import com.badlogic.ashley.core.ComponentMapper;

import exp.zhen.zayta.versions_unused.MissionComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.NameTag;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.labels.PlayerTag;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.AttackComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.ColorComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.HealthComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.explosion.ExplosiveComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.lanterns.LanternTag;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.CircularBoundsComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.DimensionComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.WorldWrapComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.TextureComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.particle.ParticleAnimationComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.sprite.SpriteAnimationComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.movable_items.components.MovableTag;
import exp.zhen.zayta.versions_unused.arcade_style_game.movable_items.components.PushComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.movable_items.locker.LockerComponent;

public class Mappers {
    public static final ComponentMapper<exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.labels.PlayerTag> PLAYER = ComponentMapper.getFor(PlayerTag.class);

    public static final ComponentMapper<exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.CircularBoundsComponent> CIRCULAR_BOUNDS = ComponentMapper.getFor(CircularBoundsComponent.class);
    public static final ComponentMapper<exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent> RECTANGULAR_BOUNDS = ComponentMapper.getFor(RectangularBoundsComponent.class);

    public static final ComponentMapper<exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.WorldWrapComponent> WORLD_WRAP = ComponentMapper.getFor(WorldWrapComponent.class);


    public static final ComponentMapper<exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent> MOVEMENT = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent> MOVEMENT_LIMITATION = ComponentMapper.getFor(MovementLimitationComponent.class);

    public static final ComponentMapper<exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position> POSITION = ComponentMapper.getFor(Position.class);


    public static final ComponentMapper <exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.TextureComponent> TEXTURE = ComponentMapper.getFor(TextureComponent.class);
    public static final ComponentMapper <exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.sprite.SpriteAnimationComponent> SPRITE_ANIMATION = ComponentMapper.getFor(SpriteAnimationComponent.class);
    public static final ComponentMapper <exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.particle.ParticleAnimationComponent> PARTICLE_ANIMATION = ComponentMapper.getFor(ParticleAnimationComponent.class);

    public static final ComponentMapper<exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.DimensionComponent> DIMENSION = ComponentMapper.getFor(DimensionComponent.class);

    public static final ComponentMapper<exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.PositionTrackerComponent> POSITION_TRACKER = ComponentMapper.getFor(PositionTrackerComponent.class);

    public static final ComponentMapper<exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.HealthComponent> HEALTH = ComponentMapper.getFor(HealthComponent.class);
    public static final ComponentMapper<exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.AttackComponent> ATK = ComponentMapper.getFor(AttackComponent.class);

    public static final ComponentMapper<exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.NameTag> NAMETAG = ComponentMapper.getFor(NameTag.class);

    public static final ComponentMapper<MissionComponent> MISSION = ComponentMapper.getFor(MissionComponent.class);

    public static final ComponentMapper<exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.explosion.ExplosiveComponent> EXPLOSIVE = ComponentMapper.getFor(ExplosiveComponent.class);

    public static final ComponentMapper<exp.zhen.zayta.versions_unused.arcade_style_game.movable_items.components.PushComponent> POCKET = ComponentMapper.getFor(PushComponent.class);
    public static final ComponentMapper<exp.zhen.zayta.versions_unused.arcade_style_game.movable_items.components.MovableTag> ITEM_SHOVE = ComponentMapper.getFor(MovableTag.class);
    public static final ComponentMapper<exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.ColorComponent> COLOR = ComponentMapper.getFor(ColorComponent.class);
    public static final ComponentMapper<exp.zhen.zayta.versions_unused.arcade_style_game.movable_items.locker.LockerComponent> LOCKER = ComponentMapper.getFor(LockerComponent.class);

    public static final ComponentMapper<exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.lanterns.LanternTag> LANTERN = ComponentMapper.getFor(LanternTag.class);

    private Mappers(){}
}
