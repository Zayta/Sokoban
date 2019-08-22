package exp.zhen.zayta.main.game.movable_items.locker;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import java.util.ArrayList;
import java.util.PriorityQueue;

import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.essence_lab.Experiment;
import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.engine.entity.components.properties.ColorComponent;
import exp.zhen.zayta.versions_unused.game_mechanics.GameControllingSystem;
import exp.zhen.zayta.main.game.essence_lab.engine.map.MapMaker;
import exp.zhen.zayta.main.game.essence_lab.engine.map.util.Arrangements;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.PositionTracker;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.Position;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.essence_lab.engine.movement.component.WorldWrapComponent;

import exp.zhen.zayta.util.KeyListMap;

public class LockerByColorSystem extends GameControllingSystem {


    //todo later add in wielder x mortal in this same class and rename class to undead x mortal collision system
    private static final Logger log = new Logger(LockerByColorSystem.class.getName(),Logger.DEBUG);
    private TextureAtlas labAtlas;

    private KeyListMap<Integer,Entity> lockersKeyListMap;
    //families are entities that can collide
    private final Family LOCKER_KEYS;

    public LockerByColorSystem(Experiment experiment, PooledEngine engine, TextureAtlas labAtlas){
        super(experiment,engine);
        addMission();
        this.labAtlas = labAtlas;
        LOCKER_KEYS = Family.all(
                LockerKeyTag.class,
                RectangularBoundsComponent.class
        ).get();


        lockersKeyListMap = new KeyListMap<Integer, Entity>();
        initLockers();
    }

    private void initLockers(){
        int numLockers = 5;
        Vector2[] points = Arrangements.generateRandomUCoordinates(numLockers);
        for(int i =0; i<points.length; i++)
        {
            int key = PositionTracker.generateKey(points[i].x,points[i].y);
            lockersKeyListMap.put(key,makeLocker(points[i].x,points[i].y, LockerComponent.class/*,WPRegionNames.EMOTES_BLUE_EEK*/));
        }
//        ////log.debug("lockerKeyListMap: "+lockersKeyListMap);
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> lockerKeys = getEngine().getEntitiesFor(LOCKER_KEYS);

        for(Entity lockerKey: lockerKeys) {
            int index = Mappers.POSITION_TRACKER.get(lockerKey).getPositionKeyListMap().getKey(lockerKey);
            int indexAbove = index+PositionTracker.n;
            int indexBelow = index-PositionTracker.n;
            int [] LOCKER_KEYS = {indexAbove-1,indexAbove,indexAbove+1,
                    index-1, index, index+1,
                    indexBelow-1, indexBelow, indexBelow+1};
            checkPlacements(lockerKey,LOCKER_KEYS);
        }
    }
    private void checkPlacements(Entity lockerKey, int [] LOCKER_KEYS){

        for (int key: LOCKER_KEYS) {
            ArrayList<Entity> lockers = lockersKeyListMap.getList(key);
            if (lockers != null) {
                for(Entity locker:lockers) {
                    if (isInside(locker, lockerKey) && isSameColor(locker, lockerKey)) {
                        ////log.debug("IsInside and same color");
                        scheduleUnlock(locker, lockerKey);
                    }
                }
                unlockLockers();

            }
        }
    }

    private boolean isSameColor(Entity locker, Entity lockerKey)
    {
        Color lockerColor = Mappers.COLOR.get(locker).getColor();
        ////log.debug("LockerKey is " +lockerKey);
        Color lockerKeyColor = Mappers.COLOR.get(lockerKey).getColor();
        return lockerColor==lockerKeyColor;
    }

    private boolean isInside(Entity locker, Entity lockerKey)
    {
        RectangularBoundsComponent lockerBounds = Mappers.RECTANGULAR_BOUNDS.get(locker);
        RectangularBoundsComponent lockerKeyBounds = Mappers.RECTANGULAR_BOUNDS.get(lockerKey);
//        return lockerBounds.getBounds().contains(lockerKeyBounds.getBounds());
        return Intersector.overlaps(lockerBounds.getBounds(),lockerKeyBounds.getBounds());
    }

    private PriorityQueue<Entity> scheduleUnlockedLockers = new PriorityQueue<Entity>();
    private void scheduleUnlock(Entity locker, Entity lockerKey) {

        LockerComponent lockerComponent = Mappers.LOCKER.get(locker);
        lockerComponent.decreaseNumRequiredKeys(1);
        if(lockerComponent.getNumRequiredKeys()<=0){
            scheduleUnlockedLockers.offer(locker);

        }

    }
    private void unlockLockers(){
        while(!scheduleUnlockedLockers.isEmpty()){
            Entity locker = scheduleUnlockedLockers.poll();
            getEngine().removeEntity(locker);
            lockersKeyListMap.removeKey(locker);

            if(lockersKeyListMap.numObjects()==0){
                completeMission();
            }
        }
    }


    @Override
    public void reset() {
        lockersKeyListMap.clear();
    }




    private Entity makeLocker(float x, float y,java.lang.Class componentType/*, String regionName*/) {
        PooledEngine engine = getEngine();

        Entity entity = engine.createEntity();
        engine.addEntity(entity);

        entity.add(engine.createComponent(componentType));//adds identifier

//        TextureComponent texture = engine.createComponent(TextureComponent.class);
//        texture.setRegion(labAtlas.findRegion(regionName));
//        entity.add(texture);

        Position position = engine.createComponent(Position.class);
        position.set(x,y);

        //todo not sure about what the dimension should be. need AI random generator to make sure still doable with certain dimension
        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.set(SizeManager.maxObjWidth,SizeManager.maxObjHeight);


        RectangularBoundsComponent bounds = engine.createComponent(RectangularBoundsComponent.class);
        bounds.setBounds(x,y,dimension.getWidth(),dimension.getHeight());
        entity.add(bounds);

        WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class); worldWrap.setBoundsOfMovement(MapMaker.getMapBounds());

        ColorComponent colorComponent = engine.createComponent(ColorComponent.class);
        colorComponent.setColor(Color.CYAN);//todo rn all lockers are orange. Change later.

        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(worldWrap);
        entity.add(colorComponent);

        return entity;
    }

}
