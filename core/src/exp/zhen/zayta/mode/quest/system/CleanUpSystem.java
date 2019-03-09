package exp.zhen.zayta.mode.quest.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import exp.zhen.zayta.mode.quest.component.labels.CleanUpTag;
import exp.zhen.zayta.mode.quest.component.properties.movement.Position;

public class CleanUpSystem extends IteratingSystem {
    private static final Family FAMILY = Family.all(
            CleanUpTag.class,
            Position.class
    ).get();

    public CleanUpSystem()
    {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
//        Position position = Mappers.POSITION.get(entity);
//
//        if(position.getY()< -SizeManager.OBSTACLE_SIZE)
//        {
//            getEngine().removeEntity(entity);//schedules removal. removes all at scheduled at same time. its SCHEDULED to be removed, not actual remove at this tiem. add is also scheduled operation
//        }

    }
}
