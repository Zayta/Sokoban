package snow.zhen.zayta.versions_unused.stone_gathering;

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
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.CircularBoundsComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.DimensionComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.WorldWrapComponent;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.TextureComponent;
import snow.zhen.zayta.versions_unused.game_mechanics.GameControllingSystem;
import snow.zhen.zayta.versions_unused.game_mechanics.collision_mechanics.template_for_collision_system.CollisionListener;


public class StonesSystem extends GameControllingSystem implements CollisionListener {

    //todo later add in wielder x mortal in this same class and rename class to undead x mortal collision system
    private static final Logger log = new Logger(StonesSystem.class.getName(),Logger.DEBUG);
    private TextureAtlas labAtlas;

    private snow.zhen.zayta.util.KeyListMap<Integer,Entity> stonesKeyListMap;
    //families are entities that can collide
    private final Family NIGHTERS;

    public StonesSystem(Experiment experiment, PooledEngine engine, TextureAtlas labAtlas){
        super(experiment,engine);
        addMission();
        this.labAtlas = labAtlas;
        NIGHTERS = Family.all(
                NighterTag.class,
                snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.RectangularBoundsComponent.class
        ).get();


        stonesKeyListMap = new KeyListMap<Integer, Entity>();
        initStones();
    }

    private void initStones(){
        int numStones = 5;
        Vector2[] points = Arrangements.generateRandomUCoordinates(numStones);
        for(int i =0; i<points.length; i++)
        {
            int key = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionTracker.generateKey(points[i].x,points[i].y);
            //////log.debug("Point "+i+ " is: ("+points[i].x+","+points[i].y+") and key is "+key);
            stonesKeyListMap.put(key,makeStone(points[i].x,points[i].y, StoneTag.class, RegionNames.STONE));
//            //////log.debug("iteration "+i+", pointsx: "+points[i].x+", points y: "+points[i].y+"\n"
//            +stonesKeyListMap.get(key));
        }
        //////log.debug("stoneKeyListMap: "+stonesKeyListMap);
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> nighters = getEngine().getEntitiesFor(NIGHTERS);
        
        for(Entity nighter: nighters) {

            Direction direction = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.MOVEMENT.get(nighter).getDirection();
            int [] keys = new int [6];

            int key = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionTracker.PositionKeyListMap.nightersKeyListMap.getKeyListMap().getKey(nighter);
            int keyAbove = key+ snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionTracker.n;
            int keyBelow = key- PositionTracker.n;
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
        RectangularBoundsComponent playerBounds = snow.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers.RECTANGULAR_BOUNDS.get(nighter);
        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.CircularBoundsComponent stoneBounds = Mappers.CIRCULAR_BOUNDS.get(stone);

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
        stonesKeyListMap.remove(stone);
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
        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.render.animation.TextureComponent texture = engine.createComponent(TextureComponent.class);
        engine.addEntity(entity);

//        PositionTrackerComponent positionTrackerComponent = engine.createComponent(PositionTrackerComponent.class);
//        positionTrackerComponent.setPositionKeyListMap(stonesKeyListMap);
//        entity.add(positionTrackerComponent);


        texture.setRegion(labAtlas.findRegion(regionName));
        entity.add(texture);


        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.Position position = engine.createComponent(Position.class);
        position.set(x,y);

        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.set(snow.zhen.zayta.versions_unused.arcade_style_game.config.SizeManager.maxObjWidth, SizeManager.maxObjHeight);

        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.CircularBoundsComponent bounds = engine.createComponent(CircularBoundsComponent.class);
        bounds.setBounds(x,y-dimension.getHeight()/2,Math.min(dimension.getWidth(),dimension.getHeight())/2);

        snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class); worldWrap.setBoundsOfMovement(MapMaker.getMapBounds());

        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(worldWrap);

        return entity;
    }


}
