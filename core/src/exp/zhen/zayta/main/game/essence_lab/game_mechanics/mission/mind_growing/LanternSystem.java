package exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.mind_growing;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import exp.zhen.zayta.RPG;
import exp.zhen.zayta.main.UIAssetDescriptors;
import exp.zhen.zayta.main.game.essence_lab.assets.WPRegionNames;
import exp.zhen.zayta.main.game.essence_lab.blocks.BlockComponent;
import exp.zhen.zayta.main.game.essence_lab.entity.components.properties.ColorComponent;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.GameControllingSystem;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.collision_mechanics.template_for_collision_system.CollisionListener;
import exp.zhen.zayta.main.game.essence_lab.map.MapMaker;
import exp.zhen.zayta.main.game.essence_lab.map.util.Arrangements;
import exp.zhen.zayta.main.game.essence_lab.movement.Direction;
import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.essence_lab.movement.PositionComparator;
import exp.zhen.zayta.main.game.essence_lab.movement.PositionTracker;
import exp.zhen.zayta.main.game.essence_lab.movement.component.AutoMovementTag;
import exp.zhen.zayta.main.game.essence_lab.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.Position;
import exp.zhen.zayta.main.game.essence_lab.movement.component.VelocityComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.WorldWrapComponent;
//import exp.zhen.zayta.main.game.essence_lab.movement.component.WorldWrapTag;
import exp.zhen.zayta.main.game.essence_lab.render.animation.TextureComponent;
import exp.zhen.zayta.main.game.essence_lab.render.mono_color.MonoColorRenderTag;
import exp.zhen.zayta.util.KeyListMap;
import exp.zhen.zayta.util.GdxUtils;
import jdk.nashorn.internal.ir.Block;


public class LanternSystem extends GameControllingSystem implements CollisionListener{

    //todo this does not account for how lanterns interact with blocks. It only accounts for how lanterns interact with moving entities

    private static final Logger log = new Logger(LanternSystem.class.getName(),Logger.DEBUG);
    private PooledEngine engine;
    private TextureAtlas labAtlas;

    private int numBlocks = GdxUtils.RANDOM.nextInt(10);
    private final KeyListMap<Integer,Entity> lanternsKeyListMap;
    //families are entities that can collide
    private Family MOVING_ENTITIES = Family.all(
            Position.class,
            PositionTrackerComponent.class,
            VelocityComponent.class,
            MovementLimitationComponent.class,
            RectangularBoundsComponent.class
    ).get();

    private ImmutableArray<Entity> entities;
    private PriorityQueue<Entity> entitiesToBeProcessed;
    public LanternSystem(RPG game, PooledEngine engine){
        super(game,engine);
        addMission();
        this.engine = engine;

        labAtlas = game.getAssetManager().get(UIAssetDescriptors.LAB);
        lanternsKeyListMap = new KeyListMap<Integer, Entity>();
        initBlocks();
        entities = engine.getEntitiesFor(MOVING_ENTITIES);
        entitiesToBeProcessed = new PriorityQueue<Entity>(entities.size(),new PositionComparator());
    }

    private void initBlocks(){
        Vector2[] points = Arrangements.generateRandomUCoordinates(numBlocks);
        for(int i =0; i<points.length; i++)
        {
            int key = PositionTracker.generateKey(points[i].x,points[i].y);
            lanternsKeyListMap.put(key,makeBlock(points[i].x,points[i].y, LanternTag.class,WPRegionNames.EMOTES_BLUE_NEUTRAL));//todo set new texture to be WPRegionNames.Blocks[randomInt() in bounds]

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
        VelocityComponent movement = Mappers.MOVEMENT.get(movingEntity);
        int key = Mappers.POSITION_TRACKER.get(movingEntity).getPositionKeyListMap().getKey(movingEntity);
        int keyAbove = key+PositionTracker.n;
        int keyBelow = key-PositionTracker.n;
        int [] keys = {keyAbove-1,keyAbove,keyAbove+1,
                key-1, key, key+1,
                keyBelow-1, keyBelow, keyBelow+1};
        checkCollision(movingEntity,keys);
        checkCollision(movingEntity, keys);


    }
    private void checkCollision(Entity movingEntity, int [] keys){
        for (int key: keys) {
            ArrayList<Entity> lanterns = lanternsKeyListMap.getList(key);
            if(lanterns!=null) {
                for (Entity block : lanterns) {
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
//        log.debug("Lantern playerBounds is "+playerBounds);
        RectangularBoundsComponent blockBounds = Mappers.RECTANGULAR_BOUNDS.get(block);
        //todo null point exception for Circular bounds. needa combine circ and rect into one bounds
        //log.debug("Lantern lanternBounds is "+blockBounds);
        return movingEntity!=block&& Intersector.overlaps(blockBounds.getBounds(),playerBounds.getBounds());
        //first case is to make sure doesnt collide with itself since lantern is also movingentity
    }

    public void collideEvent(Entity movingEntity, Entity block) {
        Direction blockDirection = Mappers.MOVEMENT.get(block).getDirection();//need this since movement is set to none in the later method (lantern is also part of moving entity)
        log.debug("lanternDirection before is "+blockDirection);
//        blockEntity(movingEntity);

        VelocityComponent movement = Mappers.MOVEMENT.get(movingEntity);
        VelocityComponent blockMovement = Mappers.MOVEMENT.get(block);




        Direction changedD = blockDirection.getOpposite();
        blockMovement.setDirection(changedD);
        entitiesToBeProcessed.remove(block);

        movement.setDirection(Direction.none);
        log.debug("lanternDirection after is "+blockMovement.getDirection());
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
        entity.add(position);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.set(SizeManager.maxObjWidth,SizeManager.maxObjHeight);
        entity.add(dimension);

        RectangularBoundsComponent bounds = engine.createComponent(RectangularBoundsComponent.class);
        bounds.setBounds(x-dimension.getWidth()/2,y-dimension.getHeight()/2,dimension.getWidth(),dimension.getHeight());
        entity.add(bounds);

        WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class);
        worldWrap.setBoundsOfMovement(MapMaker.getMapBounds());
        entity.add(worldWrap);

        BlockComponent blockComponent = engine.createComponent(BlockComponent.class);
        entity.add(blockComponent);

        VelocityComponent velocityComponent = engine.createComponent(VelocityComponent.class);
        entity.add(velocityComponent);

        AutoMovementTag autoMovementTag = engine.createComponent(AutoMovementTag.class);
        entity.add(autoMovementTag);

        MovementLimitationComponent movementLimitationComponent = engine.createComponent(MovementLimitationComponent.class);
        entity.add(movementLimitationComponent);

        PositionTrackerComponent positionTrackerComponent = engine.createComponent(PositionTrackerComponent.class);
        positionTrackerComponent.setPositionKeyListMap(lanternsKeyListMap);
        entity.add(positionTrackerComponent);
        
        
        //color

        MonoColorRenderTag monoColorRenderTag = engine.createComponent(MonoColorRenderTag.class);
        entity.add(monoColorRenderTag);

        ColorComponent colorComponent = engine.createComponent(ColorComponent.class);
        colorComponent.setColor(Color.WHITE);
        entity.add(colorComponent);

        return entity;
    }

    public KeyListMap<Integer, Entity> getBlocksKeyListMap() {
        return lanternsKeyListMap;
    }


    @Override
    public void reset() {

    }
}
