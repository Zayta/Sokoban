package exp.zhen.zayta.main.game.wake.input.move_character;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.wake.assets.WPRegionNames;
import exp.zhen.zayta.main.game.wake.entity.EntityLab;
import exp.zhen.zayta.main.game.wake.entity.components.labels.PlayerTag;
import exp.zhen.zayta.main.game.wake.entity.components.properties.explosion.ExplosiveComponent;
import exp.zhen.zayta.main.game.wake.entity.components.properties.explosion.ExplosiveHolderComponent;
import exp.zhen.zayta.main.game.wake.game_mechanics.collision_mechanics.bomb_trigger.LandmineExplosionSystem;
import exp.zhen.zayta.main.game.wake.movement.Direction;
import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.movement.PositionTracker;
import exp.zhen.zayta.main.game.wake.movement.component.Position;
import exp.zhen.zayta.main.game.wake.movement.component.VelocityComponent;
import exp.zhen.zayta.main.game.wake.render.animation.TextureComponent;
import exp.zhen.zayta.main.game.wake.render.animation.particle.ParticleAnimationComponent;

public class KeyboardController extends InputAdapter {
    private static final Logger log = new Logger(KeyboardController.class.getName(),Logger.DEBUG);

    private Direction direction=Direction.none;
    private final static Family PLAYABLE_CHARACTERS = Family.all(
            VelocityComponent.class,PlayerTag.class
    ).get();
    private Family LANDMINERS = Family.all(
            PlayerTag.class,
            ExplosiveHolderComponent .class,
            Position .class
        ).get();

    private PooledEngine engine; private TextureAtlas wakePlayAtlas;
    private ImmutableArray<Entity> entities;

    public KeyboardController(PooledEngine engine, TextureAtlas wakePlayAtlas) {
        this.engine = engine;
        this.wakePlayAtlas = wakePlayAtlas;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode==Input.Keys.LEFT){
            updateAllPlayableCharacters(Direction.left);
        }
        if(keycode==Input.Keys.RIGHT){
            updateAllPlayableCharacters(Direction.right);
        }
        if(keycode==Input.Keys.UP){
            updateAllPlayableCharacters(Direction.up);
        }
        if(keycode==Input.Keys.DOWN){
            updateAllPlayableCharacters(Direction.down);
        }
        if(keycode==Input.Keys.SPACE)
        {
            plantLandMine();

        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) { //commment this out for non-continuous movement of character
        updateAllPlayableCharacters(Direction.none);
        return true;
    }

    /*changes all playable character's directoins*/
    private void updateAllPlayableCharacters(Direction direction){
        entities = engine.getEntitiesFor(PLAYABLE_CHARACTERS);
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            VelocityComponent movement = Mappers.MOVEMENT.get(entity);
            movement.setDirection(direction);
        }
    }


    private void plantLandMine() {
        ImmutableArray<Entity> landMiners = engine.getEntitiesFor(LANDMINERS);

        for(Entity landMiner: landMiners) {
            Position position = landMiner.getComponent(Position.class);
            ExplosiveHolderComponent landMinerStrength = landMiner.getComponent(ExplosiveHolderComponent.class);

            Entity landmine = engine.createEntity();
            /*Landmine basic components*/
            ExplosiveComponent explosiveComponent = engine.createComponent(ExplosiveComponent.class);
            landmine.add(explosiveComponent);


            TextureComponent landMineImg = engine.createComponent(TextureComponent.class);
            landMineImg.setRegion(wakePlayAtlas.findRegion(WPRegionNames.PRESET_RING_FIRE));
            landmine.add(landMineImg);


//            ParticleEmitter.Particle todo check out this class from libgdx
            ParticleAnimationComponent particleAnimationComponent = engine.createComponent(ParticleAnimationComponent.class);
            particleAnimationComponent.init(landMineImg.getRegion(),8,5);
            landmine.add(particleAnimationComponent);

            ExplosiveComponent landMinePower = engine.createComponent(ExplosiveComponent.class);
            landMinePower.setPower(landMinerStrength.getCharge());
            landmine.add(landMinePower);

            EntityLab.addPositionComponents(engine,landmine,position.getX(),position.getY());

            /*put landmine in bimap*/
            Position landminePosition = Mappers.POSITION.get(landmine);
            int key = PositionTracker.generateKey(landminePosition.getX(),landminePosition.getY());
            LandmineExplosionSystem.landmineBiMap.put(key,landmine);


            engine.addEntity(landmine);
        }
    }

}
