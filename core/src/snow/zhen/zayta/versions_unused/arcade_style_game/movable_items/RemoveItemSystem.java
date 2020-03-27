package snow.zhen.zayta.versions_unused.arcade_style_game.movable_items;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import java.util.ArrayList;

import snow.zhen.zayta.versions_unused.arcade_style_game.movable_items.components.MovableTag;
import snow.zhen.zayta.versions_unused.arcade_style_game.movable_items.components.PushComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.PositionTrackerComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent;

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
        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction entityDirection = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT.get(entity).getDirection();
        PushComponent pocket = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.POCKET.get(entity);
        ArrayList<Entity> items = pocket.getCarriedItems();
        for( int i = 0; i < items.size(); i++ )
            {
                Entity item = items.get(i);
                MovableTag itemMovement = Mappers.ITEM_SHOVE.get(item);
                if(entityDirection!=itemMovement.getDirection()||entityDirection== Direction.none){
                    pocket.remove(item);
                    //todo take this away for block to keep moving
                    itemMovement.setDirection(snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction.none);
//                    Mappers.MOVEMENT.get(item).setDirection(Direction.none);
                    i--;
                }
            }
    }
}
