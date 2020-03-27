package snow.zhen.zayta.versions_unused.arcade_style_game.movable_items;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import snow.zhen.zayta.main.assets.RegionNames;
import snow.zhen.zayta.versions_unused.arcade_style_game.movable_items.components.MovableTag;
import snow.zhen.zayta.versions_unused.arcade_style_game.movable_items.components.NonAutoMotionComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.movable_items.components.PushComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.movable_items.locker.LockerKeyTag;
import snow.zhen.zayta.util.KeyListMap;
import snow.zhen.zayta.util.GdxUtils;
import snow.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionTracker;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.ColorComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.MapMaker;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.util.Arrangements;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.DimensionComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.PositionTrackerComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.WorldWrapComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.TextureComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.mono_color.MonoColorRenderTag;

public class PickUpMovableItem extends EntitySystem  {

    private PooledEngine engine; private final Viewport viewport;private TextureAtlas labAtlas;
    private static final Logger log = new Logger(PickUpMovableItem.class.getName(),Logger.DEBUG);

    private KeyListMap<Integer,Entity> movableBlocksKeyListMap;
    //families are entities that can collide
    private final Family ENTITIES;

    private int numBlocks = GdxUtils.RANDOM.nextInt(10)+3;

    public PickUpMovableItem(PooledEngine engine, Viewport viewport, TextureAtlas labAtlas){
        this.engine = engine; this.viewport = viewport; this.labAtlas = labAtlas;
        ENTITIES = Family.all(
                snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.PositionTrackerComponent.class,
                snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent.class,
                snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent.class,
                PushComponent.class
        ).get();
        movableBlocksKeyListMap = new KeyListMap<Integer, Entity>();
        initMovableBlocks();
    }

    private void initMovableBlocks(){
        Vector2[] points = Arrangements.generateRandomUCoordinates(numBlocks);
        for(int i =0; i<points.length; i++)
        {
            int key = PositionTracker.generateKey(points[i].x,points[i].y);
            Entity block = makeMovableBlock(points[i].x,points[i].y,
                    MovableTag.class,RegionNames.EMOTES_COOL);
            movableBlocksKeyListMap.put(key, block);
        }
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> nighters = getEngine().getEntitiesFor(ENTITIES);

        for(Entity entity: nighters) {
            int key = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.POSITION_TRACKER.get(entity).getPositionKeyListMap().getKey(entity);
            int keyAbove = key+ PositionTracker.n;
            int keyBelow = key- snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionTracker.n;
            int [] keys = {keyAbove-1,keyAbove,keyAbove+1,
                    key-1, key, key+1,
                    keyBelow-1, keyBelow, keyBelow+1};
            checkCollision(entity,keys);
        }
    }

    private void checkCollision(Entity entity, int [] keys){
        for (int key: keys) {
            ArrayList<Entity> movable_items = movableBlocksKeyListMap.getList(key);
            if(movable_items!=null) {
                for(Entity movable_item:movable_items) {
                    if (movable_item != null) {
                        if (movable_item != entity && checkCollisionBetween(entity, movable_item)) {//first condition is to make sure if entity is a block, the block that can push is not itself
                            collideEvent(entity, movable_item);
                        }
                    }
                }
            }
        }
    }
    private boolean checkCollisionBetween(Entity undead, Entity obstacle)
    {
        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent playerBounds = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.RECTANGULAR_BOUNDS.get(undead);
        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent obstacleBounds = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.RECTANGULAR_BOUNDS.get(obstacle);

        return Intersector.overlaps(playerBounds.getBounds(),obstacleBounds.getBounds());
    }

    private void collideEvent(Entity entity, Entity movable_item) {
        //implement what happens during collision
        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction entityDirection = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT.get(entity).getDirection();
        PushComponent pocketComponent = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.POCKET.get(entity);
        if(!pocketComponent.getCarriedItems().contains(movable_item)
                &&entityDirection!= Direction.none){//if pocket does not already hold tht item

            PushComponent itemPocket = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.POCKET.get(movable_item);
            if(itemPocket!=null && !itemPocket.getCarriedItems().contains(entity)) {//if the item to be held does not contain this item
                pocketComponent.add(movable_item);
                Mappers.ITEM_SHOVE.get(movable_item).setDirection(entityDirection);
            }
        }
    }

    public void reset() {
        movableBlocksKeyListMap.clear();
    }

    private Entity makeMovableBlock(float x, float y,java.lang.Class componentType, String regionName) {

        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.setRegion(labAtlas.findRegion(regionName));

        Entity entity = engine.createEntity();

        entity.add(engine.createComponent(componentType));//adds identifier
        entity.add(texture);
        engine.addEntity(entity);


        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position position = engine.createComponent(Position.class);
        position.set(x,y);
        entity.add(position);

        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.set(SizeManager.maxObjWidth, snow.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager.maxObjHeight);
        entity.add(dimension);

        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent bounds = engine.createComponent(RectangularBoundsComponent.class);
        bounds.setBounds(x,y,dimension.getWidth(),dimension.getHeight());
        entity.add(bounds);

        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class); worldWrap.setBoundsOfMovement(MapMaker.getMapBounds());
        entity.add(worldWrap);

        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.PositionTrackerComponent positionTrackerComponent = engine.createComponent(PositionTrackerComponent.class);
        positionTrackerComponent.setPositionKeyListMap(movableBlocksKeyListMap);
        entity.add(positionTrackerComponent);


        PushComponent pocketComponent = engine.createComponent(PushComponent.class);
        entity.add(pocketComponent);//todo this makes everything laggy

        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent movement = engine.createComponent(VelocityComponent.class);
        entity.add(movement);

        NonAutoMotionComponent nonAutoMotionComponent = engine.createComponent(NonAutoMotionComponent.class);
        entity.add(nonAutoMotionComponent);

        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent movementLimitationComponent = engine.createComponent(MovementLimitationComponent.class);
        entity.add(movementLimitationComponent);



        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.mono_color.MonoColorRenderTag monoColorRenderTag = engine.createComponent(MonoColorRenderTag.class);
        entity.add(monoColorRenderTag);

        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.properties.ColorComponent colorComponent = engine.createComponent(ColorComponent.class);
        colorComponent.setColor(Color.CYAN);
        entity.add(colorComponent);

        LockerKeyTag lockerKeyTag = engine.createComponent(LockerKeyTag.class);
        entity.add(lockerKeyTag);

        return entity;
    }

}