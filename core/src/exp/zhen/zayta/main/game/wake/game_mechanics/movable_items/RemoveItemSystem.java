package exp.zhen.zayta.main.game.wake.game_mechanics.movable_items;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import java.util.ArrayList;
import java.util.Iterator;

import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.movement.Direction;
import exp.zhen.zayta.main.game.wake.movement.component.Position;
import exp.zhen.zayta.main.game.wake.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.wake.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.wake.movement.component.VelocityComponent;

public class RemoveItemSystem extends IteratingSystem {

    public RemoveItemSystem() {
        super(
                Family.all(
                        PositionTrackerComponent.class,
                        Position.class,
                        VelocityComponent.class,
                        RectangularBoundsComponent.class,
                        PocketComponent.class
                ).get()
        );
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Direction entityDirection = Mappers.MOVEMENT.get(entity).getDirection();
        PocketComponent pocket = Mappers.POCKET.get(entity);
        ArrayList<Entity> items = pocket.getCarriedItems();

        for( int i = 0; i < items.size(); i++ )
        {
            Entity item = items.get(i);

            MovableTag itemMovement = Mappers.ITEM_SHOVE.get(item);
            if(entityDirection!=itemMovement.getDirection()){
                pocket.remove(item);
                //todo take this away for block to keep moving
                itemMovement.setDirection(Direction.none);
                i--;
            }
        }
    }
}
