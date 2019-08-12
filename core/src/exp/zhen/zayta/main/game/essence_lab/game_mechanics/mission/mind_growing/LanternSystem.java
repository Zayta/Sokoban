package exp.zhen.zayta.main.game.essence_lab.game_mechanics.mission.mind_growing;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;

import java.util.Arrays;
import java.util.PriorityQueue;

import exp.zhen.zayta.RPG;
import exp.zhen.zayta.main.UIAssetDescriptors;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.essence_lab.assets.WPRegionNames;
import exp.zhen.zayta.main.game.essence_lab.blocks.BlockComponent;
import exp.zhen.zayta.main.game.essence_lab.blocks.MovingBlockTag;
import exp.zhen.zayta.main.game.essence_lab.common.Mappers;
import exp.zhen.zayta.main.game.essence_lab.entity.components.labels.NPCTag;
import exp.zhen.zayta.main.game.essence_lab.entity.components.properties.ColorComponent;
import exp.zhen.zayta.main.game.essence_lab.entity.id_tags.NighterTag;
import exp.zhen.zayta.main.game.essence_lab.entity.components.properties.AttackComponent;
import exp.zhen.zayta.main.game.essence_lab.entity.components.properties.HealthComponent;
import exp.zhen.zayta.main.game.essence_lab.game_mechanics.GameControllingSystem;
import exp.zhen.zayta.main.game.essence_lab.map.MapMaker;
import exp.zhen.zayta.main.game.essence_lab.map.util.Arrangements;
import exp.zhen.zayta.main.game.essence_lab.movement.Direction;
import exp.zhen.zayta.main.game.essence_lab.movement.PositionComparator;
import exp.zhen.zayta.main.game.essence_lab.movement.PositionTracker;
import exp.zhen.zayta.main.game.essence_lab.movement.component.AutoMovementTag;
import exp.zhen.zayta.main.game.essence_lab.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.Position;
import exp.zhen.zayta.main.game.essence_lab.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.VelocityComponent;
import exp.zhen.zayta.main.game.essence_lab.movement.component.WorldWrapComponent;
import exp.zhen.zayta.main.game.essence_lab.render.animation.TextureComponent;
import exp.zhen.zayta.main.game.essence_lab.render.mono_color.MonoColorRenderTag;
import exp.zhen.zayta.util.GdxUtils;
import exp.zhen.zayta.util.KeyListMap;

public class LanternSystem extends GameControllingSystem implements Pool.Poolable {

    //todo later add in wielder x mortal in this same class and rename class to undead x mortal collision system
    private static final Logger log = new Logger(LanternSystem.class.getName(),Logger.DEBUG);
    //families are entities that can collide
    private PooledEngine engine;

    private final KeyListMap<Integer,Entity> lanternsKeyListMap;

    private final Family NIGHTERS = Family.all(
            ColorComponent.class,
            PositionTrackerComponent.class,
            RectangularBoundsComponent.class
    ).one(NPCTag.class,NighterTag.class).get();

    private KeyListMap<Entity,Entity> currentFighters;


    LanternSystem(RPG game, PooledEngine engine, KeyListMap<Integer,Entity> lanternsKeyListMap)
    {
        super(game,engine);
        addMission();
        this.engine = engine;
        this.lanternsKeyListMap= lanternsKeyListMap;
        currentFighters = new KeyListMap<Entity, Entity>();
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> nighters = getEngine().getEntitiesFor(NIGHTERS);

        for(Entity nighter: nighters) {
            int key = Mappers.POSITION_TRACKER.get(nighter).getPositionKeyListMap().getKey(nighter);
            int keyAbove = key+PositionTracker.n;
            int keyBelow = key-PositionTracker.n;
            int [] keys = {keyAbove-1,keyAbove,keyAbove+1,
                    key-1, key, key+1,
                    keyBelow-1, keyBelow, keyBelow+1};
            checkCollision(nighter,keys);
        }
    }

    private void checkCollision(Entity nighter, int [] keys){
        for (int key: keys) {
            Entity lantern = lanternsKeyListMap.get(key);

            if (lantern != null) {
                if (checkCollisionBetween(nighter, lantern)) {
                    if(collisionUnhandled(nighter,lantern)) {
                        collideEvent(nighter, lantern);
//                        currentFighters.put(nighter,lantern);
                        currentFighters.put(lantern,nighter);
                        break;
                    }
                }
                else{
                    updateCurrentBattles(nighter,lantern);
                }
            }
        }
    }
    private boolean checkCollisionBetween(Entity nighter, Entity lantern)
    {
        RectangularBoundsComponent playerBounds = Mappers.RECTANGULAR_BOUNDS.get(nighter);
        RectangularBoundsComponent obstacleBounds = Mappers.RECTANGULAR_BOUNDS.get(lantern);

        return Intersector.overlaps(playerBounds.getBounds(),obstacleBounds.getBounds());
    }

    private boolean collisionUnhandled(Entity nighter, Entity lantern){
        return !(currentFighters.get(nighter) == lantern) && !(currentFighters.get(lantern) == nighter);
    }

    private void collideEvent(Entity nighter, Entity lantern) {
        //sets lantern color to nighter color
        log.debug("Lantern System Collide Event");
        Mappers.COLOR.get(lantern).setColor(Mappers.COLOR.get(nighter).getColor());

    }
    private void updateCurrentBattles(Entity nighter,Entity lantern){
        currentFighters.removeKey(nighter);
        currentFighters.removeKey(lantern);
    }

    @Override
    public void reset() {
        currentFighters.clear();
    }

}
