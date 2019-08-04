package exp.zhen.zayta.main.game.essence_lab.blocks.block_player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.essence_lab.movement.Direction;
import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.Position;
import exp.zhen.zayta.main.game.essence_lab.movement.component.VelocityComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.WorldWrapTag;

public class WorldWrapPauseSystem extends IteratingSystem {

    private static final Logger log = new Logger(WorldWrapPauseSystem.class.getName(),Logger.DEBUG);

    private static final Family FAMILY = Family.all(
            WorldWrapTag.class,
            Position.class,
//            DimensionComponent.class,
            VelocityComponent.class,
            MovementLimitationComponent.class
    ).get();

    private float maxX, maxY;
    public WorldWrapPauseSystem(float maxX, float maxY)
    {
        super(FAMILY);
        this.maxX = maxX;
        this.maxY = maxY;
//        this.viewport = viewport;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Position position = Mappers.POSITION.get(entity);
        MovementLimitationComponent movementLimitationComponent =
                Mappers.MOVEMENT_LIMITATION.get(entity);
//        DimensionComponent dimension=Mappers.DIMENSION.get(entity);
        VelocityComponent movement = Mappers.MOVEMENT.get(entity);
//        log.debug("num world wrap entities: "+getEngine().getEntitiesFor(FAMILY).size());

        Direction direction = movement.getDirection();
        float x = position.getX(); float y = position.getY();

        if(direction==Direction.up&&y>maxY){
            movement.setDirection(Direction.none);
            position.set(x,maxY);
            movementLimitationComponent.setBlock(null,direction);
        }
        else if(direction==Direction.down&&y<0){
            movement.setDirection(Direction.none);
            position.set(x,0);
            movementLimitationComponent.setBlock(null,direction);
        }
        else if(direction==Direction.left&&x<0){
            movement.setDirection(Direction.none);
            position.set(0,y);
            movementLimitationComponent.setBlock(null,direction);
        }
        else if(direction==Direction.right&&x>maxX){
            movement.setDirection(Direction.none);
            position.set(maxX,y);
            movementLimitationComponent.setBlock(null,direction);
        }

    }
}
