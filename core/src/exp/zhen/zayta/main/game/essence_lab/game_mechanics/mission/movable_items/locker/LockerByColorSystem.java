package exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.movable_items.locker;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.RPG;
import exp.zhen.zayta.main.UIAssetDescriptors;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.entity.components.properties.ColorComponent;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.GameControllingSystem;
import exp.zhen.zayta.main.game.essence_lab.map.MapMaker;
import exp.zhen.zayta.main.game.essence_lab.map.util.Arrangements;
import exp.zhen.zayta.main.game.essence_lab.movement.PositionTracker;
import exp.zhen.zayta.main.game.essence_lab.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.Position;
import exp.zhen.zayta.main.game.essence_lab.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.WorldWrapComponent;
import exp.zhen.zayta.util.BiMap;

public class LockerByColorSystem extends GameControllingSystem {


    //todo later add in wielder x mortal in this same class and rename class to undead x mortal collision system
    private static final Logger log = new Logger(LockerByColorSystem.class.getName(),Logger.DEBUG);
    private TextureAtlas labAtlas;

    private BiMap<Integer,Entity> lockersBiMap;
    //families are entities that can collide
    private final Family LOCKER_KEYS;

    public LockerByColorSystem(RPG game, PooledEngine engine){
        super(game,engine);
        addMission();
        labAtlas = game.getAssetManager().get(UIAssetDescriptors.LAB);
        LOCKER_KEYS = Family.all(
                LockerKeyTag.class,
                RectangularBoundsComponent.class
        ).get();


        lockersBiMap = new BiMap<Integer, Entity>();
        initLockers();
    }

    private void initLockers(){
        int numLockers = 5;
        Vector2[] points = Arrangements.generateRandomUCoordinates(numLockers);
        for(int i =0; i<points.length; i++)
        {
            int key = PositionTracker.generateKey(points[i].x,points[i].y);
            lockersBiMap.put(key,makeLocker(points[i].x,points[i].y, LockerComponent.class/*,WPRegionNames.EMOTES_BLUE_EEK*/));
        }
//        //log.debug("lockerBiMap: "+lockersBiMap);
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> lockerKeys = getEngine().getEntitiesFor(LOCKER_KEYS);

        for(Entity lockerKey: lockerKeys) {
            int index = Mappers.POSITION_TRACKER.get(lockerKey).getPositionBiMap().getKey(lockerKey);
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
            Entity locker = lockersBiMap.get(key);

            if (locker != null) {
                if (isInside(locker,lockerKey)&&isSameColor(locker,lockerKey)) {
                    //log.debug("IsInside and same color");
                    unlock(locker,lockerKey);
                }
            }
        }
    }

    private boolean isSameColor(Entity locker, Entity lockerKey)
    {
        Color lockerColor = Mappers.COLOR.get(locker).getColor();
        //log.debug("LockerKey is " +lockerKey);
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

    private void unlock(Entity locker, Entity lockerKey) {

        LockerComponent lockerComponent = Mappers.LOCKER.get(locker);
        lockerComponent.decreaseNumRequiredKeys(1);
        if(lockerComponent.getNumRequiredKeys()<=0){
            getEngine().removeEntity(locker);
            lockersBiMap.removeKey(locker);

            if(lockersBiMap.size()==0){
                completeMission();
            }

        }

    }

    @Override
    public void reset() {
        lockersBiMap.clear();
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
