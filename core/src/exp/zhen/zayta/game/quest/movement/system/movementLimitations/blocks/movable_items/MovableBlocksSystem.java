package exp.zhen.zayta.game.quest.movement.system.movementLimitations.blocks.movable_items;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Arrays;
import java.util.Stack;

import exp.zhen.zayta.game.quest.movement.Direction;
import exp.zhen.zayta.assets.RegionNames;
import exp.zhen.zayta.common.Mappers;
import exp.zhen.zayta.config.SizeManager;
import exp.zhen.zayta.game.quest.entity.undead.nur.NighterTag;
import exp.zhen.zayta.game.quest.movement.PositionTracker;
import exp.zhen.zayta.game.quest.Quest;
import exp.zhen.zayta.game.quest.movement.component.CircularBoundsComponent;
import exp.zhen.zayta.game.quest.movement.component.DimensionComponent;
import exp.zhen.zayta.game.quest.movement.component.Position;
import exp.zhen.zayta.game.quest.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.game.quest.movement.component.VelocityComponent;
import exp.zhen.zayta.game.quest.entity.Arrangements;
import exp.zhen.zayta.util.BiMap;

public class MovableBlocksSystem extends EntitySystem  {

    /*todo this system is flawed. can only move one block at a time and left and down movements are laggy.
    * I suspect some of the lagginess is because of circular bounds. Maybe I should switch to rectangular*/
    /*Moveable Blocks have:
    *   Position
        DimensionComponent
        CircularBoundsComponent
        WorldWrapTag
        VelocityComponent
        */

    private PooledEngine engine; private final Viewport viewport;
    private static final Logger log = new Logger(MovableBlocksSystem.class.getName(),Logger.DEBUG);

    private BiMap<Integer,Entity> movableBlocksBiMap;
    //families are entities that can collide
    private final Family UNDEADS;

//    private ArrayList<Entity> currentParents;
//    Stack<Entity> blocksToBePushed;

    public MovableBlocksSystem(PooledEngine engine,Viewport viewport){
        this.engine = engine; this.viewport = viewport;
        UNDEADS = Family.all(
                NighterTag.class,//todo for debug only, remove when done
                PositionTrackerComponent.class,
                Position.class,
                VelocityComponent.class,
                CircularBoundsComponent.class
        ).get();
        movableBlocksBiMap = new BiMap<Integer, Entity>();
        initMovableBlocks();
    }

    private void initMovableBlocks(){
        int numBlocks = 5;
        Vector2[] points = Arrangements.circle(numBlocks,SizeManager.WORLD_CENTER_X,SizeManager.WORLD_CENTER_Y,SizeManager.WORLD_WIDTH/3);
        for(int i =0; i<numBlocks; i++)
        {
            int key = PositionTracker.generateKey(points[i].x,points[i].y);
            Entity block = Quest.manufacturer.makeEntityInPos(points[i].x,points[i].y,
                    MovableTag.class,RegionNames.BACKGROUND);
            movableBlocksBiMap.put(key, block);
            VelocityComponent velocityComponent = engine.createComponent(VelocityComponent.class);
            block.add(velocityComponent);
        }
//        currentParents = new ArrayList<Entity>();
//        blocksToBePushed = new Stack<Entity>();
//        log.debug("blockBiMap: "+movableBlocksBiMap);
    }

    @Override
    public void update(float deltaTime) {

        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(UNDEADS);

        for(Entity entity: entities) {
            Stack<Entity> blocks = getSurroundingBlocks(entity,Mappers.MOVEMENT.get(entity).getDirection());
            pushBlocksBy(entity,blocks);
        }
//        blocksToBePushed.clear();
    }
    private void pushBlocksBy(Entity entityThatPushes, Stack<Entity> blocksToBePushed){
        Direction direction = Mappers.MOVEMENT.get(entityThatPushes).getDirection();

        while(!blocksToBePushed.isEmpty()){
            Entity blockToBePushed = blocksToBePushed.pop();
            Stack<Entity> blocksAroundBlockToBePushed=getSurroundingBlocks(blockToBePushed,direction);
            if(!blocksAroundBlockToBePushed.isEmpty()){
                pushBlocksBy(blockToBePushed,blocksAroundBlockToBePushed);
                log.debug("Block has blocks around it");
            }

            blocksToBePushed.remove(blockToBePushed);
            Vector2 nextPos = calculateNextPos(blockToBePushed,entityThatPushes,direction);
            if(canMove(blockToBePushed,nextPos.x,nextPos.y)) {
                setBlockPosition(blockToBePushed, nextPos.x, nextPos.y);
            }

        }
    }

    private Stack<Entity> getSurroundingBlocks(Entity entity,Direction direction){
        Stack<Entity> blocksToCheck = new Stack<Entity>();
        int key = findKey(entity);
        int keyAbove = key+PositionTracker.n;
        int keyBelow = key-PositionTracker.n;

        int [] keys = new int[6];
        keys[5] = key;
        switch (direction){
            case none:
                keys = new int [0];
                break;
            case up:
                keys[0]= keyAbove;keys[1]= keyAbove+1;keys[2]= keyAbove-1;
                keys[3] = key-1;keys[4] = key+1;
                break;
            case down:
                keys[0]= keyBelow;keys[1]= keyBelow+1;keys[2]= keyBelow-1;
                keys[3] = key-1;keys[4] = key+1;
                break;
            case left:
                keys[0]= keyAbove-1;keys[1]= key-1;keys[2]= keyBelow-1;
                keys[3] = keyAbove;keys[4] = keyBelow;
                break;
            case right:
                keys[0]= keyAbove+1;keys[1]= key+1;keys[2]= keyBelow+1;
                keys[3] = keyAbove;keys[4] = keyBelow;
                break;
        }

        for(int k:keys){
            Entity blockToPush = movableBlocksBiMap.get(k);
            if(blockToPush!=null&&blockToPush!=entity&&collisionBetween(entity,blockToPush)){
                blocksToCheck.push(blockToPush);
            }
        }

        System.out.println(Arrays.asList(blocksToCheck).toArray().toString());
        return blocksToCheck;
    }

    private boolean canMove(Entity block, float nextPosX,float nextPosY){
        return withinBounds(block,nextPosX,nextPosY)
                &&
                positionHashAvailable(PositionTracker.generateKey(nextPosX,nextPosY),block);
    }
    //todo find way to make sure all keys in biMap are unique
//    private int [] getBlocksToCheck(Entity entity, Direction direction, boolean isBlock){
//        int key = findKey(entity);
//        int keyAbove = key+PositionTracker.n;
//        int keyBelow = key-PositionTracker.n;
//        int [] keys;
//        if(isBlock){keys = new int [5];}
//        else{
//            keys = new int [6];
//            keys[5] = key;
//        }
//        switch (direction){
//            case none:
//                keys = new int [0];
//                break;
//            case up:
//                keys[0]= keyAbove;keys[1]= keyAbove+1;keys[2]= keyAbove-1;
//                keys[3] = key-1;keys[4] = key+1;
//                break;
//            case down:
//                keys[0]= keyBelow;keys[1]= keyBelow+1;keys[2]= keyBelow-1;
//                keys[3] = key-1;keys[4] = key+1;
//                break;
//            case left:
//                keys[0]= keyAbove-1;keys[1]= key-1;keys[2]= keyBelow-1;
//                keys[3] = keyAbove;keys[4] = keyBelow;
//                break;
//            case right:
//                keys[0]= keyAbove+1;keys[1]= key+1;keys[2]= keyBelow+1;
//                keys[3] = keyAbove;keys[4] = keyBelow;
//                break;
//        }
//        return keys;
//    }

    private int findKey(Entity entity){

        if(movableBlocksBiMap.containsValue(entity))//if entity is a block return key from block bimap. clumsy code r
            return movableBlocksBiMap.getKey(entity);
        return Mappers.POSITION_TRACKER.get(entity).getPositionBiMap().getBiMap().getKey(entity);
    }

    private boolean withinBounds(Entity block, float newPosX, float newPosY){
        DimensionComponent dimension = Mappers.DIMENSION.get(block);
        float maxX = viewport.getWorldWidth()-dimension.getWidth(),maxY = viewport.getWorldHeight()-dimension.getHeight();
        return newPosX>0&&newPosX<maxX
                && newPosY>0&&newPosY<maxY;

    }


    private Vector2 calculateNextPos(Entity block,Entity entityThatPushes,Direction direction){
        Position posOfEntityThatPushes = Mappers.POSITION.get(entityThatPushes);
        Position posOfBlock = Mappers.POSITION.get(block);
        CircularBoundsComponent boundsOfEntityThatPushes = Mappers.BOUNDS.get(entityThatPushes);
        CircularBoundsComponent boundsOfBlock = Mappers.BOUNDS.get(block);
        float offset = boundsOfEntityThatPushes.getRadius()+boundsOfBlock.getRadius();
        float nextPosX=posOfBlock.getX(), nextPosY=posOfBlock.getY();
        //todo update velocity of block before using this method whne a block does the pushing
        switch (direction){
            case none://todo handle none case in future. rn it should overlap w nighter
                break;
            case up:
                nextPosY = posOfEntityThatPushes.getY()+offset;
                break;
            case down:
                nextPosY = posOfEntityThatPushes.getY()-offset;
                break;
            case left:
                nextPosX = posOfEntityThatPushes.getX()-offset;
                break;
            case right:
                nextPosX = posOfEntityThatPushes.getX()+offset;
                break;
        }

        Vector2 vector2 = new Vector2(nextPosX,nextPosY);
        return vector2;
    }

    private boolean positionHashAvailable(int newKey,Entity block)
    {
        return !movableBlocksBiMap.containsKey(newKey)||movableBlocksBiMap.get(newKey)==block;
    }
    private boolean collisionBetween(Entity entity, Entity block)
    {
        CircularBoundsComponent playerBounds = Mappers.BOUNDS.get(entity);
        CircularBoundsComponent blockBounds = Mappers.BOUNDS.get(block);

        return Intersector.overlaps(playerBounds.getBounds(),blockBounds.getBounds());
    }

    private void setBlockPosition(Entity block, float nextPosX, float nextPosY){
        Mappers.POSITION.get(block).set(nextPosX,nextPosY);
        PositionTracker.updateBiMap(movableBlocksBiMap,block,nextPosX,nextPosY);
    }

    public void reset() {
        movableBlocksBiMap.clear();
    }

//
//    private boolean movedSurroundingBlocks(Entity entity, int [] keys,Direction direction){
//        for (int key: keys) {
//            Entity block = movableBlocksBiMap.get(key);
//            //todo parent may be checking block, and then block checks parent as well. That's where cycle comes from.
//            if (block != null && block!=entity && !blocksToBePushed.contains(block)/* && !currentParents.contains(block)*/) {
//                if (collisionBetween(entity, block)) {
//                    log.debug("Block collide");
//                    if(movedBlock(block,direction)){
//                        return true;
//                    }
//                    else{
//                        return false;
//                    }
//                }
//            }
//        }
//        return true;
//    }
//    private boolean movedBlock(Entity block, Direction direction){
//        //block can move if it is within bounds and its path is not blocked by immovable block
//        Position blockPos = Mappers.POSITION.get(block);
//        DimensionComponent dimension = Mappers.DIMENSION.get(block);
//        boolean withinBounds=true, pathNotBlocked = true;
//
//        float maxX = viewport.getWorldWidth()-dimension.getWidth(),maxY = viewport.getWorldHeight()-dimension.getHeight();
//        switch (direction){
//            case up:
//                withinBounds = blockPos.getY()<maxY;
//                break;
//            case down:
//                withinBounds=blockPos.getY()>0;
//                break;
//            case left:
//                withinBounds = blockPos.getX()>0;
//                break;
//            case right:
//                withinBounds = blockPos.getX()<maxX;
//                break;
//            case none:
//                break;
//        }
//        int [] blocksToCheck = getBlocksToCheck(block,direction,true);
//        if(blocksToCheck.length>0) {
//            pathNotBlocked = movedSurroundingBlocks(block, blocksToCheck, direction);
//        }
//        return withinBounds&&pathNotBlocked;
//    }

//
//    private boolean withinBounds(Entity block, Direction direction){
//        Position blockPos = Mappers.POSITION.get(block);
//        DimensionComponent dimension = Mappers.DIMENSION.get(block);
//        boolean withinBounds=true;
//
//        float maxX = viewport.getWorldWidth()-dimension.getWidth(),maxY = viewport.getWorldHeight()-dimension.getHeight();
//        switch (direction){
//            case up:
//                withinBounds = blockPos.getY()<maxY;
//                break;
//            case down:
//                withinBounds=blockPos.getY()>0;
//                break;
//            case left:
//                withinBounds = blockPos.getX()>0;
//                break;
//            case right:
//                withinBounds = blockPos.getX()<maxX;
//                break;
//            case none:
//                break;
//        }
//        return withinBounds;
//    }

//    private void pushBlocks(Entity entityThatPushes, Direction direction){
//        while(!blocksToBePushed.isEmpty()){
//            Entity block = blocksToBePushed.pop();
//            Mappers.MOVEMENT.get(block).setDirection(direction);
//            Vector2 nextPos = calculateNextPos(block, entityThatPushes, direction);
//
//            if(canMove(block,nextPos.x,nextPos.y)) {
//                setBlockPosition(block, nextPos.x, nextPos.y);
//            }
//
//            Mappers.MOVEMENT.get(block).setDirection(Direction.none);
//        }
//    }

}
