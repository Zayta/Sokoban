package snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.blocks;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import snow.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionTracker;
import snow.zhen.zayta.versions_unused.game_mechanics.collision_mechanics.template_for_collision_system.CollisionListener;
//import snow.zhen.zayta.main.game.experiment.engine.movement.component.WorldWrapTag;
import snow.zhen.zayta.main.assets.RegionNames;
import snow.zhen.zayta.util.GdxUtils;
import snow.zhen.zayta.util.KeyListMap;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.MapMaker;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.util.Arrangements;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionComparator;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.DimensionComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.PositionTrackerComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.WorldWrapComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.TextureComponent;


public class BlockSystem extends IteratingSystem implements CollisionListener{

    //todo later add in wielder x mortal in this same class and rename class to undead x mortal collision system
    private static final Logger log = new Logger(BlockSystem.class.getName(),Logger.DEBUG);
    private PooledEngine engine;
    private TextureAtlas labAtlas;

    private int numBlocks = snow.zhen.zayta.util.GdxUtils.RANDOM.nextInt(10);
    private final snow.zhen.zayta.util.KeyListMap<Integer,Entity> blocksKeyListMap;
    //families are entities that can collide
    private static Family MOVING_ENTITIES = Family.all(
            snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position.class,
            snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.PositionTrackerComponent.class,
            snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent.class,
            snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent.class,
            snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent.class
    ).get();

    public BlockSystem(PooledEngine engine, TextureAtlas labAtlas){
        super(MOVING_ENTITIES);
        this.engine = engine;
        this.labAtlas = labAtlas;
        blocksKeyListMap = new snow.zhen.zayta.util.KeyListMap<Integer, Entity>();
//        blocksKeyListMap = PositionTracker.globalTracker;
        initBlocks(RegionNames.BLOCKS_HOUSES);

    }

    private void initBlocks(String [] regionNames){
        Vector2[] points = Arrangements.generateRandomUCoordinates(numBlocks);
        for(int i =0; i<points.length; i++)
        {
            int key = PositionTracker.generateKey(points[i].x,points[i].y);
            blocksKeyListMap.put(key,makeBlock(points[i].x,points[i].y, BlockComponent.class,regionNames[GdxUtils.RANDOM.nextInt(regionNames.length)]));//todo set new texture to be RegionNames.Blocks[randomInt() in bounds]

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
            snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent movement = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT.get(movingEntity);
            snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction direction = movement.getDirection();
//
            int[] keys = new int[6];

            int key = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.POSITION_TRACKER.get(movingEntity).getPositionKeyListMap().getKey(movingEntity);
            int keyAbove = key + PositionTracker.n;
            int keyBelow = key - snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionTracker.n;
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
                    if (checkCollisionBetween(movingEntity, block)) {

                        collideEvent(movingEntity, block);
                    }
                }
            }

        }
    }
    private boolean checkCollisionBetween(Entity movingEntity, Entity block)
    {
        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent playerBounds = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.RECTANGULAR_BOUNDS.get(movingEntity);
        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent blockBounds = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.RECTANGULAR_BOUNDS.get(block);
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
        MovementLimitationComponent movementLimitationComponent =
                snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT_LIMITATION.get(movingEntity);

        movementLimitationComponent.setBlock(block,
                snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT.get(movingEntity).getDirection()
        );

        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position position = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.POSITION.get(movingEntity);
        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent blockBounds = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.RECTANGULAR_BOUNDS.get(block);
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
            default:
                throw new IllegalStateException("Unexpected value: " + movement.getDirection());
        }
        movement.setDirection(Direction.none);
    }



    private Entity makeBlock(float x, float y,java.lang.Class componentType, String regionName) {
        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.setRegion(labAtlas.findRegion(regionName));

        Entity entity = engine.createEntity();

        entity.add(engine.createComponent(componentType));//adds identifier
        entity.add(texture);
        engine.addEntity(entity);


        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position position = engine.createComponent(Position.class);
        position.set(x,y);

        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.set(SizeManager.maxObjWidth, snow.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager.maxObjHeight);

        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent bounds = engine.createComponent(RectangularBoundsComponent.class);
        bounds.setBounds(x-dimension.getWidth()/2,y-dimension.getHeight()/2,dimension.getWidth(),dimension.getHeight());

        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class);
        worldWrap.setBoundsOfMovement(MapMaker.getMapBounds());
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(worldWrap);

        //todo remove later: for DEBUG only
        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.PositionTrackerComponent positionTrackerComponent = engine.createComponent(PositionTrackerComponent.class);
        positionTrackerComponent.setPositionKeyListMap(blocksKeyListMap);
        entity.add(positionTrackerComponent);


        return entity;
    }

    public KeyListMap<Integer, Entity> getBlocksKeyListMap() {
        return blocksKeyListMap;
    }






}
