package exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.input.move_character;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.common.Mappers;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.components.labels.PlayerTag;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction;
import exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent;

public class KeyboardController extends InputAdapter {
    private static final Logger log = new Logger(KeyboardController.class.getName(),Logger.DEBUG);

    private exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction direction= exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction.none;
    private final static Family PLAYABLE_CHARACTERS = Family.all(
            exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.component.VelocityComponent.class,
            PlayerTag.class
    ).get();
//    private Family LANDMINERS = Family.all(
//            PlayerTag.class,
//            ExplosiveHolderComponent .class,
//            Position .class
//        ).get();

    private PooledEngine engine; private TextureAtlas labAtlas;
    private ImmutableArray<Entity> entities;

    public KeyboardController(PooledEngine engine, TextureAtlas labAtlas) {
        this.engine = engine;
        this.labAtlas = labAtlas;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode==Input.Keys.LEFT){
            updateAllPlayableCharacters(exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction.left);
        }
        if(keycode==Input.Keys.RIGHT){
            updateAllPlayableCharacters(exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction.right);
        }
        if(keycode==Input.Keys.UP){
            updateAllPlayableCharacters(exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction.up);
        }
        if(keycode==Input.Keys.DOWN){
            updateAllPlayableCharacters(exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction.down);
        }
//        if(keycode==Input.Keys.SPACE)
//        {
//            LandmineMaker.plantLandMine(engine,labAtlas.findRegion(RegionNames.PRESET_RING_FIRE));
//
//        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) { //commment this out for non-continuous movement of character
        updateAllPlayableCharacters(exp.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.movement.Direction.none);
        return true;
    }

    /*changes all playable character's directoins*/
    private void updateAllPlayableCharacters(Direction direction){

        entities = engine.getEntitiesFor(PLAYABLE_CHARACTERS);
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            VelocityComponent movement = Mappers.MOVEMENT.get(entity);
            if(movement.getDirection()==direction)
                continue;
            movement.setDirection(direction);
//            MovementLimitationComponent movementLimitation = Mappers.MOVEMENT_LIMITATION.get(entity);
//          if(movementLimitation!=null)
//            if(direction!=movementLimitation.getBlockedDirection()||direction==Direction.none)
//                movement.setDirection(direction);
//            else
//                movement.setDirection(Direction.none);

        }
    }


//    private void plantLandMine() {
//        ImmutableArray<Entity> landMiners = engine.getEntitiesFor(LANDMINERS);
//
//        for(Entity landMiner: landMiners) {
//            Position position = landMiner.getComponent(Position.class);
//            ExplosiveHolderComponent landMinerStrength = landMiner.getComponent(ExplosiveHolderComponent.class);
//
//            Entity landmine = engine.createEntity();
//            /*Landmine basic components*/
//            ExplosiveComponent explosiveComponent = engine.createComponent(ExplosiveComponent.class);
//            landmine.add(explosiveComponent);
//
//
//            TextureComponent landMineImg = engine.createComponent(TextureComponent.class);
//            landMineImg.setRegion(labAtlas.findRegion(RegionNames.PRESET_RING_FIRE));
//            landmine.add(landMineImg);
//
//
////            ParticleEmitter.Particle todo check out this class from libgdx
//            ParticleAnimationComponent particleAnimationComponent = engine.createComponent(ParticleAnimationComponent.class);
//            particleAnimationComponent.init(landMineImg.getRegion(),8,5);
//            landmine.add(particleAnimationComponent);
//
//            ExplosiveComponent landMinePower = engine.createComponent(ExplosiveComponent.class);
//            landMinePower.setPower(landMinerStrength.getCharge());
//            landmine.add(landMinePower);
//
//            EntityLab.addPositionComponents(engine,landmine,position.getX(),position.getY());
//
//            /*put landmine in bimap*/
//            Position landminePosition = Mappers.POSITION.get(landmine);
//            int key = PositionTracker.generateKey(landminePosition.getX(),landminePosition.getY());
//            LandmineExplosionSystem.landmineKeyListMap.put(key,landmine);
//
//
//            engine.addEntity(landmine);
//        }
//    }

}
