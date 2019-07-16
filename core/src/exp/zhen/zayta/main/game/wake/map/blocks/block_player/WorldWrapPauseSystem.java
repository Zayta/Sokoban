package exp.zhen.zayta.main.game.wake.map.blocks.block_player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.wake.entity.components.labels.PlayerTag;
import exp.zhen.zayta.main.game.wake.movement.Direction;
import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.wake.movement.component.Position;
import exp.zhen.zayta.main.game.wake.movement.component.VelocityComponent;
import exp.zhen.zayta.main.game.wake.movement.component.WorldWrapTag;

public class WorldWrapPauseSystem extends IteratingSystem {

    private static final Logger log = new Logger(WorldWrapPauseSystem.class.getName(),Logger.DEBUG);

    private static final Family FAMILY = Family.all(
            WorldWrapTag.class,
            Position.class,
            DimensionComponent.class,
            VelocityComponent.class
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
        DimensionComponent dimension=Mappers.DIMENSION.get(entity);
        VelocityComponent movement = Mappers.MOVEMENT.get(entity);

        Direction direction = movement.getDirection();
        float x = position.getX(); float y = position.getY();

        if(direction==Direction.up&&y>maxY){
            movement.setDirection(Direction.none);
//            position.set(x,maxY);
        }
        else if(direction==Direction.down&&y<0){
            movement.setDirection(Direction.none);
//            position.set(x,0);
        }
        else if(direction==Direction.left&&x<0){
            movement.setDirection(Direction.none);
//            position.set(0,y);
        }
        else if(direction==Direction.right&&x>maxX){
            movement.setDirection(Direction.none);
//            position.set(maxX,y);
        }

    }
    private boolean cannotMove(Direction direction, float x, float y, float maxX, float maxY){
        return (direction==Direction.up&&y>maxY)
                ||(direction==Direction.down&&y<0) //it is assumed minX and minY are 0
                ||(direction==Direction.left&&x<0)
                ||(direction==Direction.right&&x>maxX);
    }
}
