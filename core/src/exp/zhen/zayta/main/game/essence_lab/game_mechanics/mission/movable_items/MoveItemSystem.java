package exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.movable_items;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.movable_items.components.PushComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.Direction;
import exp.zhen.zayta.main.game.essence_lab.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.Position;
import exp.zhen.zayta.main.game.essence_lab.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.VelocityComponent;

public class MoveItemSystem extends EntitySystem {

    private static final Logger log = new Logger(MoveItemSystem.class.getName(),Logger.DEBUG);
    private static Family HOLDERS = Family.all(
            PositionTrackerComponent.class,
            Position.class,
            VelocityComponent.class,
            RectangularBoundsComponent.class,
            PushComponent.class
    ).get();
    private ImmutableArray<Entity> entities;
    private PriorityQueue<Entity> upMovingEntities,downMovingEntities,leftMovingEntities,rightMovingEntities;

    //    private PriorityQueue<Entity> upMovingEntities,downMovingEntities,leftMovingEntities,rightMovingEntities;

    public static Comparator<Entity> verticalComparator = new Comparator<Entity>() {
        @Override
        public int compare(Entity entity1, Entity entity2) {
            float scalar = 100;//arbitrary scalar
            Position position1 = Mappers.POSITION.get(entity1);
            Position position2 = Mappers.POSITION.get(entity2);
            // ascending order
            return (int)(scalar*position2.getY())-(int)(scalar*position1.getY());
            // descending order
            // return fruit1.getQuantity() - fruit2.getQuantity()
        }
    };
    public static Comparator<Entity> horizontalComparator = new Comparator<Entity>() {
        @Override
        public int compare(Entity entity1, Entity entity2) {
            float scalar = 100;//arbitrary scalar
            Position position1 = Mappers.POSITION.get(entity1);
            Position position2 = Mappers.POSITION.get(entity2);
            // ascending order
            return (int)(scalar*position2.getX())-(int)(scalar*position1.getX());
            // descending order
            // return fruit1.getQuantity() - fruit2.getQuantity()
        }
    };
    public MoveItemSystem(PooledEngine engine) {
//        super(HOLDERS);
        entities = engine.getEntitiesFor(HOLDERS);
        int size = entities.size();
        upMovingEntities = new PriorityQueue<Entity>(size,Collections.<Entity>reverseOrder(verticalComparator));
        downMovingEntities = new PriorityQueue<Entity>(size,verticalComparator);
        leftMovingEntities = new PriorityQueue<Entity>(size, horizontalComparator);
        rightMovingEntities = new PriorityQueue<Entity>(size, Collections.<Entity>reverseOrder(horizontalComparator));

    }

    @Override
    public void update(float deltaTime) {

        for(Entity entity:entities){
            switch (Mappers.MOVEMENT.get(entity).getDirection()){
                case up:
                    upMovingEntities.offer(entity);
                    break;
                case down:
                    downMovingEntities.offer(entity);
                    break;
                case left:
                    leftMovingEntities.offer(entity);
                    break;
                case right:
                    rightMovingEntities.offer(entity);
                    break;
            }
        }


        while(!upMovingEntities.isEmpty()){
            processEntity(upMovingEntities.poll(),deltaTime);
        }

        while(!downMovingEntities.isEmpty()){
            processEntity(downMovingEntities.poll(),deltaTime);
        }

        while(!leftMovingEntities.isEmpty()){
            processEntity(leftMovingEntities.poll(),deltaTime);
        }

        while(!rightMovingEntities.isEmpty()){
            processEntity(rightMovingEntities.poll(),deltaTime);
        }
//        for (int i = entities.size()-1; i >= 0; i--) {
//            processEntity(entities.get(i), deltaTime);
//        }
    }

//    @Override
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
