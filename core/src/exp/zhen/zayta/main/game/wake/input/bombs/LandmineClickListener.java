package exp.zhen.zayta.main.game.wake.input.bombs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.game.wake.assets.WPRegionNames;
import exp.zhen.zayta.main.game.wake.entity.EntityLab;
import exp.zhen.zayta.main.game.wake.entity.components.labels.PlayerTag;
import exp.zhen.zayta.main.game.wake.entity.components.properties.explosion.ExplosiveComponent;
import exp.zhen.zayta.main.game.wake.entity.components.properties.explosion.ExplosiveHolderComponent;
import exp.zhen.zayta.main.game.wake.game_mechanics.collision_mechanics.template_for_collision_system.CollisionSystemTemplate;
import exp.zhen.zayta.main.game.wake.movement.component.Position;
import exp.zhen.zayta.main.game.wake.render.animation.TextureComponent;
import exp.zhen.zayta.main.game.wake.render.animation.particle.ParticleAnimationComponent;

public class LandmineClickListener extends ClickListener {

    private static final Logger log = new Logger(CollisionSystemTemplate.class.getName(),Logger.DEBUG);
    //families are entities that can collide
    private final Family LANDMINERS;

    private PooledEngine engine;
    private TextureAtlas wakePlayAtlas;

    public LandmineClickListener(PooledEngine engine, TextureAtlas wakePlayAtlas){
        LANDMINERS = Family.all(
                PlayerTag.class,
                ExplosiveHolderComponent.class,
                Position.class
        ).get();
        this.engine = engine;
        this.wakePlayAtlas = wakePlayAtlas;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        plantLandMine();
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

            engine.addEntity(landmine);
        }
    }




}
