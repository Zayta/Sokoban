package exp.zhen.zayta.main.game.wake.game_mechanics.movable_items;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Logger;

import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.Map;

import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.entity.id_tags.NighterTag;
import exp.zhen.zayta.main.game.wake.game_mechanics.movable_items.components.PushComponent;
import exp.zhen.zayta.main.game.wake.movement.Direction;
import exp.zhen.zayta.main.game.wake.movement.PositionTracker;
import exp.zhen.zayta.main.game.wake.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.main.game.wake.movement.component.Position;
import exp.zhen.zayta.main.game.wake.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.wake.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.wake.movement.component.VelocityComponent;
import exp.zhen.zayta.util.BiMap;

public class MoveItemSystem extends IteratingSystem {

    private static final Logger log = new Logger(MoveItemSystem.class.getName(),Logger.DEBUG);
    private static Family HOLDERS = Family.all(
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
        VelocityComponent entityMovement = Mappers.MOVEMENT.get(entity);
        RectangularBoundsComponent entityBounds = Mappers.RECTANGULAR_BOUNDS.get(entity);
        ArrayList<Entity> items = Mappers.POCKET.get(entity).getCarriedItems();
        Position entityPos = Mappers.POSITION.get(entity);
        //todo move every item in pocket by setting their positions.
        Direction direction = entityMovement.getDirection();
        switch (direction){
            case none:
                break;
            case up:
                for(Entity item: items){
                    MovementLimitationComponent itemMovementLimitation =
                            Mappers.MOVEMENT_LIMITATION.get(item);
                    if(itemMovementLimitation.getBlockedDirection()!=direction) {
                        Position itemPos = Mappers.POSITION.get(item);
                        itemPos.setY(entityBounds.getTop());
                    }
                    else{
                        MovementLimitationComponent entityMovementLimitation = Mappers.MOVEMENT_LIMITATION.get(entity);
                        if(entityMovementLimitation!=null) {
                            RectangularBoundsComponent itemBounds = Mappers.RECTANGULAR_BOUNDS.get(item);
                            entityMovement.setDirection(Direction.none);
                            entityPos.setY(itemBounds.getBottom());
                            entityMovementLimitation.setBlock(item,direction);
                        }
                    }
                }
                break;
            case down:
                for(Entity item: items){
                    MovementLimitationComponent itemMovementLimitation =
                            Mappers.MOVEMENT_LIMITATION.get(item);
                    if(itemMovementLimitation.getBlockedDirection()!=direction) {
                        Position itemPos = Mappers.POSITION.get(item);
                        itemPos.setY(entityBounds.getBottom());
                    }
                    else{
                        MovementLimitationComponent entityMovementLimitation = Mappers.MOVEMENT_LIMITATION.get(entity);
                        if(entityMovementLimitation!=null) {
                            RectangularBoundsComponent itemBounds = Mappers.RECTANGULAR_BOUNDS.get(item);
                            entityMovement.setDirection(Direction.none);
                            entityPos.setY(itemBounds.getTop());
                            entityMovementLimitation.setBlock(item,direction);
                        }
                    }

                }
                break;
            case left:
                for(Entity item: items){
                    MovementLimitationComponent itemMovementLimitation =
                            Mappers.MOVEMENT_LIMITATION.get(item);
                    if(itemMovementLimitation.getBlockedDirection()!=direction) {
                        Position itemPos = Mappers.POSITION.get(item);
                        itemPos.setX(entityBounds.getLeft());
                    }
                    else{
                        MovementLimitationComponent entityMovementLimitation = Mappers.MOVEMENT_LIMITATION.get(entity);
                        if(entityMovementLimitation!=null) {
                            RectangularBoundsComponent itemBounds = Mappers.RECTANGULAR_BOUNDS.get(item);
                            entityMovement.setDirection(Direction.none);
                            entityPos.setX(itemBounds.getRight());
                            entityMovementLimitation.setBlock(item,direction);
                        }
                    }
                }
                break;
            case right:
                for(Entity item: items){

                    MovementLimitationComponent itemMovementLimitation =
                            Mappers.MOVEMENT_LIMITATION.get(item);
                    if(itemMovementLimitation.getBlockedDirection()!=direction) {
                        Position itemPos = Mappers.POSITION.get(item);
                        itemPos.setX(entityBounds.getRight());
                    }
                    else{
                        MovementLimitationComponent entityMovementLimitation = Mappers.MOVEMENT_LIMITATION.get(entity);
                        if(entityMovementLimitation!=null) {
                            RectangularBoundsComponent itemBounds = Mappers.RECTANGULAR_BOUNDS.get(item);
                            entityMovement.setDirection(Direction.none);
                            entityPos.setX(itemBounds.getLeft());
                            entityMovementLimitation.setBlock(item,direction);
                        }
                    }
                }
                break;
        }
    }
//
//    public boolean itemWillCollideWithBlocks(Rectangle nextBoundsOfItem) {
//
//            int key = PositionTracker.generateKey(nextBoundsOfItem.x,nextBoundsOfItem.y);
//            int keyAbove = key + PositionTracker.n;
//            int keyBelow = key - PositionTracker.n;
//            int [] keys = new int []{
//                    key,key-1,key+1,keyAbove-1,keyAbove,keyAbove+1,keyBelow-1,keyBelow,keyBelow+1
//            };
//            return checkForBlocks(nextBoundsOfItem,keys);
//
//    }
//    private boolean checkForBlocks(Rectangle nextBoundsOfItem, int [] blockKeys){
//        boolean collidedWithBlocks = false;
//        for (int key: blockKeys) {
//            Entity block = blocksBiMap.get(key);
//
//            collidedWithBlocks = collidedWithBlocks||
//                    (block!=null&&checkCollisionBetween(nextBoundsOfItem,block));
//        }
//        return collidedWithBlocks;
//    }
//    private boolean checkCollisionBetween(Rectangle nextBoundsOfItem, Entity block)
//    {
//        RectangularBoundsComponent blockBounds = Mappers.RECTANGULAR_BOUNDS.get(block);
//
//        return Intersector.overlaps(blockBounds.getBounds(),nextBoundsOfItem);
//    }
//
//    private Rectangle getNextBoundsOfItem(RectangularBoundsComponent currentItemBounds, float newLeft, float newBottom){
//        Rectangle nextItemBounds = new Rectangle(currentItemBounds.getBounds());
//        nextItemBounds.setPosition(newLeft,newBottom);
//
//        return nextItemBounds;
//    }

}
