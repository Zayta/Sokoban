package exp.zhen.zayta.main.game.wake.game_mechanics.movable_items;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Logger;

import java.util.ArrayList;

import exp.zhen.zayta.main.game.conquest.soldiers.nur.Nighter;
import exp.zhen.zayta.main.game.wake.WakeMode;
import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.entity.id_tags.NighterTag;
import exp.zhen.zayta.main.game.wake.movement.Direction;
import exp.zhen.zayta.main.game.wake.movement.PositionTracker;
import exp.zhen.zayta.main.game.wake.movement.component.Position;
import exp.zhen.zayta.main.game.wake.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.wake.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.wake.movement.component.VelocityComponent;

public class MoveItemSystem extends IteratingSystem {

    private static final Logger log = new Logger(MoveItemSystem.class.getName(),Logger.DEBUG);
    //todo is this system necessary since every pocket item has a velocity component already?
    private static Family HOLDERS = Family.all(
            NighterTag.class,//todo remove later, for debug only
            PositionTrackerComponent.class,
            Position.class,
            VelocityComponent.class,
            RectangularBoundsComponent.class,
            PushComponent.class
    ).get();

    public MoveItemSystem() {
        super(HOLDERS);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Direction direction = Mappers.MOVEMENT.get(entity).getDirection();
        RectangularBoundsComponent entityBounds = Mappers.RECTANGULAR_BOUNDS.get(entity);
        ArrayList<Entity> items = Mappers.POCKET.get(entity).getCarriedItems();

        //todo move every item in pocket by setting their positions.
        switch (direction){
            case none:
                break;
            case up:
                for(Entity item: items){
                    Position position = Mappers.POSITION.get(item);
                    position.setY(entityBounds.getTop());
                }
                break;
            case down:
                for(Entity item: items){
                    Position position = Mappers.POSITION.get(item);
                    position.setY(entityBounds.getBottom());
                }
                break;
            case left:
                for(Entity item: items){
                    Position position = Mappers.POSITION.get(item);
                    position.setX(entityBounds.getLeft());
                }
                break;
            case right:
                for(Entity item: items){
                    Position position = Mappers.POSITION.get(item);
                    position.setX(entityBounds.getRight());
                }
                break;
        }
        log.debug("Size of pocket items: "+items.size());
    }

}
