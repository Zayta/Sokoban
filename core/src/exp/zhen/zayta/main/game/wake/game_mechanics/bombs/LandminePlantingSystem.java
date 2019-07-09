package exp.zhen.zayta.main.game.wake.game_mechanics.bombs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.RPG;
import exp.zhen.zayta.main.game.wake.assets.WPRegionNames;
import exp.zhen.zayta.main.game.wake.entity.EntityLab;
import exp.zhen.zayta.main.game.wake.entity.components.properties.explosion.ExplosiveComponent;
import exp.zhen.zayta.main.game.wake.entity.components.properties.explosion.ExplosiveHolderComponent;
import exp.zhen.zayta.main.game.wake.game_mechanics.template_for_collision_system.CollisionSystemTemplate;
import exp.zhen.zayta.main.game.wake.movement.component.Position;
import exp.zhen.zayta.main.game.wake.render.GameRenderSystem;
import exp.zhen.zayta.main.game.wake.visual.TextureComponent;

public class LandminePlantingSystem extends EntitySystem {

    private static final Logger log = new Logger(CollisionSystemTemplate.class.getName(),Logger.DEBUG);
    //families are entities that can collide
    private final Family FAMILY;

    private PooledEngine engine;
    private TextureAtlas wakePlayAtlas;

    public LandminePlantingSystem(PooledEngine engine, TextureAtlas wakePlayAtlas){
        FAMILY = Family.all(
                ExplosiveHolderComponent.class,
                Position.class
        ).get();
        this.engine = engine;
        this.wakePlayAtlas = wakePlayAtlas;
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> landMiners = getEngine().getEntitiesFor(FAMILY);

        for(Entity landMiner: landMiners) {
            Position position = landMiner.getComponent(Position.class);
            ExplosiveHolderComponent landMinerStrength = landMiner.getComponent(ExplosiveHolderComponent.class);

            Entity landmine = engine.createEntity();
            /*Landmine basic components*/
            ExplosiveComponent explosiveComponent = engine.createComponent(ExplosiveComponent.class);
            TextureComponent landMineImg = engine.createComponent(TextureComponent.class);
            landMineImg.setRegion(wakePlayAtlas.findRegion(WPRegionNames.MAPS_MEMLAB1));//todo change texture region
            //todo rn landmine is equal to atk of landmine planter
            ExplosiveComponent landMinePower = engine.createComponent(ExplosiveComponent.class);
            landMinePower.setPower(landMinerStrength.getCharge());

            EntityLab.addPositionComponents(engine,landmine,position.getX(),position.getY());
            landmine.add(explosiveComponent);
            landmine.add(landMinePower);
            engine.addEntity(landmine);
        }
    }




}
