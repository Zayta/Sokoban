package exp.zhen.zayta.main.game.wake.map.blocks;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.wake.assets.WPRegionNames;
import exp.zhen.zayta.main.game.wake.game_mechanics.collision_mechanics.template_for_collision_system.CollisionListener;
import exp.zhen.zayta.main.game.wake.map.MapMaker;
import exp.zhen.zayta.main.game.wake.movement.Direction;
import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.wake.movement.PositionTracker;
import exp.zhen.zayta.main.game.wake.entity.util.Arrangements;
import exp.zhen.zayta.main.game.wake.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.main.game.wake.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.wake.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.wake.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.wake.movement.component.Position;
import exp.zhen.zayta.main.game.wake.movement.component.VelocityComponent;
import exp.zhen.zayta.main.game.wake.movement.component.WorldWrapTag;
import exp.zhen.zayta.main.game.wake.render.animation.TextureComponent;
import exp.zhen.zayta.util.BiMap;
import exp.zhen.zayta.util.GdxUtils;


public class BlockSystem extends EntitySystem implements CollisionListener{

    //todo later add in wielder x mortal in this same class and rename class to undead x mortal collision system
    private static final Logger log = new Logger(BlockSystem.class.getName(),Logger.DEBUG);
    private PooledEngine engine;
    private TextureAtlas wakePlayAtlas;

    private int numBlocks = GdxUtils.RANDOM.nextInt(10);
    private BiMap<Integer,Entity> blocksBiMap;
    //families are entities that can collide
    private Family MOVING_ENTITIES;
    //todo add "add or remove" block feature

    public BlockSystem(PooledEngine engine, TextureAtlas wakePlayAtlas){
//        super();
        this.engine = engine;
        this.wakePlayAtlas = wakePlayAtlas;
        MOVING_ENTITIES = Family.all(
                Position.class,
                PositionTrackerComponent.class,
                VelocityComponent.class,
                MovementLimitationComponent.class,
                RectangularBoundsComponent.class
        ).get();
        blocksBiMap = new BiMap<Integer, Entity>();
        initBlocks();
    }

    private void initBlocks(){
        Vector2[] points = Arrangements.circle(numBlocks,SizeManager.WAKE_WORLD_CENTER_X,SizeManager.WAKE_WORLD_CENTER_Y,SizeManager.WAKE_WORLD_WIDTH/3);
        for(int i =0; i<numBlocks; i++)
        {
            int key = PositionTracker.generateKey(points[i].x,points[i].y);
            blocksBiMap.put(key,makeBlock(points[i].x,points[i].y, BlockComponent.class,WPRegionNames.BACKGROUND));//todo set new texture to be WPRegionNames.Blocks[randomInt() in bounds]

        }
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> movingEntities = getEngine().getEntitiesFor(MOVING_ENTITIES);

        for(Entity movingEntity: movingEntities) {

            VelocityComponent movement = Mappers.MOVEMENT.get(movingEntity);
            Direction direction = movement.getDirection();
//
//            int[] keys = new int[6];

            int key = Mappers.POSITION_TRACKER.get(movingEntity).getPositionBiMap().getKey(movingEntity);
            int keyAbove = key + PositionTracker.n;
            int keyBelow = key - PositionTracker.n;
            int [] keys = new int []{
              key,key-1,key+1,keyAbove-1,keyAbove,keyAbove+1,keyBelow-1,keyBelow,keyBelow+1
            };
//            switch (direction) {
//                case none:
//                    Entity block = blocksBiMap.get(key);
//                    if (block != null)
//                        checkCollisionBetween(movingEntity, block);
//                    continue;
//                case up:
//                    keys[0] = keyAbove;
//                    keys[1] = keyAbove + 1;
//                    keys[2] = keyAbove - 1;
//                    keys[3] = key - 1;
//                    keys[4] = key + 1;
//                    break;
//                case down:
//                    keys[0] = keyBelow;
//                    keys[1] = keyBelow + 1;
//                    keys[2] = keyBelow - 1;
//                    keys[3] = key - 1;
//                    keys[4] = key + 1;
//                    break;
//                case left:
//                    keys[0] = keyAbove - 1;
//                    keys[1] = key - 1;
//                    keys[2] = keyBelow - 1;
//                    keys[3] = keyAbove;
//                    keys[4] = keyBelow;
//                    break;
//                case right:
//                    keys[0] = keyAbove + 1;
//                    keys[1] = key + 1;
//                    keys[2] = keyBelow + 1;
//                    keys[3] = keyAbove;
//                    keys[4] = keyBelow;
//                    break;
//            }
//            keys[5] = key;
            checkCollision(movingEntity, keys);

        }
    }
    private void checkCollision(Entity movingEntity, int [] keys){
        for (int key: keys) {
            Entity block = blocksBiMap.get(key);

            MovementLimitationComponent movementLimitationComponent =
                    Mappers.MOVEMENT_LIMITATION.get(movingEntity);
            if(movementLimitationComponent.getBlockedDirection()!=Direction.none)
                movementLimitationComponent.setBlockedDirection(Direction.none);
            if (block != null) {
                if (checkCollisionBetween(movingEntity, block)) {

                    movementLimitationComponent.setBlockedDirection(
                            Mappers.MOVEMENT.get(movingEntity).getDirection()
                    );
                    collideEvent(movingEntity, block);
                }
            }
        }
    }
    private boolean checkCollisionBetween(Entity movingEntity, Entity block)
    {
        RectangularBoundsComponent playerBounds = Mappers.RECTANGULAR_BOUNDS.get(movingEntity);
        RectangularBoundsComponent blockBounds = Mappers.RECTANGULAR_BOUNDS.get(block);

        return Intersector.overlaps(blockBounds.getBounds(),playerBounds.getBounds());
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

    public void collideEvent(Entity movingEntity, Entity block) {
        blockEntity(movingEntity,block);
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



    private Entity makeBlock(float x, float y,java.lang.Class componentType, String regionName) {
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

        RectangularBoundsComponent bounds = engine.createComponent(RectangularBoundsComponent.class);
        bounds.setBounds(x-dimension.getWidth()/2,y-dimension.getHeight()/2,dimension.getWidth(),dimension.getHeight());

        WorldWrapTag worldWrap = engine.createComponent(WorldWrapTag.class);

        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(worldWrap);

        return entity;
    }


}
