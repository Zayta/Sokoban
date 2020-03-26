package exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.blocks;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

import exp.zhen.zayta.main.Game;
import exp.zhen.zayta.main.assets.AssetDescriptors;
import exp.zhen.zayta.main.assets.RegionNames;
import exp.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.ColorComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.MapMaker;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.util.Arrangements;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionComparator;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionTracker;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.AutoMovementTag;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.DimensionComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.WorldWrapComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.TextureComponent;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.mono_color.MonoColorRenderTag;
import exp.zhen.zayta.versions_unused.game_mechanics.collision_mechanics.template_for_collision_system.CollisionListener;
//import exp.zhen.zayta.main.game.experiment.engine.movement.component.WorldWrapTag;
import exp.zhen.zayta.util.KeyListMap;
import exp.zhen.zayta.util.GdxUtils;

import static exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction.none;
import static exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction.up;


public class MovingBlocksSystem extends EntitySystem implements CollisionListener{

    //todo this does not account for how blocks interact with blocks. It only accounts for how blocks interact with moving entities

    private static final Logger log = new Logger(MovingBlocksSystem.class.getName(),Logger.DEBUG);
    private PooledEngine engine;
    private TextureAtlas labAtlas;

    private int numBlocks = GdxUtils.RANDOM.nextInt(10);
    private final KeyListMap<Integer,Entity> blocksKeyListMap;
    //families are entities that can collide
    private Family MOVING_ENTITIES = Family.all(
            exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position.class,
            exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.PositionTrackerComponent.class,
            exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent.class,
            exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent.class,
            exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent.class
    ).exclude(MovingBlockTag.class).get();

    private ImmutableArray<Entity> entities;
    private PriorityQueue<Entity> entitiesToBeProcessed;
    public MovingBlocksSystem(Game game, PooledEngine engine){
//        super(game,engine);
//        addMission();
        this.engine = engine;

        labAtlas = game.getAssetManager().get(AssetDescriptors.LAB);
        blocksKeyListMap = new KeyListMap<Integer, Entity>();
        initBlocks();
        entities = engine.getEntitiesFor(MOVING_ENTITIES);
        entitiesToBeProcessed = new PriorityQueue<Entity>(entities.size(),new PositionComparator());
    }

    private void initBlocks(){
        Vector2[] points = Arrangements.generateRandomUCoordinates(numBlocks);
        for(int i =0; i<points.length; i++)
        {
            int key = exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionTracker.generateKey(points[i].x,points[i].y);
            blocksKeyListMap.put(key,makeBlock(points[i].x,points[i].y, MovingBlockTag.class,RegionNames.EMOTES_NEUTRAL));//todo set new texture to be RegionNames.Blocks[randomInt() in bounds]

        }
    }
    @Override
    public void update(float deltaTime) {
//        Collections.sort(entities, new PositionComparator());
        entitiesToBeProcessed.addAll(Arrays.asList(entities.toArray()));

        while(!entitiesToBeProcessed.isEmpty()){
            processEntity(entitiesToBeProcessed.poll(),deltaTime);
        }

    }

    private void processEntity(Entity movingEntity,float deltaTime) {
        //todo need to process entity in order of direction.
        int key = exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.POSITION_TRACKER.get(movingEntity).getPositionKeyListMap().getKey(movingEntity);
        int keyAbove = key+ exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionTracker.n;
        int keyBelow = key- PositionTracker.n;
        int [] keys = {keyAbove-1,keyAbove,keyAbove+1,
                key-1, key, key+1,
                keyBelow-1, keyBelow, keyBelow+1};
        checkCollision(movingEntity,keys);
        checkCollision(movingEntity, keys);


    }
    private void checkCollision(Entity movingEntity, int [] keys){
        for (int key: keys) {
            ArrayList<Entity> blocks = blocksKeyListMap.getList(key);
            if(blocks!=null) {
                for (Entity block : blocks) {
                    exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent movementLimitationComponent =
                            exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT_LIMITATION.get(movingEntity);
                    if (checkCollisionBetween(movingEntity, block)) {

                        movementLimitationComponent.setBlock(block,
                                exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT.get(movingEntity).getDirection()
                        );
                        collideEvent(movingEntity, block);
                    }
                }
            }

        }
    }
    private boolean checkCollisionBetween(Entity movingEntity, Entity block)
    {
        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent playerBounds = exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.RECTANGULAR_BOUNDS.get(movingEntity);
//        //log.debug("Block playerBounds is "+playerBounds);
        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent blockBounds = exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.RECTANGULAR_BOUNDS.get(block);
        //todo null point exception for Circular bounds. needa combine circ and rect into one bounds
        ////log.debug("Block blockBounds is "+blockBounds);
        return movingEntity!=block&& overlaps(blockBounds.getBounds(),playerBounds.getBounds(),
                exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT.get(block).getDirection());
        //first case is to make sure doesnt collide with itself since block is also movingentity
    }
    public static boolean overlaps (Rectangle r1, Rectangle r2, exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction direction) {
        float threshold = 0.8f;
        if(direction== up||direction==Direction.down)
        {
            return r1.x < r2.x + threshold*r2.width && r1.x + threshold*r1.width > r2.x && r1.y < r2.y + r2.height && r1.y + r1.height > r2.y;
        }
        return r1.x < r2.x + r2.width && r1.x + r1.width > r2.x && r1.y < r2.y + threshold*r2.height && r1.y + threshold*r1.height > r2.y;


//        return r1.x < r2.x + threshold*r2.width && r1.x + threshold*r1.width > r2.x && r1.y < r2.y + threshold*r2.height && r1.y + threshold*r1.height > r2.y;
    }

    public void collideEvent(Entity movingEntity, Entity block) {
//        blockEntity(movingEntity);



//        VelocityComponent movement = Mappers.MOVEMENT.get(movingEntity);
//        movement.setDirection(Direction.none);

        blockEntity(block,movingEntity);
        blockEntity(movingEntity,block);
//        //log.debug("blockDirection after is "+blockMovement.getDirection());
        entitiesToBeProcessed.remove(block);
    }
    private void blockEntity(Entity movingEntity, Entity block){

        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position position = exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.POSITION.get(movingEntity);
        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent blockBounds = exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.RECTANGULAR_BOUNDS.get(block);
        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent movement = exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT.get(movingEntity);

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
        Mappers.MOVEMENT_LIMITATION.get(movingEntity).setBlock(block,movement.getDirection()); //sb before movement changes to none

        movement.setDirection(none);

    }



    private Entity makeBlock(float x, float y,java.lang.Class componentType, String regionName) {
        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.setRegion(labAtlas.findRegion(regionName));

        Entity entity = engine.createEntity();

        entity.add(engine.createComponent(componentType));//adds identifier
        entity.add(texture);
        engine.addEntity(entity);





        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position position = engine.createComponent(Position.class);
        position.set(x,y);
        entity.add(position);

        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.set(exp.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager.maxObjWidth, SizeManager.maxObjHeight);
        entity.add(dimension);

        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent bounds = engine.createComponent(RectangularBoundsComponent.class);
        bounds.setBounds(x-dimension.getWidth()/2,y-dimension.getHeight()/2,dimension.getWidth(),dimension.getHeight());
        entity.add(bounds);

        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class);
        worldWrap.setBoundsOfMovement(MapMaker.getMapBounds());
        entity.add(worldWrap);

        BlockComponent blockComponent = engine.createComponent(BlockComponent.class);
        entity.add(blockComponent);

        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent velocityComponent = engine.createComponent(VelocityComponent.class);
        velocityComponent.setDirection(Direction.generateRandomDirection());
        entity.add(velocityComponent);

        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.AutoMovementTag autoMovementTag = engine.createComponent(AutoMovementTag.class);
        entity.add(autoMovementTag);

        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent movementLimitationComponent = engine.createComponent(MovementLimitationComponent.class);
        entity.add(movementLimitationComponent);

        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.PositionTrackerComponent positionTrackerComponent = engine.createComponent(PositionTrackerComponent.class);
        positionTrackerComponent.setPositionKeyListMap(blocksKeyListMap);
        entity.add(positionTrackerComponent);
        
        
        //color

        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.mono_color.MonoColorRenderTag monoColorRenderTag = engine.createComponent(MonoColorRenderTag.class);
        entity.add(monoColorRenderTag);

        exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.ColorComponent colorComponent = engine.createComponent(ColorComponent.class);
        colorComponent.setColor(Color.WHITE);
        entity.add(colorComponent);

        return entity;
    }

    public KeyListMap<Integer, Entity> getBlocksKeyListMap() {
        return blocksKeyListMap;
    }

}
