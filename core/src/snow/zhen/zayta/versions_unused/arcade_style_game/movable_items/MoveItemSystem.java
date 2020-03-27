package snow.zhen.zayta.versions_unused.arcade_style_game.movable_items;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import snow.zhen.zayta.versions_unused.arcade_style_game.movable_items.components.PushComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionComparator;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.PositionTrackerComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent;

public class MoveItemSystem extends IteratingSystem {

    private static final Logger log = new Logger(MoveItemSystem.class.getName(),Logger.DEBUG);
    private static Family HOLDERS = Family.all(
            PositionTrackerComponent.class,
            snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position.class,
            snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent.class,
            snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent.class,
            PushComponent.class
    ).get();
    public MoveItemSystem() {
        super(HOLDERS);
    }

    @Override
    public void update(float deltaTime) {
        List<Entity> entities = Arrays.asList(getEntities().toArray());
        Collections.sort(entities, new PositionComparator());
        for (int i = 0; i < entities.size(); ++i) {
            processEntity(entities.get(i), deltaTime);
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        VelocityComponent entityMovement = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT.get(entity);
        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent entityBounds = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.RECTANGULAR_BOUNDS.get(entity);
        ArrayList<Entity> items = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.POCKET.get(entity).getCarriedItems();
        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position entityPos = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.POSITION.get(entity);
        //todo move every item in pocket by setting their positions.
        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction direction = entityMovement.getDirection();
        switch (direction){
            case none:
                break;
            case up:
                for(Entity item: items){
                    snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent itemMovementLimitation =
                            snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT_LIMITATION.get(item);
                    if(itemMovementLimitation.getBlockedDirection()!=direction) {
                        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position itemPos = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.POSITION.get(item);
                        itemPos.setY(entityBounds.getTop());
                    }
                    else{
                        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent entityMovementLimitation = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT_LIMITATION.get(entity);
                        if(entityMovementLimitation!=null) {
                            snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent itemBounds = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.RECTANGULAR_BOUNDS.get(item);
                            entityMovement.setDirection(Direction.none);
                            entityPos.setY(itemBounds.getBottom());
                            entityMovementLimitation.setBlock(item,direction);
                        }
                    }
                }
                break;
            case down:
                for(Entity item: items){
                    snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent itemMovementLimitation =
                            snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT_LIMITATION.get(item);
                    if(itemMovementLimitation.getBlockedDirection()!=direction) {
                        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position itemPos = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.POSITION.get(item);
                        itemPos.setY(entityBounds.getBottom());
                    }
                    else{
                        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent entityMovementLimitation = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT_LIMITATION.get(entity);
                        if(entityMovementLimitation!=null) {
                            snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent itemBounds = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.RECTANGULAR_BOUNDS.get(item);
                            entityMovement.setDirection(Direction.none);
                            entityPos.setY(itemBounds.getTop());
                            entityMovementLimitation.setBlock(item,direction);
                        }
                    }

                }
                break;
            case left:
                for(Entity item: items){
                    snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent itemMovementLimitation =
                            snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT_LIMITATION.get(item);
                    if(itemMovementLimitation.getBlockedDirection()!=direction) {
                        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position itemPos = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.POSITION.get(item);
                        itemPos.setX(entityBounds.getLeft());
                    }
                    else{
                        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent entityMovementLimitation = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT_LIMITATION.get(entity);
                        if(entityMovementLimitation!=null) {
                            snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent itemBounds = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.RECTANGULAR_BOUNDS.get(item);
                            entityMovement.setDirection(Direction.none);
                            entityPos.setX(itemBounds.getRight());
                            entityMovementLimitation.setBlock(item,direction);
                        }
                    }
                }
                break;
            case right:
                for(Entity item: items){

                    snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent itemMovementLimitation =
                            snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT_LIMITATION.get(item);
                    if(itemMovementLimitation.getBlockedDirection()!=direction) {
                        Position itemPos = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.POSITION.get(item);
                        itemPos.setX(entityBounds.getRight());
                    }
                    else{
                        MovementLimitationComponent entityMovementLimitation = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT_LIMITATION.get(entity);
                        if(entityMovementLimitation!=null) {
                            RectangularBoundsComponent itemBounds = Mappers.RECTANGULAR_BOUNDS.get(item);
                            entityMovement.setDirection(snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction.none);
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
//            Entity block = blocksKeyListMap.get(key);
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
