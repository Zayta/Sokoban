package exp.zhen.zayta.main.game.experiment.engine.lanterns;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import exp.zhen.zayta.main.assets.RegionNames;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.experiment.Experiment;
import exp.zhen.zayta.main.game.experiment.engine.blocks.BlockComponent;
import exp.zhen.zayta.main.game.experiment.engine.entity.EntityLab;
import exp.zhen.zayta.main.game.experiment.engine.entity.components.properties.ColorComponent;
import exp.zhen.zayta.main.game.experiment.engine.entity.components.properties.explosion.ExplosiveComponent;
import exp.zhen.zayta.main.game.experiment.engine.map.MapMaker;
import exp.zhen.zayta.main.game.experiment.engine.map.util.Arrangements;
import exp.zhen.zayta.main.game.experiment.engine.movement.Direction;
import exp.zhen.zayta.main.game.experiment.engine.movement.PositionTracker;
import exp.zhen.zayta.main.game.experiment.engine.movement.component.AutoMovementTag;
import exp.zhen.zayta.main.game.experiment.engine.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.experiment.engine.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.main.game.experiment.engine.movement.component.Position;
import exp.zhen.zayta.main.game.experiment.engine.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.experiment.engine.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.experiment.engine.movement.component.VelocityComponent;
import exp.zhen.zayta.main.game.experiment.engine.movement.component.WorldWrapComponent;
import exp.zhen.zayta.main.game.experiment.engine.render.animation.TextureComponent;
import exp.zhen.zayta.main.game.experiment.engine.render.animation.sprite.SpriteAnimationComponent;
import exp.zhen.zayta.main.game.experiment.engine.render.mono_color.MonoColorRenderTag;
import exp.zhen.zayta.util.KeyListMap;

public class LanternSpawnSystem extends IntervalSystem {

    private PooledEngine engine;
    private TextureAtlas labAtlas;
    private EntityLab entityLab;
    private Vector2 spawnPoint;
    private KeyListMap<Integer,Entity> lanternsKeyListMap;
    private TextureRegion[] textureRegions;
    public LanternSpawnSystem(Experiment experiment, EntityLab entityLab, PooledEngine engine, TextureAtlas labAtlas, float interval) {
        super(interval);
        this.engine = engine;
        this.entityLab = entityLab;
        this.labAtlas = labAtlas;
        textureRegions = new TextureRegion[4];
        //init textureRegions
        String [] textureRegionNames = {RegionNames.FIRE_BLOB_BACK,RegionNames.FIRE_BLOB_FRONT,RegionNames.FIRE_BLOB_LEFT,RegionNames.FIRE_BLOB_RIGHT};
        for(int i = 0; i<textureRegionNames.length;i++){
            textureRegions[i] = labAtlas.findRegion(textureRegionNames[i]);
        }

        lanternsKeyListMap = new KeyListMap<Integer, Entity>();
        spawnPoint = Arrangements.generateRandomUCoordinates(1)[0];
        engine.addSystem(new LanternSystem(engine,lanternsKeyListMap));
        engine.addSystem(new LanternCheckFlareSystem(experiment,engine));

        //spawns three initially
        for(int i =0; i<3; i++){
            spawn();
        }
    }

    @Override
    protected void updateInterval() {
        spawn();
    }

    private void spawn(){
        //to generate from certain spot, take out randomeness
        int key = PositionTracker.generateKey(spawnPoint.x, spawnPoint.y);
        lanternsKeyListMap.put(key, entityLab.makeLantern(spawnPoint.x, spawnPoint.y,
                textureRegions,lanternsKeyListMap));//todo set new texture to be RegionNames.Blocks[randomInt() in bounds]
//        Vector2[] points = Arrangements.generateRandomUCoordinates(1);
//        for(Vector2 point:points) {
//            int key = PositionTracker.generateKey(point.x, point.y);
//            lanternsKeyListMap.put(key, makeLantern(point.x, point.y, MovingBlockTag.class, RegionNames.EMOTES_BLUSH));//todo set new texture to be RegionNames.Blocks[randomInt() in bounds]
//        }
    }

}
