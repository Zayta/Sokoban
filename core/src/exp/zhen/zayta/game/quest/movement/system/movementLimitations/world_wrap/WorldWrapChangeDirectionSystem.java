package exp.zhen.zayta.game.quest.movement.system.movementLimitations.world_wrap;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.viewport.Viewport;

import exp.zhen.zayta.game.quest.movement.Direction;
import exp.zhen.zayta.common.Mappers;
import exp.zhen.zayta.game.quest.component.labels.NPCTag;
import exp.zhen.zayta.game.quest.movement.component.DimensionComponent;
import exp.zhen.zayta.game.quest.movement.component.Position;
import exp.zhen.zayta.game.quest.movement.component.VelocityComponent;

public class WorldWrapChangeDirectionSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            NPCTag.class,
//            MortalTag.class,
            WorldWrapTag.class,
            Position.class,
            DimensionComponent.class,
            VelocityComponent.class
    ).get();


    private final Viewport viewport;

    public WorldWrapChangeDirectionSystem(Viewport viewport)
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
