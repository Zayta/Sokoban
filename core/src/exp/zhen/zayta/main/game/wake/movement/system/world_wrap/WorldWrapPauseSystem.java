package exp.zhen.zayta.main.game.wake.movement.system.world_wrap;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.main.game.wake.movement.Direction;
import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.entity.components.labels.PlayerTag;
import exp.zhen.zayta.main.game.wake.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.wake.movement.component.Position;
import exp.zhen.zayta.main.game.wake.movement.component.VelocityComponent;

public class WorldWrapPauseSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            PlayerTag.class,
            WorldWrapTag.class,
            Position.class,
            DimensionComponent.class,
            VelocityComponent.class
    ).get();


    private final Viewport viewport;

    public WorldWrapPauseSystem(Viewport viewport)
    {
        super(FAMILY);
        this.viewport = viewport;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Position position = Mappers.POSITION.get(entity);
        DimensionComponent dimension=Mappers.DIMENSION.get(entity);
        VelocityComponent movement = Mappers.MOVEMENT.get(entity);

        Direction direction = movement.getDirection();
        float x = position.getX(); float y = position.getY();
        float maxX = viewport.getWorldWidth()-dimension.getWidth();
        float maxY = viewport.getWorldHeight()-dimension.getHeight();

        if(direction==Direction.up&&y>maxY){
            movement.setDirection(Direction.none);
            position.set(x,maxY);
        }
        else if(direction==Direction.down&&y<0){
            movement.setDirection(Direction.none);
            position.set(x,0);
        }
        else if(direction==Direction.left&&x<0){
            movement.setDirection(Direction.none);
            position.set(0,y);
        }
        else if(direction==Direction.right&&x>maxX){
            movement.setDirection(Direction.none);
            position.set(maxX,y);
        }

    }
    private boolean cannotMove(Direction direction, float x, float y, float maxX, float maxY){
        return (direction==Direction.up&&y>maxY)
                ||(direction==Direction.down&&y<0) //it is assumed minX and minY are 0
                ||(direction==Direction.left&&x<0)
                ||(direction==Direction.right&&x>maxX);
    }
}
