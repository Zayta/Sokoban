package exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.movable_items;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import java.util.ArrayList;

import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.movable_items.components.MovableTag;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.movable_items.components.PushComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.Direction;
import exp.zhen.zayta.main.game.essence_lab.movement.component.Position;
import exp.zhen.zayta.main.game.essence_lab.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.VelocityComponent;

public class RemoveItemSystem extends IteratingSystem {

    public RemoveItemSystem() {
        super(
                Family.all(
                        PositionTrackerComponent.class,
                        VelocityComponent.class,
                        PushComponent.class
                ).get()
        );
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Direction entityDirection = Mappers.MOVEMENT.get(entity).getDirection();
        PushComponent pocket = Mappers.POCKET.get(entity);
        ArrayList<Entity> items = pocket.getCarriedItems();
        for( int i = 0; i < items.size(); i++ )
            {
                Entity item = items.get(i);
                MovableTag itemMovement = Mappers.ITEM_SHOVE.get(item);
                if(entityDirection!=itemMovement.getDirection()||entityDirection==Direction.none){
                    pocket.remove(item);
                    //todo take this away for block to keep moving
                    itemMovement.setDirection(Direction.none);
//                    Mappers.MOVEMENT.get(item).setDirection(Direction.none);
                    i--;
                }
            }
    }
}
