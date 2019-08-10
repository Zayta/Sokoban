package exp.zhen.zayta.main.game.essence_lab.blocks;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import exp.zhen.zayta.main.game.essence_lab.assets.WPRegionNames;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.collision_mechanics.template_for_collision_system.CollisionListener;
import exp.zhen.zayta.main.game.essence_lab.map.MapMaker;
import exp.zhen.zayta.main.game.essence_lab.map.util.Arrangements;
import exp.zhen.zayta.main.game.essence_lab.movement.Direction;
import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.essence_lab.movement.PositionComparator;
import exp.zhen.zayta.main.game.essence_lab.movement.PositionTracker;
import exp.zhen.zayta.main.game.essence_lab.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.Position;
import exp.zhen.zayta.main.game.essence_lab.movement.component.VelocityComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.WorldWrapComponent;
//import exp.zhen.zayta.main.game.essence_lab.movement.component.WorldWrapTag;
import exp.zhen.zayta.main.game.essence_lab.render.animation.TextureComponent;
import exp.zhen.zayta.util.KeyListMap;
import exp.zhen.zayta.util.GdxUtils;


public abstract class BlockSystem extends IteratingSystem implements CollisionListener{

    //todo later add in wielder x mortal in this same class and rename class to undead x mortal collision system
    private static final Logger log = new Logger(BlockSystem.class.getName(),Logger.DEBUG);
    private PooledEngine engine;
    private TextureAtlas labAtlas;

    private int numBlocks = GdxUtils.RANDOM.nextInt(10);
    private final KeyListMap<Integer,Entity> blocksKeyListMap;
    //families are entities that can collide
    private static Family MOVING_ENTITIES = Family.all(
            Position.class,
            PositionTrackerComponent.class,
            VelocityComponent.class,
            MovementLimitationComponent.class,
            RectangularBoundsComponent.class
    ).get();

    public BlockSystem(PooledEngine engine, TextureAtlas labAtlas){
        super(MOVING_ENTITIES);
        this.engine = engine;
        this.labAtlas = labAtlas;
        blocksKeyListMap = new KeyListMap<Integer, Entity>();
//        blocksKeyListMap = PositionTracker.globalTracker;
        initBlocks();

    }

    private void initBlocks(){
        Vector2[] points = Arrangements.generateRandomUCoordinates(numBlocks);
        for(int i =0; i<points.length; i++)
        {
            int key = PositionTracker.generateKey(points[i].x,points[i].y);
            blocksKeyListMap.put(key,makeBlock(points[i].x,points[i].y, BlockComponent.class,WPRegionNames.BACKGROUND));//todo set new texture to be WPRegionNames.Blocks[randomInt() in bounds]

        }
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
    public void processEntity(Entity movingEntity,float deltaTime) {
        //todo need to process entity in order of direction.
            VelocityComponent movement = Mappers.MOVEMENT.get(movingEntity);
            Direction direction = movement.getDirection();
//
            int[] keys = new int[6];

            int key = Mappers.POSITION_TRACKER.get(movingEntity).getPositionKeyListMap().getKey(movingEntity);
            int keyAbove = key + PositionTracker.n;
            int keyBelow = key - PositionTracker.n;
//            int [] keys = new int []{
//              key,key-1,key+1,keyAbove-1,keyAbove,keyAbove+1,keyBelow-1,keyBelow,keyBelow+1
//            };
            switch (direction) {
                case none:
                    Entity block = blocksKeyListMap.get(key);
                    if (block != null)
                        checkCollisionBetween(movingEntity, block);
                    break;
                case up:
                    keys[0] = keyAbove;
                    keys[1] = keyAbove + 1;
                    keys[2] = keyAbove - 1;
                    keys[3] = key - 1;
                    keys[4] = key + 1;
                    break;
                case down:
                    keys[0] = keyBelow;
                    keys[1] = keyBelow + 1;
                    keys[2] = keyBelow - 1;
                    keys[3] = key - 1;
                    keys[4] = key + 1;
                    break;
                case left:
                    keys[0] = keyAbove - 1;
                    keys[1] = key - 1;
                    keys[2] = keyBelow - 1;
                    keys[3] = keyAbove;
                    keys[4] = keyBelow;
                    break;
                case right:
                    keys[0] = keyAbove + 1;
                    keys[1] = key + 1;
                    keys[2] = keyBelow + 1;
                    keys[3] = keyAbove;
                    keys[4] = keyBelow;
                    break;
            }
            keys[5] = key;
            checkCollision(movingEntity, keys);


    }
    private void checkCollision(Entity movingEntity, int [] keys){
        for (int key: keys) {
            ArrayList<Entity> blocks = blocksKeyListMap.getList(key);
            if(blocks!=null) {
                for (Entity block : blocks) {
                    MovementLimitationComponent movementLimitationComponent =
                            Mappers.MOVEMENT_LIMITATION.get(movingEntity);
                    if (checkCollisionBetween(movingEntity, block)) {

                        movementLimitationComponent.setBlock(block,
                                Mappers.MOVEMENT.get(movingEntity).getDirection()
                        );
                        collideEvent(movingEntity, block);
                    }
                }
            }

        }
    }
    private boolean checkCollisionBetween(Entity movingEntity, Entity block)
    {
        RectangularBoundsComponent playerBounds = Mappers.RECTANGULAR_BOUNDS.get(movingEntity);
        RectangularBoundsComponent blockBounds = Mappers.RECTANGULAR_BOUNDS.get(block);
        if(blockBounds==null)return false;
        //todo null point exception for Circular bounds. needa combine circ and rect into one bounds
        return overlaps(blockBounds.getBounds(),playerBounds.getBounds());
    }

    public static boolean overlaps (Rectangle r1, Rectangle r2) {
        float threshold = 0.9f;
        return r1.x < r2.x + threshold*r2.width && r1.x + threshold*r1.width > r2.x && r1.y < r2.y + threshold*r2.height && r1.y + threshold*r1.height > r2.y;
    }

    public void collideEvent(Entity movingEntity, Entity block) {
        if(movingEntity!=block) {
//            Mappers.MOVEMENT.get(movingEntity).setDirection(Direction.none);
            blockEntity(movingEntity, block);
        }


//        VelocityComponent movement = Mappers.MOVEMENT.get(movingEntity);
//        MovementLimitationComponent movementLimitation = Mappers.MOVEMENT_LIMITATION.get(movingEntity);
//        if(movement.getDirection()==movementLimitation.getBlockedDirection()) {
//            blockEntity(movingEntity,block);
////            movement.setDirection(Direction.none);
//        }
//        else {
//
//            movementLimitation.setBlockedDirection(movement.getDirection());
//        }

    }

    private void blockEntity(Entity movingEntity, Entity block){
        Position position = Mappers.POSITION.get(movingEntity);
        RectangularBoundsComponent blockBounds = Mappers.RECTANGULAR_BOUNDS.get(block);
        VelocityComponent movement = Mappers.MOVEMENT.get(movingEntity);

        switch (movement.getDirection()){
            case up:
                position.setY(blockBounds.getBottom());
                break;
            case down://down and left are working ok
                position.setY(blockBounds.getTop());
                break;
            case left:
                position.setX(blockBounds.getRight());
                break;
            case right:
                position.setX(blockBounds.getLeft());
                break;
            case none:
                break;
        }
        movement.setDirection(Direction.none);
    }



    private Entity makeBlock(float x, float y,java.lang.Class componentType, String regionName) {
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.setRegion(labAtlas.findRegion(regionName));

        Entity entity = engine.createEntity();

        entity.add(engine.createComponent(componentType));//adds identifier
        entity.add(texture);
        engine.addEntity(entity);


        Position position = engine.createComponent(Position.class);
        position.set(x,y);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.set(SizeManager.maxObjWidth,SizeManager.maxObjHeight);

        RectangularBoundsComponent bounds = engine.createComponent(RectangularBoundsComponent.class);
        bounds.setBounds(x-dimension.getWidth()/2,y-dimension.getHeight()/2,dimension.getWidth(),dimension.getHeight());

        WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class);
        worldWrap.setBoundsOfMovement(MapMaker.getMapBounds());
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(worldWrap);

        //todo remove later: for DEBUG only
        PositionTrackerComponent positionTrackerComponent = engine.createComponent(PositionTrackerComponent.class);
        positionTrackerComponent.setPositionKeyListMap(blocksKeyListMap);
        entity.add(positionTrackerComponent);


        return entity;
    }

    public KeyListMap<Integer, Entity> getBlocksKeyListMap() {
        return blocksKeyListMap;
    }






}
