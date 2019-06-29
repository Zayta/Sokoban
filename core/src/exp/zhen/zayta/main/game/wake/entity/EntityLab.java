package exp.zhen.zayta.main.game.wake.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;

import exp.zhen.zayta.UserData;
import exp.zhen.zayta.main.UIAssetDescriptors;
import exp.zhen.zayta.main.game.characters.Undead;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.config.SpeedManager;
import exp.zhen.zayta.main.game.wake.assets.WPRegionNames;
import exp.zhen.zayta.main.game.wake.common.Mappers;
import exp.zhen.zayta.main.game.wake.entity.components.labels.NPCTag;
import exp.zhen.zayta.main.game.wake.entity.components.labels.PlayerTag;
import exp.zhen.zayta.main.game.wake.entity.components.labels.id_tags.MortalTag;
import exp.zhen.zayta.main.game.wake.entity.nur.NUR;
import exp.zhen.zayta.main.game.wake.entity.utsubyo.Utsubyo;
import exp.zhen.zayta.main.game.wake.movement.Direction;
import exp.zhen.zayta.main.game.wake.movement.PositionTracker;
import exp.zhen.zayta.main.game.wake.movement.component.CircularBoundsComponent;
import exp.zhen.zayta.main.game.wake.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.wake.movement.component.Position;
import exp.zhen.zayta.main.game.wake.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.wake.movement.component.VelocityComponent;
import exp.zhen.zayta.main.game.wake.movement.component.WorldWrapTag;
import exp.zhen.zayta.main.game.wake.visual.AnimationComponent;
import exp.zhen.zayta.main.game.wake.visual.TextureComponent;

public class EntityLab {
    private PooledEngine engine;
    private TextureAtlas wakePlayAtlas;
    private NUR nur; private Utsubyo utsubyo;
    public EntityLab(PooledEngine engine, AssetManager assetManager)
    {
        this.engine = engine;
        wakePlayAtlas = assetManager.get(UIAssetDescriptors.WAKE_PLAY);
        nur = new NUR(engine,wakePlayAtlas); utsubyo = new Utsubyo(engine,wakePlayAtlas);
    }

    public void addEntities() {
//        initCivilization();
        initMonsters();
        addPlayer();
    }

    /**For player**/
    private void addPlayer(){
        /*add NighterPool*/
        float playerStartX = (SizeManager.WAKE_WORLD_WIDTH - SizeManager.maxObjWidth)/2;
        float playerStartY = 1-SizeManager.maxObjHeight/2;

        Entity consciousNighter = nur.getNighter(Undead.Lorale);

        PlayerTag playerTag = engine.createComponent(PlayerTag.class);
        consciousNighter.add(playerTag);
        addMovementComponents(engine, consciousNighter,playerStartX,playerStartY,PositionTracker.PositionBiMap.nightersBiMap);
        engine.addEntity(consciousNighter);


        UserData.Player = consciousNighter;
    }




    /**For monsters**/
    private void initMonsters(){
        //todo also in future make civilians change direction randomly
        /*add Monsters*/
        int numMonsters = 1;
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
        addMovementComponents(engine,entity,x,y,PositionTracker.PositionBiMap.monstersBiMap);
        engine.addEntity(entity);

        Mappers.MOVEMENT.get(entity).setDirection(Direction.generateRandomDirection());
    }


    /**For society**/
    private void initCivilization(){
        //todo also in future make civilians change direction randomly
        /*add Civilization*/
        int numCivilians = 5;
        float minX = 0; float maxX = SizeManager.WAKE_WORLD_WIDTH-SizeManager.maxObjWidth;
        float minY = 0; float maxY = SizeManager.WAKE_WORLD_HEIGHT-SizeManager.maxObjHeight;
        for(int i = 0; i<numCivilians; i++) {
            float civX = MathUtils.random(minX,maxX);
            float civY = MathUtils.random(minY,maxY);
            addCivilian(civX, civY);
        }

    }
    private void addCivilian(float x, float y){
        NPCTag npcTag = engine.createComponent(NPCTag.class);

        MortalTag mortalTag = engine.createComponent(MortalTag.class);

        TextureComponent texture = engine.createComponent(TextureComponent.class);

        AnimationComponent animationComponent = engine.createComponent(AnimationComponent.class);
        animationComponent.init(wakePlayAtlas.findRegion(WPRegionNames.CIVILIAN));


        Entity entity = engine.createEntity();
        addMovementComponents(engine,entity,x,y,PositionTracker.PositionBiMap.civiliansBiMap);
        entity.add(npcTag);
        entity.add(mortalTag);
        entity.add(texture);
        entity.add(animationComponent);
        engine.addEntity(entity);

        Mappers.MOVEMENT.get(entity).setDirection(Direction.generateRandomDirection());
    }





    public void addMovementComponents(PooledEngine engine, Entity entity, float x, float y, PositionTracker.PositionBiMap posMap){
        addPositionComponents(engine,entity,x,y);

        PositionTrackerComponent positionTrackerComponent = engine.createComponent(PositionTrackerComponent.class);
        positionTrackerComponent.setPositionBiMap(posMap);
        entity.add(positionTrackerComponent);

        VelocityComponent movement = engine.createComponent(VelocityComponent.class);
        movement.setSpeed(SpeedManager.DEFAULT_SPEED,SpeedManager.DEFAULT_SPEED);
        entity.add(movement);

    }


    public void addPositionComponents(PooledEngine engine, Entity entity, float x, float y){
        Position position = engine.createComponent(Position.class);
        position.set(x,y);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.set(SizeManager.maxObjWidth,SizeManager.maxObjHeight);

        CircularBoundsComponent bounds = engine.createComponent(CircularBoundsComponent.class);
        bounds.setBounds(x,y-dimension.getHeight()/2,SizeManager.maxBoundsRadius);

        WorldWrapTag worldWrap = engine.createComponent(WorldWrapTag.class);

        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(worldWrap);
    }


}
