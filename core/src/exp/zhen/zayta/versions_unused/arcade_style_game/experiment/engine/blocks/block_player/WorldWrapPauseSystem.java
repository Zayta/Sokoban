package exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.blocks.block_player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.WorldWrapComponent;
//import exp.zhen.zayta.main.game.experiment.engine.movement.component.WorldWrapTag;

public class WorldWrapPauseSystem extends IteratingSystem {

    private static final Logger log = new Logger(WorldWrapPauseSystem.class.getName(),Logger.DEBUG);

    private static final Family FAMILY = Family.all(
            WorldWrapComponent.class,
            Position.class,
//            DimensionComponent.class,
            VelocityComponent.class,
            MovementLimitationComponent.class
    ).get();

//    private float maxX, maxY;
    public WorldWrapPauseSystem()
    {
        super(FAMILY);
//        this.viewport = viewport;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        WorldWrapComponent worldWrapComponent = Mappers.WORLD_WRAP.get(entity);
        Position position = Mappers.POSITION.get(entity);
        MovementLimitationComponent movementLimitationComponent =
                Mappers.MOVEMENT_LIMITATION.get(entity);
//        DimensionComponent dimension=Mappers.DIMENSION.get(entity);
        VelocityComponent movement = Mappers.MOVEMENT.get(entity);
//        ////log.debug("num world wrap entities: "+getEngine().getEntitiesFor(FAMILY).size());

        Direction direction = movement.getDirection();
        float x = position.getX(); float y = position.getY();

        if(direction==Direction.up&&y>worldWrapComponent.getTop()){
            movement.setDirection(Direction.none);
            position.set(x,worldWrapComponent.getTop());
            movementLimitationComponent.setBlock(null,direction);
        }
        else if(direction==Direction.down&&y<worldWrapComponent.getBottom()){
            movement.setDirection(Direction.none);
            position.set(x,worldWrapComponent.getBottom());
            movementLimitationComponent.setBlock(null,direction);
        }
        else if(direction==Direction.left&&x<worldWrapComponent.getLeft()){
            movement.setDirection(Direction.none);
            position.set(worldWrapComponent.getLeft(),y);
            movementLimitationComponent.setBlock(null,direction);
        }
        else if(direction==Direction.right&&x>worldWrapComponent.getRight()){
            movement.setDirection(Direction.none);
            position.set(worldWrapComponent.getRight(),y);
            movementLimitationComponent.setBlock(null,direction);
        }

    }
}
