package snow.zhen.zayta.versions_unused.spirit_gathering;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import snow.zhen.zayta.main.assets.RegionNames;
import snow.zhen.zayta.util.KeyListMap;
import snow.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.Experiment;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.id_tags.NighterTag;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.MapMaker;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.util.Arrangements;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionTracker;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.AutoMovementTag;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.CircularBoundsComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.DimensionComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.PositionTrackerComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.WorldWrapComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.TextureComponent;
import snow.zhen.zayta.versions_unused.game_mechanics.GameControllingSystem;
import snow.zhen.zayta.versions_unused.game_mechanics.collision_mechanics.template_for_collision_system.CollisionListener;

public class SpiritSystem extends GameControllingSystem implements CollisionListener {

    //todo later add in wielder x mortal in this same class and rename class to undead x mortal collision system
    private int numSpirits = 5;
    private static final Logger log = new Logger(SpiritSystem.class.getName(),Logger.DEBUG);
    private TextureAtlas labAtlas;

    private snow.zhen.zayta.util.KeyListMap<Integer,Entity> spiritsKeyListMap;
    //families are entities that can collide
    private final Family NIGHTERS;

    public SpiritSystem(Experiment experiment, PooledEngine engine, TextureAtlas labAtlas){
        super(experiment,engine);
        addMission();
        labAtlas = labAtlas;
        NIGHTERS = Family.all(
                NighterTag.class,
                snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent.class
        ).get();


        spiritsKeyListMap = new KeyListMap<Integer, Entity>();
        initSpirits();
    }

    private void initSpirits(){
        Vector2[] points = Arrangements.generateRandomUCoordinates(numSpirits);
        for(int i =0; i<numSpirits; i++)
        {
            int key = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionTracker.generateKey(points[i].x,points[i].y);
            spiritsKeyListMap.put(key,makeSpirit(points[i].x,points[i].y, SpiritTag.class, RegionNames.EMOTES_BLUSH));
        }
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> nighters = getEngine().getEntitiesFor(NIGHTERS);

        for(Entity nighter: nighters) {

//            Direction direction = Mappers.MOVEMENT.get(nighter).getDirection();
//            int [] keys = new int [6];
//
            int key = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionTracker.PositionKeyListMap.nightersKeyListMap.getKeyListMap().getKey(nighter);
            int keyAbove = key+ snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionTracker.n;
            int keyBelow = key- PositionTracker.n;
            int [] keys = {key-1, key, key+1,
                            keyAbove-1, keyAbove, keyAbove+1,
                            keyBelow-1, keyBelow, keyBelow+1};
//            switch (direction){
//                case none:
//                    Entity spirit = spiritsKeyListMap.get(key);
//                    if(spirit!=null)
//                        checkCollisionBetween(nighter,spirit);
//                    break;
//                case up:
//                    keys[0]= keyAbove;
//                    keys[1]= keyAbove+1;
//                    keys[2]= keyAbove-1;
//                    keys[3] = key-1;
//                    keys[4] = key+1;
//                    break;
//                case down:
//                    keys[0]= keyBelow;
//                    keys[1]= keyBelow+1;
//                    keys[2]= keyBelow-1;
//                    keys[3] = key-1;
//                    keys[4] = key+1;
//                    break;
//                case left:
//                    keys[0]= keyAbove-1;
//                    keys[1]= key-1;
//                    keys[2]= keyBelow-1;
//                    keys[3] = keyAbove;
//                    keys[4] = keyBelow;
//                    break;
//                case right:
//                    keys[0]= keyAbove+1;
//                    keys[1]= key+1;
//                    keys[2]= keyBelow+1;
//                    keys[3] = keyAbove;
//                    keys[4] = keyBelow;
//                    break;
//            }
//            keys[5] = key;
            checkCollision(nighter,keys);
        }
    }
    private void checkCollision(Entity nighter, int [] keys){
        for (int key: keys) {
            Entity spirit = spiritsKeyListMap.get(key);

            if (spirit != null) {
                if (checkCollisionBetween(nighter, spirit)) {
                    collideEvent(nighter, spirit);
                }
            }
        }
    }
    private boolean checkCollisionBetween(Entity nighter, Entity spirit)
    {
        RectangularBoundsComponent playerBounds = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.RECTANGULAR_BOUNDS.get(nighter);
        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.CircularBoundsComponent spiritBounds = Mappers.CIRCULAR_BOUNDS.get(spirit);

        return Intersector.overlaps(spiritBounds.getBounds(),playerBounds.getBounds());
    }

    @Override
    public void collideEvent(Entity nighter, Entity spirit) {
//       //set spirit position, remove spirit movement component
//        Position spiritPos = Mappers.POSITION.get(spirit);
//        spiritPos.set(0,0);//later set elsewhere
//        spirit.remove(VelocityComponent.class);
        //todo set spirit in position at bottom of screen instead
        getEngine().removeEntity(spirit);
        spiritsKeyListMap.remove(spirit);
        if(spiritsKeyListMap.numObjects()==0){
            completeMission();
        }

    }

    @Override
    public void reset() {
        spiritsKeyListMap.clear();
    }




    private Entity makeSpirit(float x, float y,java.lang.Class componentType, String regionName) {


        PooledEngine engine = getEngine();
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
        dimension.set(snow.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager.maxObjWidth, SizeManager.maxObjHeight);
        entity.add(dimension);

        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.CircularBoundsComponent bounds = engine.createComponent(CircularBoundsComponent.class);
        bounds.setBounds(x,y-dimension.getHeight()/2,Math.min(dimension.getWidth(),dimension.getHeight())/2);

        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class); worldWrap.setBoundsOfMovement(MapMaker.getMapBounds());

        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent movementLimitationComponent = engine.createComponent(snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent.class);
        entity.add(movementLimitationComponent);

        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.PositionTrackerComponent positionTrackerComponent = engine.createComponent(PositionTrackerComponent.class);
        positionTrackerComponent.setPositionKeyListMap(spiritsKeyListMap);


        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent movement = engine.createComponent(VelocityComponent.class);
        movement.setDirection(Direction.generateRandomDirection());

//        ParticleAnimationComponent particleAnimationComponent = engine.createComponent(ParticleAnimationComponent.class);
//        particleAnimationComponent.init(texture.getRegion(),1,5);
//        entity.add(particleAnimationComponent);

        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.MovementLimitationComponent movementLimitationComponent1 = engine.createComponent(MovementLimitationComponent.class);
        entity.add(movementLimitationComponent);


        entity.add(bounds);
        entity.add(worldWrap);
        entity.add(positionTrackerComponent);
        entity.add(movement);

        //for certain movement systems
        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.AutoMovementTag autoMovementTag = engine.createComponent(AutoMovementTag.class);
        entity.add(autoMovementTag);

        return entity;
    }


}