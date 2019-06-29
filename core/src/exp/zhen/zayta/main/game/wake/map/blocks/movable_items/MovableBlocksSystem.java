package exp.zhen.zayta.main.game.wake.map.blocks.movable_items;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Stack;

import exp.zhen.zayta.main.game.wake.assets.WPRegionNames;
import exp.zhen.zayta.main.game.wake.movement.Direction;
import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.wake.entity.components.labels.id_tags.NighterTag;
import exp.zhen.zayta.main.game.wake.movement.PositionTracker;
import exp.zhen.zayta.main.game.wake.movement.component.CircularBoundsComponent;
import exp.zhen.zayta.main.game.wake.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.wake.movement.component.Position;
import exp.zhen.zayta.main.game.wake.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.wake.movement.component.VelocityComponent;
import exp.zhen.zayta.main.game.wake.entity.Arrangements;
import exp.zhen.zayta.main.game.wake.movement.component.WorldWrapTag;
import exp.zhen.zayta.main.game.wake.visual.TextureComponent;
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

    private PooledEngine engine; private final Viewport viewport; private TextureAtlas wakePlayAtlas;
    private static final Logger log = new Logger(MovableBlocksSystem.class.getName(),Logger.DEBUG);

    private BiMap<Integer,Entity> movableBlocksBiMap;
    //families are entities that can collide
    private final Family UNDEADS;

//    private ArrayList<Entity> currentParents;
//    Stack<Entity> blocksToBePushed;

    public MovableBlocksSystem(PooledEngine engine, Viewport viewport, TextureAtlas wakePlayAtlas){
        this.engine = engine; this.viewport = viewport; this.wakePlayAtlas = wakePlayAtlas;
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
        Vector2[] points = Arrangements.circle(numBlocks,SizeManager.WAKE_WORLD_CENTER_X,SizeManager.WAKE_WORLD_CENTER_Y,SizeManager.WAKE_WORLD_WIDTH/3);
        for(int i =0; i<numBlocks; i++)
        {
            int key = PositionTracker.generateKey(points[i].x,points[i].y);
            Entity block = makeMovableBlock(points[i].x,points[i].y,
                    MovableTag.class,WPRegionNames.BACKGROUND);
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
            Direction direction = Mappers.MOVEMENT.get(entity).getDirection();
            Stack<Entity> blocks = getSurroundingBlocks(entity,direction);
            pushBlocksBy(entity,blocks,direction);
            exploredBlocks.clear();
        }
    }

    private void pushBlocksBy(Entity entityThatPushes, Stack<Entity> blocksToBePushed, Direction direction){
//        Direction direction = Mappers.MOVEMENT.get(entityThatPushes).getDirection();

        while(!blocksToBePushed.isEmpty()){
            Entity blockToBePushed = blocksToBePushed.pop();
//            blocksToBePushed.remove(blockToBePushed);
            Vector2 nextPos = calculateNextPos(blockToBePushed,entityThatPushes,direction);
            //todo rn the canMove method is based on position hash, and does not check whether block with that hash can move. so canMove is flawed. if switch this part with bottom part ,then the calculate next Pos will be flawed for the block in front.
            if(canMove(blockToBePushed,nextPos.x,nextPos.y)) {
                setBlockPosition(blockToBePushed, nextPos.x, nextPos.y);
            }

            Stack<Entity> blocksAroundBlockToBePushed = getSurroundingBlocks(blockToBePushed,direction);
//            blocksToBePushed.remove(blockToBePushed);
            if(!blocksAroundBlockToBePushed.isEmpty()){
                pushBlocksBy(blockToBePushed,blocksAroundBlockToBePushed,direction);
                log.debug("Block has blocks around it");
            }

        }
    }


    private ArrayList<Entity> exploredBlocks = new ArrayList<Entity>();
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
            if(blockToPush!=null&&blockToPush!=entity&&collisionBetween(entity,blockToPush)&&!exploredBlocks.contains(blockToPush)){
                blocksToCheck.push(blockToPush);
            }
        }
        exploredBlocks.addAll(blocksToCheck);
        return blocksToCheck;
    }

    private boolean canMove(Entity block, float nextPosX,float nextPosY){
        if(withinBounds(block,nextPosX,nextPosY))
        {
            int newKey = PositionTracker.generateKey(nextPosX,nextPosY);
            if(positionHashAvailable(newKey,block))
                return true;
        }
        return false;
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



    private Entity makeMovableBlock(float x, float y,java.lang.Class componentType, String regionName) {

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.setRegion(wakePlayAtlas.findRegion(regionName));

        Entity entity = engine.createEntity();

        entity.add(engine.createComponent(componentType));//adds identifier
        entity.add(texture);
        engine.addEntity(entity);


        Position position = engine.createComponent(Position.class);
        position.set(x,y);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.set(SizeManager.maxObjWidth,SizeManager.maxObjHeight);

        CircularBoundsComponent bounds = engine.createComponent(CircularBoundsComponent.class);
        bounds.setBounds(x,y-dimension.getHeight()/2,SizeManager.maxBoundsRadius);

        WorldWrapTag worldWrap = engine.createComponent(WorldWrapTag.class);

        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(worldWrap);

        return entity;
    }

}