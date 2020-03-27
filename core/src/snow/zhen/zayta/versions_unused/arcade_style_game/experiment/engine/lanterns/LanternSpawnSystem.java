package snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.lanterns;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import snow.zhen.zayta.main.assets.RegionNames;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.PositionTracker;
import snow.zhen.zayta.util.KeyListMap;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.Experiment;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.EntityLab;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.util.Arrangements;

public class LanternSpawnSystem extends IntervalSystem {
    private snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.EntityLab entityLab;
    private Vector2 spawnPoint;
    private KeyListMap<Integer,Entity> lanternsKeyListMap;
    private TextureRegion[] textureRegions;
    public LanternSpawnSystem(Experiment experiment, EntityLab entityLab, PooledEngine engine, TextureAtlas labAtlas, float interval) {
        super(interval);
        this.entityLab = entityLab;
        textureRegions = new TextureRegion[4];
        //init textureRegions
        String [] textureRegionNames = {RegionNames.FIRE_BLOB_BACK,RegionNames.FIRE_BLOB_FRONT,RegionNames.FIRE_BLOB_LEFT,RegionNames.FIRE_BLOB_RIGHT};
        for(int i = 0; i<textureRegionNames.length;i++){
            textureRegions[i] = labAtlas.findRegion(textureRegionNames[i]);
        }

        lanternsKeyListMap = new KeyListMap<Integer, Entity>();
        spawnPoint = Arrangements.generateRandomUCoordinates(1)[0];
        engine.addSystem(new LanternFlareSystem(engine,labAtlas,lanternsKeyListMap));
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
