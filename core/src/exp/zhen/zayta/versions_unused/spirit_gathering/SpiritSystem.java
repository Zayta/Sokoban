package exp.zhen.zayta.versions_unused.spirit_gathering;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.RPG;
import exp.zhen.zayta.main.UIAssetDescriptors;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.essence_lab.Experiment;
import exp.zhen.zayta.main.game.essence_lab.assets.WPRegionNames;
import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.entity.id_tags.NighterTag;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.GameControllingSystem;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.collision_mechanics.template_for_collision_system.CollisionListener;
import exp.zhen.zayta.main.game.essence_lab.map.MapMaker;
import exp.zhen.zayta.main.game.essence_lab.map.util.Arrangements;
import exp.zhen.zayta.main.game.essence_lab.movement.Direction;
import exp.zhen.zayta.main.game.essence_lab.movement.PositionTracker;
import exp.zhen.zayta.main.game.essence_lab.movement.component.AutoMovementTag;
import exp.zhen.zayta.main.game.essence_lab.movement.component.CircularBoundsComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.Position;
import exp.zhen.zayta.main.game.essence_lab.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.VelocityComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.WorldWrapComponent;
import exp.zhen.zayta.main.game.essence_lab.render.animation.TextureComponent;
import exp.zhen.zayta.util.KeyListMap;

public class SpiritSystem extends GameControllingSystem implements CollisionListener {

    //todo later add in wielder x mortal in this same class and rename class to undead x mortal collision system
    private int numSpirits = 5;
    private static final Logger log = new Logger(exp.zhen.zayta.versions_unused.spirit_gathering.SpiritSystem.class.getName(),Logger.DEBUG);
    private TextureAtlas labAtlas;

    private KeyListMap<Integer,Entity> spiritsKeyListMap;
    //families are entities that can collide
    private final Family NIGHTERS;

    public SpiritSystem(Experiment experiment, PooledEngine engine, TextureAtlas labAtlas){
        super(experiment,engine);
        addMission();
        labAtlas = labAtlas;
        NIGHTERS = Family.all(
                NighterTag.class,
                RectangularBoundsComponent.class
        ).get();


        spiritsKeyListMap = new KeyListMap<Integer, Entity>();
        initSpirits();
    }

    private void initSpirits(){
        Vector2[] points = Arrangements.generateRandomUCoordinates(numSpirits);
        for(int i =0; i<numSpirits; i++)
        {
            int key = PositionTracker.generateKey(points[i].x,points[i].y);
            spiritsKeyListMap.put(key,makeSpirit(points[i].x,points[i].y, SpiritTag.class,WPRegionNames.EMOTES_BLUSH));
        }
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> nighters = getEngine().getEntitiesFor(NIGHTERS);

        for(Entity nighter: nighters) {

//            Direction direction = Mappers.MOVEMENT.get(nighter).getDirection();
//            int [] keys = new int [6];
//
            int key = PositionTracker.PositionKeyListMap.nightersKeyListMap.getKeyListMap().getKey(nighter);
            int keyAbove = key+PositionTracker.n;
            int keyBelow = key-PositionTracker.n;
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
        RectangularBoundsComponent playerBounds = Mappers.RECTANGULAR_BOUNDS.get(nighter);
        CircularBoundsComponent spiritBounds = Mappers.CIRCULAR_BOUNDS.get(spirit);

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
        spiritsKeyListMap.removeKey(spirit);
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

        CircularBoundsComponent bounds = engine.createComponent(CircularBoundsComponent.class);
        bounds.setBounds(x,y-dimension.getHeight()/2,Math.min(dimension.getWidth(),dimension.getHeight())/2);

        WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class); worldWrap.setBoundsOfMovement(MapMaker.getMapBounds());

        MovementLimitationComponent movementLimitationComponent = engine.createComponent(MovementLimitationComponent.class);
        entity.add(movementLimitationComponent);

        PositionTrackerComponent positionTrackerComponent = engine.createComponent(PositionTrackerComponent.class);
        positionTrackerComponent.setPositionKeyListMap(spiritsKeyListMap);


        VelocityComponent movement = engine.createComponent(VelocityComponent.class);
        movement.setDirection(Direction.generateRandomDirection());

//        ParticleAnimationComponent particleAnimationComponent = engine.createComponent(ParticleAnimationComponent.class);
//        particleAnimationComponent.init(texture.getRegion(),1,5);
//        entity.add(particleAnimationComponent);

        MovementLimitationComponent movementLimitationComponent1 = engine.createComponent(MovementLimitationComponent.class);
        entity.add(movementLimitationComponent);


        entity.add(bounds);
        entity.add(worldWrap);
        entity.add(positionTrackerComponent);
        entity.add(movement);

        //for certain movement systems
        AutoMovementTag autoMovementTag = engine.createComponent(AutoMovementTag.class);
        entity.add(autoMovementTag);

        return entity;
    }


}