package exp.zhen.zayta.main.game.wake.game_mechanics.mission.spirit_gathering;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
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
import exp.zhen.zayta.main.game.wake.assets.WPRegionNames;
import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.entity.components.labels.NPCTag;
import exp.zhen.zayta.main.game.wake.entity.components.labels.PlayerTag;
import exp.zhen.zayta.main.game.wake.entity.id_tags.NighterTag;
import exp.zhen.zayta.main.game.wake.entity.util.Arrangements;
import exp.zhen.zayta.main.game.wake.game_mechanics.GameControllingSystem;
import exp.zhen.zayta.main.game.wake.game_mechanics.collision_mechanics.template_for_collision_system.CollisionListener;
import exp.zhen.zayta.main.game.wake.game_mechanics.mission.spirit_gathering.SpiritTag;
import exp.zhen.zayta.main.game.wake.movement.Direction;
import exp.zhen.zayta.main.game.wake.movement.PositionTracker;
import exp.zhen.zayta.main.game.wake.movement.component.CircularBoundsComponent;
import exp.zhen.zayta.main.game.wake.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.wake.movement.component.Position;
import exp.zhen.zayta.main.game.wake.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.wake.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.wake.movement.component.VelocityComponent;
import exp.zhen.zayta.main.game.wake.movement.component.WorldWrapTag;
import exp.zhen.zayta.main.game.wake.render.animation.TextureComponent;
import exp.zhen.zayta.main.game.wake.render.animation.particle.ParticleAnimationComponent;
import exp.zhen.zayta.util.BiMap;

public class SpiritSystem extends GameControllingSystem implements CollisionListener {

    //todo later add in wielder x mortal in this same class and rename class to undead x mortal collision system
    private static final Logger log = new Logger(exp.zhen.zayta.main.game.wake.game_mechanics.mission.spirit_gathering.SpiritSystem.class.getName(),Logger.DEBUG);
    private TextureAtlas wakePlayAtlas;

    private BiMap<Integer,Entity> spiritsBiMap;
    //families are entities that can collide
    private final Family NIGHTERS;

    public SpiritSystem(RPG game, PooledEngine engine){
        super(game,engine);
        addMission();
        wakePlayAtlas = game.getAssetManager().get(UIAssetDescriptors.WAKE_PLAY);
        NIGHTERS = Family.all(
                NighterTag.class,
                RectangularBoundsComponent.class
        ).get();


        spiritsBiMap = new BiMap<Integer, Entity>();
        initSpirits();
    }

    private void initSpirits(){
        int numSpirits = 5;
        Vector2[] points = Arrangements.circle(numSpirits,SizeManager.WAKE_WORLD_CENTER_X,SizeManager.WAKE_WORLD_CENTER_Y,SizeManager.WAKE_WORLD_WIDTH/3);
        for(int i =0; i<numSpirits; i++)
        {
            int key = PositionTracker.generateKey(points[i].x,points[i].y);
            spiritsBiMap.put(key,makeSpirit(points[i].x,points[i].y, SpiritTag.class,WPRegionNames.LIGHT_EFFECTS[1]));
        }
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> nighters = getEngine().getEntitiesFor(NIGHTERS);

        for(Entity nighter: nighters) {

            Direction direction = Mappers.MOVEMENT.get(nighter).getDirection();
            int [] keys = new int [6];

            int key = PositionTracker.PositionBiMap.nightersBiMap.getBiMap().getKey(nighter);
            int keyAbove = key+PositionTracker.n;
            int keyBelow = key-PositionTracker.n;
            switch (direction){
                case none:
                    Entity spirit = spiritsBiMap.get(key);
                    if(spirit!=null)
                        checkCollisionBetween(nighter,spirit);
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
        }
    }
    private void checkCollision(Entity nighter, int [] keys){
        for (int key: keys) {
            Entity spirit = spiritsBiMap.get(key);

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
        spiritsBiMap.removeKey(spirit);
        if(spiritsBiMap.size()==0){
            completeMission();
        }

    }

    @Override
    public void reset() {
        spiritsBiMap.clear();
    }




    private Entity makeSpirit(float x, float y,java.lang.Class componentType, String regionName) {


        PooledEngine engine = getEngine();
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

        CircularBoundsComponent bounds = engine.createComponent(CircularBoundsComponent.class);
        bounds.setBounds(x,y-dimension.getHeight()/2,Math.min(dimension.getWidth(),dimension.getHeight())/2);

        WorldWrapTag worldWrap = engine.createComponent(WorldWrapTag.class);

        PositionTrackerComponent positionTrackerComponent = engine.createComponent(PositionTrackerComponent.class);
        positionTrackerComponent.setPositionBiMap(spiritsBiMap);


        VelocityComponent movement = engine.createComponent(VelocityComponent.class);
        movement.setDirection(Direction.generateRandomDirection());

        ParticleAnimationComponent particleAnimationComponent = engine.createComponent(ParticleAnimationComponent.class);
        particleAnimationComponent.init(texture.getRegion(),1,5);

        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(worldWrap);
        entity.add(positionTrackerComponent);
        entity.add(movement);
        entity.add(particleAnimationComponent);

        //for certain movement systems
        NPCTag npcTag = engine.createComponent(NPCTag.class);
        entity.add(npcTag);

        return entity;
    }


}