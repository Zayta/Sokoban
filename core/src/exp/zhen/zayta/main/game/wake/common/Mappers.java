package exp.zhen.zayta.main.game.wake.common;

import com.badlogic.ashley.core.ComponentMapper;

import exp.zhen.zayta.main.game.wake.entity.components.NameTag;
import exp.zhen.zayta.main.game.wake.entity.components.labels.id_tags.MortalTag;
import exp.zhen.zayta.main.game.wake.entity.components.properties.HealthComponent;
import exp.zhen.zayta.main.game.wake.movement.component.Position;
import exp.zhen.zayta.main.game.wake.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.wake.collision.mission.stone_gathering.StoneTag;
import exp.zhen.zayta.main.game.wake.visual.AnimationComponent;
import exp.zhen.zayta.main.game.wake.movement.component.CircularBoundsComponent;
import exp.zhen.zayta.main.game.wake.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.wake.movement.component.VelocityComponent;
import exp.zhen.zayta.main.game.wake.entity.components.properties.ObstacleComponent;
import exp.zhen.zayta.main.game.wake.visual.TextureComponent;

public class Mappers {

    public static final ComponentMapper<CircularBoundsComponent> BOUNDS = ComponentMapper.getFor(CircularBoundsComponent.class);

    public static final ComponentMapper<VelocityComponent> MOVEMENT = ComponentMapper.getFor(VelocityComponent.class);

    public static final ComponentMapper<Position> POSITION = ComponentMapper.getFor(Position.class);
    public static final ComponentMapper<MortalTag> CIVILIAN =
            ComponentMapper.getFor(MortalTag.class);

    public static final ComponentMapper<StoneTag> STONE = ComponentMapper.getFor(StoneTag.class);

    public static final ComponentMapper<ObstacleComponent> OBSTACLE =
ComponentMapper.getFor(ObstacleComponent.class);

    public static final ComponentMapper <TextureComponent> TEXTURE = ComponentMapper.getFor(TextureComponent.class);
    public static final ComponentMapper <AnimationComponent> ANIMATION = ComponentMapper.getFor(AnimationComponent.class);

    public static final ComponentMapper<DimensionComponent> DIMENSION = ComponentMapper.getFor(DimensionComponent.class);

    public static final ComponentMapper<PositionTrackerComponent> POSITION_TRACKER = ComponentMapper.getFor(PositionTrackerComponent.class);

    public static final ComponentMapper<HealthComponent> BATTLESTATS = ComponentMapper.getFor(HealthComponent.class);

    public static final ComponentMapper<NameTag> NAMETAG = ComponentMapper.getFor(NameTag.class);

    private Mappers(){}
}
