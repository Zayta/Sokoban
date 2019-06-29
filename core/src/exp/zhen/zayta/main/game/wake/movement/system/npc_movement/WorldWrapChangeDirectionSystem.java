package exp.zhen.zayta.main.game.wake.movement.system.npc_movement;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;

import exp.zhen.zayta.main.game.wake.movement.Direction;
import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.entity.components.labels.NPCTag;
import exp.zhen.zayta.main.game.wake.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.wake.movement.component.Position;
import exp.zhen.zayta.main.game.wake.movement.component.VelocityComponent;
import exp.zhen.zayta.main.game.wake.movement.component.WorldWrapTag;

public class WorldWrapChangeDirectionSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            NPCTag.class,
//            MortalTag.class,
            WorldWrapTag.class,
            Position.class,
            DimensionComponent.class,
            VelocityComponent.class
    ).get();


    private final MapProperties mapProperties;

    public WorldWrapChangeDirectionSystem(TiledMap tiledMap)
    {
        super(FAMILY);
        this.mapProperties = tiledMap.getProperties();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Position position = Mappers.POSITION.get(entity);
        DimensionComponent dimension=Mappers.DIMENSION.get(entity);
        VelocityComponent movement = Mappers.MOVEMENT.get(entity);

        Direction direction = movement.getDirection();
        float x = position.getX(); float y = position.getY();
        float maxX = mapProperties.get("width", Integer.class)-dimension.getWidth();
        float maxY = mapProperties.get("height", Integer.class)-dimension.getHeight();


        if(direction==Direction.up&&y>maxY){
            movement.setDirection(Direction.generateDirectionExcluding(Direction.up));
        }
        else if(direction==Direction.down&&y<0){
            movement.setDirection(Direction.generateDirectionExcluding(Direction.down));
        }
        else if(direction==Direction.left&&x<0){
            movement.setDirection(Direction.generateDirectionExcluding(Direction.left));
        }
        else if(direction==Direction.right&&x>maxX){
            movement.setDirection(Direction.generateDirectionExcluding(Direction.right));
        }

    }
}
