package exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.mind_growing;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import exp.zhen.zayta.RPG;
import exp.zhen.zayta.main.UIAssetDescriptors;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.essence_lab.assets.WPAssetDescriptors;
import exp.zhen.zayta.main.game.essence_lab.assets.WPRegionNames;
import exp.zhen.zayta.main.game.essence_lab.blocks.BlockComponent;
import exp.zhen.zayta.main.game.essence_lab.blocks.MovingBlockTag;
import exp.zhen.zayta.main.game.essence_lab.entity.components.properties.ColorComponent;
import exp.zhen.zayta.main.game.essence_lab.entity.components.properties.explosion.ExplosiveComponent;
import exp.zhen.zayta.main.game.essence_lab.map.MapMaker;
import exp.zhen.zayta.main.game.essence_lab.map.util.Arrangements;
import exp.zhen.zayta.main.game.essence_lab.movement.Direction;
import exp.zhen.zayta.main.game.essence_lab.movement.PositionTracker;
import exp.zhen.zayta.main.game.essence_lab.movement.component.AutoMovementTag;
import exp.zhen.zayta.main.game.essence_lab.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.Position;
import exp.zhen.zayta.main.game.essence_lab.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.VelocityComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.WorldWrapComponent;
import exp.zhen.zayta.main.game.essence_lab.render.animation.TextureComponent;
import exp.zhen.zayta.main.game.essence_lab.render.animation.sprite.SpriteAnimationComponent;
import exp.zhen.zayta.main.game.essence_lab.render.mono_color.MonoColorRenderTag;
import exp.zhen.zayta.util.GdxUtils;
import exp.zhen.zayta.util.KeyListMap;

public class LanternSpawnSystem extends IntervalSystem {

    private PooledEngine engine;
    private TextureAtlas labAtlas;

    private Vector2 spawnPoint;
    private KeyListMap<Integer,Entity> lanternsKeyListMap;
    private TextureRegion[] textureRegions;
    public LanternSpawnSystem(RPG game, PooledEngine engine, float interval) {
        super(interval);
        this.engine = engine;
        this.labAtlas = game.getAssetManager().get(UIAssetDescriptors.LAB);
        textureRegions = new TextureRegion[4];
        //init textureRegions
        String [] textureRegionNames = {WPRegionNames.FIRE_BLOB_BACK,WPRegionNames.FIRE_BLOB_FRONT,WPRegionNames.FIRE_BLOB_LEFT,WPRegionNames.FIRE_BLOB_RIGHT};
        for(int i = 0; i<textureRegionNames.length;i++){
            textureRegions[i] = labAtlas.findRegion(textureRegionNames[i]);
        }

        lanternsKeyListMap = new KeyListMap<Integer, Entity>();
        spawnPoint = Arrangements.generateRandomUCoordinates(1)[0];
        engine.addSystem(new LanternSystem(game,engine,lanternsKeyListMap));
    }

    @Override
    protected void updateInterval() {
        //to generate from certain spot, take out randomeness
        int key = PositionTracker.generateKey(spawnPoint.x, spawnPoint.y);
            lanternsKeyListMap.put(key, makeLantern(spawnPoint.x, spawnPoint.y,
                    LanternTag.class,
                    textureRegions));//todo set new texture to be WPRegionNames.Blocks[randomInt() in bounds]
//        Vector2[] points = Arrangements.generateRandomUCoordinates(1);
//        for(Vector2 point:points) {
//            int key = PositionTracker.generateKey(point.x, point.y);
//            lanternsKeyListMap.put(key, makeLantern(point.x, point.y, MovingBlockTag.class, WPRegionNames.EMOTES_BLUSH));//todo set new texture to be WPRegionNames.Blocks[randomInt() in bounds]
//        }
    }


    private Entity makeLantern(float x, float y,java.lang.Class componentType, TextureRegion[] textureRegions) {
        TextureComponent texture = engine.createComponent(TextureComponent.class);
//        texture.setRegion(labAtlas.findRegion(regionName));

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
        bounds.setBounds(x-dimension.getWidth()/2,y-dimension.getHeight()/2,dimension.getWidth(),dimension.getHeight());
        entity.add(bounds);

        WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class);
        worldWrap.setBoundsOfMovement(MapMaker.getMapBounds());
        entity.add(worldWrap);

        BlockComponent blockComponent = engine.createComponent(BlockComponent.class);
        entity.add(blockComponent);

        VelocityComponent velocityComponent = engine.createComponent(VelocityComponent.class);
        velocityComponent.setDirection(Direction.generateRandomDirection());
        entity.add(velocityComponent);

        AutoMovementTag autoMovementTag = engine.createComponent(AutoMovementTag.class);
        entity.add(autoMovementTag);

        MovementLimitationComponent movementLimitationComponent = engine.createComponent(MovementLimitationComponent.class);
        entity.add(movementLimitationComponent);

        PositionTrackerComponent positionTrackerComponent = engine.createComponent(PositionTrackerComponent.class);
        positionTrackerComponent.setPositionKeyListMap(lanternsKeyListMap);
        entity.add(positionTrackerComponent);


        //color

        MonoColorRenderTag monoColorRenderTag = engine.createComponent(MonoColorRenderTag.class);
        entity.add(monoColorRenderTag);


        SpriteAnimationComponent spriteAnimationComponent = engine.createComponent(SpriteAnimationComponent.class);
        //this is based on my spreadsheet
        spriteAnimationComponent.init(textureRegions[0],textureRegions[1],textureRegions[2],textureRegions[3],8,5);
        entity.add(spriteAnimationComponent);

        ColorComponent colorComponent = engine.createComponent(ColorComponent.class);
        colorComponent.setColor(Color.WHITE);
        entity.add(colorComponent);

        //explosive
        ExplosiveComponent explosiveComponent = engine.createComponent(ExplosiveComponent.class);
        entity.add(explosiveComponent);

        return entity;
    }
}
