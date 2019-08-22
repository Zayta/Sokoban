package exp.zhen.zayta.versions_unused.stone_gathering;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.essence_lab.Experiment;
import exp.zhen.zayta.main.game.essence_lab.assets.WPRegionNames;
import exp.zhen.zayta.main.game.essence_lab.engine.entity.id_tags.NighterTag;
import exp.zhen.zayta.versions_unused.game_mechanics.GameControllingSystem;
import exp.zhen.zayta.versions_unused.game_mechanics.collision_mechanics.template_for_collision_system.CollisionListener;
import exp.zhen.zayta.main.game.essence_lab.engine.map.MapMaker;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.Direction;
import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.PositionTracker;
import exp.zhen.zayta.main.game.essence_lab.engine.map.util.Arrangements;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.CircularBoundsComponent;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.Position;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.WorldWrapComponent;
import exp.zhen.zayta.main.game.essence_lab.engine.render.animation.TextureComponent;
import exp.zhen.zayta.util.KeyListMap;


public class StonesSystem extends GameControllingSystem implements CollisionListener {

    //todo later add in wielder x mortal in this same class and rename class to undead x mortal collision system
    private static final Logger log = new Logger(StonesSystem.class.getName(),Logger.DEBUG);
    private TextureAtlas labAtlas;

    private KeyListMap<Integer,Entity> stonesKeyListMap;
    //families are entities that can collide
    private final Family NIGHTERS;

    public StonesSystem(Experiment experiment, PooledEngine engine, TextureAtlas labAtlas){
        super(experiment,engine);
        addMission();
        this.labAtlas = labAtlas;
        NIGHTERS = Family.all(
                NighterTag.class,
                RectangularBoundsComponent.class
        ).get();


        stonesKeyListMap = new KeyListMap<Integer, Entity>();
        initStones();
    }

    private void initStones(){
        int numStones = 5;
        Vector2[] points = Arrangements.generateRandomUCoordinates(numStones);
        for(int i =0; i<points.length; i++)
        {
            int key = PositionTracker.generateKey(points[i].x,points[i].y);
            ////log.debug("Point "+i+ " is: ("+points[i].x+","+points[i].y+") and key is "+key);
            stonesKeyListMap.put(key,makeStone(points[i].x,points[i].y, StoneTag.class,WPRegionNames.STONE));
//            ////log.debug("iteration "+i+", pointsx: "+points[i].x+", points y: "+points[i].y+"\n"
//            +stonesKeyListMap.get(key));
        }
        ////log.debug("stoneKeyListMap: "+stonesKeyListMap);
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> nighters = getEngine().getEntitiesFor(NIGHTERS);
        
        for(Entity nighter: nighters) {

            Direction direction = Mappers.MOVEMENT.get(nighter).getDirection();
            int [] keys = new int [6];

            int key = PositionTracker.PositionKeyListMap.nightersKeyListMap.getKeyListMap().getKey(nighter);
            int keyAbove = key+PositionTracker.n;
            int keyBelow = key-PositionTracker.n;
            switch (direction){
                case none:
                    Entity stone = stonesKeyListMap.get(key);
                    if(stone!=null)
                        checkCollisionBetween(nighter,stone);
                    continue;
                case up:
                    keys[0]= keyAbove;
                    keys[1]= keyAbove+1;
                    keys[2]= keyAbove-1;
                    keys[3] = key-1;
                    keys[4] = key+1;
                    break;
                case down:
                    keys[0]= keyBelow;
                    keys[1]= keyBelow+1;
                    keys[2]= keyBelow-1;
                    keys[3] = key-1;
                    keys[4] = key+1;
                    break;
                case left:
                    keys[0]= keyAbove-1;
                    keys[1]= key-1;
                    keys[2]= keyBelow-1;
                    keys[3] = keyAbove;
                    keys[4] = keyBelow;
                    break;
                case right:
                    keys[0]= keyAbove+1;
                    keys[1]= key+1;
                    keys[2]= keyBelow+1;
                    keys[3] = keyAbove;
                    keys[4] = keyBelow;
                    break;
            }
            keys[5] = key;
            checkCollision(nighter,keys);


//            int key = PositionTracker.PositionKeyListMap.nightersKeyListMap.getKeyListMap().getKey(nighter);
//            int keyAbove = key+PositionTracker.n;
//            int keyBelow = key-PositionTracker.n;
//            int [] keys = {keyAbove-1,keyAbove,keyAbove+1,
//                    key-1, key, key+1,
//                    keyBelow-1, keyBelow, keyBelow+1};
//            checkCollision(nighter,keys);
        }
    }
    private void checkCollision(Entity nighter, int [] keys){
        for (int key: keys) {
            Entity stone = stonesKeyListMap.get(key);

            if (stone != null) {
                if (checkCollisionBetween(nighter, stone)) {
                    collideEvent(nighter, stone);
                }
            }
        }
    }
    private boolean checkCollisionBetween(Entity nighter, Entity stone)
    {
        RectangularBoundsComponent playerBounds = Mappers.RECTANGULAR_BOUNDS.get(nighter);
        CircularBoundsComponent stoneBounds = Mappers.CIRCULAR_BOUNDS.get(stone);

        return Intersector.overlaps(stoneBounds.getBounds(),playerBounds.getBounds());
    }

    @Override
    public void collideEvent(Entity nighter, Entity stone) {
//       //set stone position, remove stone movement component
//        Position stonePos = Mappers.POSITION.get(stone);
//        stonePos.set(0,0);//later set elsewhere
//        stone.remove(VelocityComponent.class);
        //todo set stone in position at bottom of screen instead
        getEngine().removeEntity(stone);
        stonesKeyListMap.removeKey(stone);
        if(stonesKeyListMap.numObjects()==0){
            completeMission();
        }

    }

    @Override
    public void reset() {
        stonesKeyListMap.clear();
    }




    private Entity makeStone(float x, float y,java.lang.Class componentType, String regionName) {


        PooledEngine engine = getEngine();

        Entity entity = engine.createEntity();

        entity.add(engine.createComponent(componentType));//adds identifier
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        engine.addEntity(entity);

//        PositionTrackerComponent positionTrackerComponent = engine.createComponent(PositionTrackerComponent.class);
//        positionTrackerComponent.setPositionKeyListMap(stonesKeyListMap);
//        entity.add(positionTrackerComponent);


        texture.setRegion(labAtlas.findRegion(regionName));
        entity.add(texture);


        Position position = engine.createComponent(Position.class);
        position.set(x,y);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.set(SizeManager.maxObjWidth,SizeManager.maxObjHeight);

        CircularBoundsComponent bounds = engine.createComponent(CircularBoundsComponent.class);
        bounds.setBounds(x,y-dimension.getHeight()/2,Math.min(dimension.getWidth(),dimension.getHeight())/2);

        WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class); worldWrap.setBoundsOfMovement(MapMaker.getMapBounds());

        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(worldWrap);

        return entity;
    }


}
