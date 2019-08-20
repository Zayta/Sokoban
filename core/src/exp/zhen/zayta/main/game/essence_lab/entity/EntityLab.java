package exp.zhen.zayta.main.game.essence_lab.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.UIAssetDescriptors;
import exp.zhen.zayta.main.game.characters.Undead;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.config.SpeedManager;
import exp.zhen.zayta.main.game.essence_lab.assets.WPRegionNames;
import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.entity.components.labels.NPCTag;
import exp.zhen.zayta.main.game.essence_lab.entity.components.labels.PlayerTag;
import exp.zhen.zayta.main.game.essence_lab.entity.id_tags.MortalTag;
import exp.zhen.zayta.main.game.essence_lab.entity.nur.NUR;
import exp.zhen.zayta.main.game.essence_lab.entity.utsubyo.Utsubyo;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.movable_items.components.PushComponent;
import exp.zhen.zayta.main.game.essence_lab.map.MapMaker;
import exp.zhen.zayta.main.game.essence_lab.map.util.Arrangements;
import exp.zhen.zayta.main.game.essence_lab.movement.Direction;
import exp.zhen.zayta.main.game.essence_lab.movement.PositionTracker;
import exp.zhen.zayta.main.game.essence_lab.movement.component.AutoMovementTag;
import exp.zhen.zayta.main.game.essence_lab.movement.component.CircularBoundsComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.Position;
import exp.zhen.zayta.main.game.essence_lab.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.VelocityComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.WorldWrapComponent;
import exp.zhen.zayta.main.game.essence_lab.render.animation.sprite.SpriteAnimationComponent;
import exp.zhen.zayta.main.game.essence_lab.render.animation.TextureComponent;

public class EntityLab {

    private static final Logger log = new Logger(EntityLab.class.getName(),Logger.DEBUG);
    private PooledEngine engine;
    private TextureAtlas labAtlas;
    private NUR nur; private Utsubyo utsubyo;

    public EntityLab(PooledEngine engine, AssetManager assetManager)
    {
        this.engine = engine;
        labAtlas = assetManager.get(UIAssetDescriptors.LAB);
        nur = new NUR(engine,labAtlas); utsubyo = new Utsubyo(engine,labAtlas);
    }

    public void addEntities(int numNighters,int numMonsters) {
//        initCivilization();
        addNighters(numNighters);
        addMonsters(numMonsters);
    }

    /**For player**/
    private void addNighters(int numNighters){
        /*add NighterPool*/
        Vector2[] points = Arrangements.generateRandomUCoordinates(numNighters);

        for(int i = 0; i<points.length;i++) {
            Entity nighter = nur.getNighter(Undead.values()[i]);
            PlayerTag playerTag = engine.createComponent(PlayerTag.class);
            nighter.add(playerTag);
            addMovementComponents(engine, nighter, points[i].x, points[i].y, PositionTracker.PositionKeyListMap.nightersKeyListMap);
            PushComponent pocketComponent = engine.createComponent(PushComponent.class);
            nighter.add(pocketComponent);
            engine.addEntity(nighter);
        }
    }




    /**For monsters**/
    private void addMonsters(int numMonsters){
        //todo also in future make civilians change direction randomly
        /*add Monsters*/
        float minX = 0; float maxX = SizeManager.WAKE_WORLD_WIDTH-SizeManager.maxObjWidth;
        float minY = 0; float maxY = SizeManager.WAKE_WORLD_HEIGHT-SizeManager.maxObjHeight;
        for(int i = 0; i<numMonsters; i++) {
            float civX = MathUtils.random(minX,maxX);
            float civY = MathUtils.random(minY,maxY);
            addMonster(civX, civY);
        }

    }
    private void addMonster(float x, float y){
        Entity entity = utsubyo.generateMonster(1);
        addMovementComponents(engine,entity,x,y,PositionTracker.PositionKeyListMap.monstersKeyListMap);
        PushComponent pocketComponent = engine.createComponent(PushComponent.class);
        entity.add(pocketComponent);


        AutoMovementTag autoMovementTag = engine.createComponent(AutoMovementTag.class);
        entity.add(autoMovementTag);

        engine.addEntity(entity);

        Mappers.MOVEMENT.get(entity).setDirection(Direction.generateRandomDirection());
    }





    private void addMovementComponents(PooledEngine engine, Entity entity, float x, float y, PositionTracker.PositionKeyListMap posMap){
        addPositionComponents(engine,entity,x,y);

        PositionTrackerComponent positionTrackerComponent = engine.createComponent(PositionTrackerComponent.class);
        positionTrackerComponent.setPositionKeyListMap(posMap);
        entity.add(positionTrackerComponent);

        VelocityComponent movement = engine.createComponent(VelocityComponent.class);
        movement.setSpeed(SpeedManager.DEFAULT_SPEED,SpeedManager.DEFAULT_SPEED);
        entity.add(movement);

        MovementLimitationComponent movementLimitationComponent = engine.createComponent(MovementLimitationComponent.class);
        entity.add(movementLimitationComponent);
        //todo for ghostification remove movementLimitationComponent and block component
//        BlockComponent blockComponent = engine.createComponent(BlockComponent.class);
//        entity.add(blockComponent);//adding block component to entity causes lag cuz of the setPosition taht is used with the blocks.
    }

    public static void addRoundPositionComponents(PooledEngine engine,Entity entity,float x, float y){

        CircularBoundsComponent bounds = engine.createComponent(CircularBoundsComponent.class);
        entity.add(bounds);
        addPositionComponents(engine,entity,x,y);
    }

     static void addPositionComponents(PooledEngine engine, Entity entity, float x, float y){
        Position position = engine.createComponent(Position.class);
        position.set(x,y);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.set(SizeManager.maxObjWidth,SizeManager.maxObjHeight);

        RectangularBoundsComponent bounds = engine.createComponent(RectangularBoundsComponent.class);
//        bounds.setBounds(x-dimension.getWidth()/2,y-dimension.getHeight()/2,SizeManager.maxBoundsRadius);
//        bounds.setBounds(x,y-dimension.getHeight()/2,SizeManager.maxBoundsRadius);
//        bounds.setBounds(x-dimension.getWidth()/2,y-dimension.getHeight()/2,dimension.getWidth(),dimension.getHeight());
        bounds.setBounds(x,y-dimension.getHeight()/2,dimension.getWidth(),dimension.getHeight());
        WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class); worldWrap.setBoundsOfMovement(MapMaker.getMapBounds());

        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(worldWrap);
    }







//    /**For society**/
//    private void initCivilization(){
//        //todo also in future make civilians change direction randomly
//        /*add Civilization*/
//        int numCivilians = 5;
//        float minX = 0; float maxX = SizeManager.WAKE_WORLD_WIDTH-SizeManager.maxObjWidth;
//        float minY = 0; float maxY = SizeManager.WAKE_WORLD_HEIGHT-SizeManager.maxObjHeight;
//        for(int i = 0; i<numCivilians; i++) {
//            float civX = MathUtils.random(minX,maxX);
//            float civY = MathUtils.random(minY,maxY);
//            addCivilian(civX, civY);
//        }
//
//    }
//    private void addCivilian(float x, float y){
//        NPCTag npcTag = engine.createComponent(NPCTag.class);
//
//        MortalTag mortalTag = engine.createComponent(MortalTag.class);
//
//        TextureComponent texture = engine.createComponent(TextureComponent.class);
//
//        SpriteAnimationComponent spriteAnimationComponent = engine.createComponent(SpriteAnimationComponent.class);
//        spriteAnimationComponent.init(labAtlas.findRegion(WPRegionNames.CIVILIAN));
//
//
//        Entity entity = engine.createEntity();
//        addMovementComponents(engine,entity,x,y,PositionTracker.PositionKeyListMap.civiliansKeyListMap);
//        entity.add(npcTag);
//        entity.add(mortalTag);
//        entity.add(texture);
//        entity.add(spriteAnimationComponent);
//
//        AutoMovementTag autoMovementTag = engine.createComponent(AutoMovementTag.class);
//        entity.add(autoMovementTag);
//        engine.addEntity(entity);
//
//        Mappers.MOVEMENT.get(entity).setDirection(Direction.generateRandomDirection());
//    }


}
