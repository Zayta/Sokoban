package exp.zhen.zayta.main.game.personality_engineering_lab.game_mechanics.mission.movable_items;

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

import exp.zhen.zayta.main.game.personality_engineering_lab.assets.WPRegionNames;
import exp.zhen.zayta.main.game.personality_engineering_lab.game_mechanics.mission.movable_items.components.MovableTag;
import exp.zhen.zayta.main.game.personality_engineering_lab.game_mechanics.mission.movable_items.components.NonAutoMotionComponent;
import exp.zhen.zayta.main.game.personality_engineering_lab.game_mechanics.mission.movable_items.components.PushComponent;
import exp.zhen.zayta.main.game.personality_engineering_lab.map.MapMaker;
import exp.zhen.zayta.main.game.personality_engineering_lab.movement.Direction;
import exp.zhen.zayta.main.game.personality_engineering_lab.common.Mappers;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.personality_engineering_lab.movement.PositionTracker;
import exp.zhen.zayta.main.game.personality_engineering_lab.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.main.game.personality_engineering_lab.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.personality_engineering_lab.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.personality_engineering_lab.movement.component.Position;
import exp.zhen.zayta.main.game.personality_engineering_lab.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.personality_engineering_lab.movement.component.VelocityComponent;
import exp.zhen.zayta.main.game.personality_engineering_lab.movement.component.WorldWrapTag;
import exp.zhen.zayta.main.game.personality_engineering_lab.render.animation.TextureComponent;
import exp.zhen.zayta.main.game.personality_engineering_lab.render.mono_color.MonoColorComponent;
import exp.zhen.zayta.util.BiMap;
import exp.zhen.zayta.util.GdxUtils;

public class PickUpMovableItem extends EntitySystem  {

    private PooledEngine engine; private final Viewport viewport;private TextureAtlas labAtlas;
    private static final Logger log = new Logger(PickUpMovableItem.class.getName(),Logger.DEBUG);

    private BiMap<Integer,Entity> movableBlocksBiMap;
    //families are entities that can collide
    private final Family ENTITIES;

    private int numBlocks = GdxUtils.RANDOM.nextInt(10)+3;

    public PickUpMovableItem(PooledEngine engine, Viewport viewport, TextureAtlas labAtlas){
        this.engine = engine; this.viewport = viewport; this.labAtlas = labAtlas;
        ENTITIES = Family.all(
                PositionTrackerComponent.class,
                Position.class,
                VelocityComponent.class,
                RectangularBoundsComponent.class,
                PushComponent.class
        ).get();
        movableBlocksBiMap = new BiMap<Integer, Entity>();
        initMovableBlocks();
    }

    private void initMovableBlocks(){
        Vector2[] points = MapMaker.generateRandomCoordinates(numBlocks);
        for(int i =0; i<numBlocks; i++)
        {
            int key = PositionTracker.generateKey(points[i].x,points[i].y);
            Entity block = makeMovableBlock(points[i].x,points[i].y,
                    MovableTag.class,WPRegionNames.EMOTES_BLUE_COOL);
            movableBlocksBiMap.put(key, block);
        }
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> nighters = getEngine().getEntitiesFor(ENTITIES);

        for(Entity entity: nighters) {
            int key = Mappers.POSITION_TRACKER.get(entity).getPositionBiMap().getKey(entity);
            int keyAbove = key+PositionTracker.n;
            int keyBelow = key-PositionTracker.n;
            int [] keys = {keyAbove-1,keyAbove,keyAbove+1,
                    key-1, key, key+1,
                    keyBelow-1, keyBelow, keyBelow+1};
            checkCollision(entity,keys);
        }
    }

    private void checkCollision(Entity entity, int [] keys){
        for (int key: keys) {
            Entity movable_item = movableBlocksBiMap.get(key);

            if (movable_item != null) {
                if (checkCollisionBetween(entity, movable_item)) {
                    collideEvent(entity, movable_item);
                }
            }
        }
    }
    private boolean checkCollisionBetween(Entity undead, Entity obstacle)
    {
        RectangularBoundsComponent playerBounds = Mappers.RECTANGULAR_BOUNDS.get(undead);
        RectangularBoundsComponent obstacleBounds = Mappers.RECTANGULAR_BOUNDS.get(obstacle);

        return Intersector.overlaps(playerBounds.getBounds(),obstacleBounds.getBounds());
    }

    private void collideEvent(Entity entity, Entity movable_item) {
        //implement what happens during collision
        Direction entityDirection = Mappers.MOVEMENT.get(entity).getDirection();
        PushComponent pocketComponent = Mappers.POCKET.get(entity);
        if(!pocketComponent.getCarriedItems().contains(movable_item)&&entityDirection!=Direction.none){//if pocket does not already hold tht item
            pocketComponent.add(movable_item);
            Mappers.ITEM_SHOVE.get(movable_item).setDirection(entityDirection);
//            Mappers.MOVEMENT.get(movable_item).setDirection(entityDirection);
        }
    }

    public void reset() {
        movableBlocksBiMap.clear();
    }

    private Entity makeMovableBlock(float x, float y,java.lang.Class componentType, String regionName) {

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
        bounds.setBounds(x,y,dimension.getWidth(),dimension.getHeight());
        entity.add(bounds);

        WorldWrapTag worldWrap = engine.createComponent(WorldWrapTag.class);
        entity.add(worldWrap);

        PositionTrackerComponent positionTrackerComponent = engine.createComponent(PositionTrackerComponent.class);
        positionTrackerComponent.setPositionBiMap(movableBlocksBiMap);
        entity.add(positionTrackerComponent);


//        PushComponent pocketComponent = engine.createComponent(PushComponent.class);
//        entity.add(pocketComponent);//todo this makes everything laggy

        VelocityComponent movement = engine.createComponent(VelocityComponent.class);
        entity.add(movement);

        NonAutoMotionComponent nonAutoMotionComponent = engine.createComponent(NonAutoMotionComponent.class);
        entity.add(nonAutoMotionComponent);

        MovementLimitationComponent movementLimitationComponent = engine.createComponent(MovementLimitationComponent.class);
        entity.add(movementLimitationComponent);



        MonoColorComponent monoColorComponent = engine.createComponent(MonoColorComponent.class);
        monoColorComponent.setColor(Color.CYAN);
        entity.add(monoColorComponent);


        return entity;
    }

}