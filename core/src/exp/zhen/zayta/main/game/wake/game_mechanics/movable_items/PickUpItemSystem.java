package exp.zhen.zayta.main.game.wake.game_mechanics.movable_items;

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

import exp.zhen.zayta.main.game.wake.assets.WPRegionNames;
import exp.zhen.zayta.main.game.wake.movement.Direction;
import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.wake.entity.id_tags.NighterTag;
import exp.zhen.zayta.main.game.wake.movement.PositionTracker;
import exp.zhen.zayta.main.game.wake.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.wake.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.wake.movement.component.Position;
import exp.zhen.zayta.main.game.wake.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.wake.movement.component.VelocityComponent;
import exp.zhen.zayta.main.game.wake.entity.util.Arrangements;
import exp.zhen.zayta.main.game.wake.movement.component.WorldWrapTag;
import exp.zhen.zayta.main.game.wake.render.animation.TextureComponent;
import exp.zhen.zayta.main.game.wake.render.animation.particle.ParticleAnimationComponent;
import exp.zhen.zayta.util.BiMap;

public class PickUpItemSystem extends EntitySystem  {

    private PooledEngine engine; private final Viewport viewport;private TextureAtlas wakePlayAtlas;
    private static final Logger log = new Logger(PickUpItemSystem.class.getName(),Logger.DEBUG);

    private BiMap<Integer,Entity> movableBlocksBiMap;
    //families are entities that can collide
    private final Family ENTITIES;
    
    public PickUpItemSystem(PooledEngine engine, Viewport viewport, TextureAtlas wakePlayAtlas){
        this.engine = engine; this.viewport = viewport; this.wakePlayAtlas = wakePlayAtlas;
        ENTITIES = Family.all(
                NighterTag.class,//todo for debug only, remove when done
                PositionTrackerComponent.class,
                Position.class,
                VelocityComponent.class,
                RectangularBoundsComponent.class,
                PocketComponent.class
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
                    break;
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
        Mappers.POCKET.get(entity).add(movable_item);
        Mappers.ITEM_SHOVE.get(movable_item).setDirection(entityDirection);
    }
    
    public void reset() {
        movableBlocksBiMap.clear();
    }

    
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

        RectangularBoundsComponent bounds = engine.createComponent(RectangularBoundsComponent.class);
//        bounds.setBounds(x,y-dimension.getHeight()/2,Math.min(dimension.getWidth(),dimension.getHeight())/2);
        bounds.setBounds(x,y,dimension.getWidth(),dimension.getHeight());

        WorldWrapTag worldWrap = engine.createComponent(WorldWrapTag.class);

        PositionTrackerComponent positionTrackerComponent = engine.createComponent(PositionTrackerComponent.class);
        positionTrackerComponent.setPositionBiMap(movableBlocksBiMap);


//        VelocityComponent movement = engine.createComponent(VelocityComponent.class);
//        movement.setDirection(Direction.none);

        ParticleAnimationComponent particleAnimationComponent = engine.createComponent(ParticleAnimationComponent.class);
        particleAnimationComponent.init(texture.getRegion(),1,5);

        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(worldWrap);
        entity.add(positionTrackerComponent);
//        entity.add(movement);
        entity.add(particleAnimationComponent);

        return entity;
    }

}