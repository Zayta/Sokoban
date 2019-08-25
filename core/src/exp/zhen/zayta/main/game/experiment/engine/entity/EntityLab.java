package exp.zhen.zayta.main.game.experiment.engine.entity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.assets.AssetDescriptors;
import exp.zhen.zayta.main.game.characters.Undead;
import exp.zhen.zayta.main.game.config.SizeManager;
import exp.zhen.zayta.main.game.config.SpeedManager;
import exp.zhen.zayta.main.game.experiment.common.Mappers;
import exp.zhen.zayta.main.game.experiment.engine.blocks.BlockComponent;
import exp.zhen.zayta.main.game.experiment.engine.entity.components.NameTag;
import exp.zhen.zayta.main.game.experiment.engine.entity.components.labels.PlayerTag;
import exp.zhen.zayta.main.game.experiment.engine.entity.components.labels.UndeadTag;
import exp.zhen.zayta.main.game.experiment.engine.entity.components.properties.AttackComponent;
import exp.zhen.zayta.main.game.experiment.engine.entity.components.properties.ColorComponent;
import exp.zhen.zayta.main.game.experiment.engine.entity.components.properties.DefenseComponent;
import exp.zhen.zayta.main.game.experiment.engine.entity.components.properties.HealthComponent;
import exp.zhen.zayta.main.game.experiment.engine.entity.components.properties.explosion.ExplosiveComponent;
import exp.zhen.zayta.main.game.experiment.engine.entity.id_tags.NighterTag;
import exp.zhen.zayta.main.game.characters.nur.NUR;
import exp.zhen.zayta.main.game.experiment.engine.entity.utsubyo.Utsubyo;
import exp.zhen.zayta.main.game.experiment.engine.lanterns.LanternTag;
import exp.zhen.zayta.main.game.experiment.engine.render.mono_color.MonoColorRenderTag;
import exp.zhen.zayta.main.game.movable_items.components.PushComponent;
import exp.zhen.zayta.main.game.experiment.engine.map.MapMaker;
import exp.zhen.zayta.main.game.experiment.engine.map.util.Arrangements;
import exp.zhen.zayta.main.game.experiment.engine.movement.Direction;
import exp.zhen.zayta.main.game.experiment.engine.movement.PositionTracker;
import exp.zhen.zayta.main.game.experiment.engine.movement.component.AutoMovementTag;
import exp.zhen.zayta.main.game.experiment.engine.movement.component.CircularBoundsComponent;
import exp.zhen.zayta.main.game.experiment.engine.movement.component.MovementLimitationComponent;
import exp.zhen.zayta.main.game.experiment.engine.movement.component.RectangularBoundsComponent;
import exp.zhen.zayta.main.game.experiment.engine.movement.component.DimensionComponent;
import exp.zhen.zayta.main.game.experiment.engine.movement.component.Position;
import exp.zhen.zayta.main.game.experiment.engine.movement.component.PositionTrackerComponent;
import exp.zhen.zayta.main.game.experiment.engine.movement.component.VelocityComponent;
import exp.zhen.zayta.main.game.experiment.engine.movement.component.WorldWrapComponent;
import exp.zhen.zayta.main.game.experiment.engine.render.animation.sprite.SpriteAnimationComponent;
import exp.zhen.zayta.main.game.experiment.engine.render.animation.TextureComponent;
import exp.zhen.zayta.util.BiMap;
import exp.zhen.zayta.util.KeyListMap;

public class EntityLab {

    private static final Logger log = new Logger(EntityLab.class.getName(),Logger.DEBUG);
    private PooledEngine engine;
    private TextureAtlas labAtlas;
    private NUR nur; private Utsubyo utsubyo;

    public EntityLab(NUR nur, PooledEngine engine, AssetManager assetManager)
    {
        this.engine = engine;
        labAtlas = assetManager.get(AssetDescriptors.LAB);
        this.nur = nur; utsubyo = new Utsubyo(engine,labAtlas);
    }

    public void addEntities(int numNighters,int numMonsters) {
        /*add Nighters*/

        Vector2[] points = Arrangements.generateRandomUCoordinates(numNighters);
        for(int i = 0; i<points.length;i++) {
            addNighter(Undead.values()[i],points[i]);
        }

        addMonsters(numMonsters);
    }

    /**For player**/
    private void addNighter(Undead undead,Vector2 pos){
        /*add NighterPool*/
//        Vector2[] points = Arrangements.generateRandomUCoordinates(numNighters);

//        for(int i = 0; i<points.length;i++) {
            Entity nighter = engine.createEntity();


            Stats stats = nur.nighters.get(undead);
//        ////log.debug("Stats is "+stats);
            addIdentityComponents(nighter, stats.getName());
            addAnimationComponents(nighter, stats.getTextureRegion());
            addBattleComponents(nighter, stats.getHp(), stats.getAtk(), stats.getDef());
            addColor(nighter, undead);

            PlayerTag playerTag = engine.createComponent(PlayerTag.class);
            nighter.add(playerTag);
            addMovementComponents(engine, nighter, pos.x, pos.y, PositionTracker.PositionKeyListMap.nightersKeyListMap.getKeyListMap());
            PushComponent pocketComponent = engine.createComponent(PushComponent.class);
            nighter.add(pocketComponent);
            engine.addEntity(nighter);
//        }
    }
    /**Components of a Nighter**/
    private void addIdentityComponents(Entity nighter,String name){
        UndeadTag undeadTag = engine.createComponent(UndeadTag.class);
        NighterTag nighterTag = engine.createComponent(NighterTag.class);
        NameTag nameTag = engine.createComponent(NameTag.class);
        nameTag.setName(name);

        nighter.add(undeadTag);
        nighter.add(nighterTag);
        nighter.add(nameTag);
    }
    private void addAnimationComponents(Entity nighter,TextureRegion textureRegion){
        TextureComponent texture = engine.createComponent(TextureComponent.class);

        SpriteAnimationComponent spriteAnimationComponent = engine.createComponent(SpriteAnimationComponent.class);
        spriteAnimationComponent.init(textureRegion);

        nighter.add(texture);
        nighter.add(spriteAnimationComponent);
    }


    private void addBattleComponents(Entity nighter, int hp, int atk, int def){
        addHealthComponent(nighter,hp);

        AttackComponent attackComponent = engine.createComponent(AttackComponent.class);
        attackComponent.init(atk);

        DefenseComponent defenseComponent = engine.createComponent(DefenseComponent.class);
        defenseComponent.init(def);

        nighter.add(attackComponent);
        nighter.add(defenseComponent);
    }

    private void addHealthComponent(Entity nighter,int hp){
        HealthComponent healthComponent = engine.createComponent(HealthComponent.class);
        healthComponent.init(hp);
        nighter.add(healthComponent);
    }

    private void addColor(Entity entity, Undead undead){
        ColorComponent colorComponent = engine.createComponent(ColorComponent.class);
        switch (undead){
            case Ruzo:
                break;
            case Tenyu:
                colorComponent.setColor(Color.SKY);
                break;
            case Lorale:
                colorComponent.setColor(Color.ORANGE);
                break;
            case Letra:
                colorComponent.setColor(Color.FIREBRICK);
                break;
            case Taria:
                colorComponent.setColor(Color.PURPLE);
                break;
            case Cumin:
                break;
            case Kira:
                break;
            case Foofi:
                break;

        }
        entity.add(colorComponent);
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
        addMovementComponents(engine,entity,x,y,PositionTracker.PositionKeyListMap.monstersKeyListMap.getKeyListMap());
        PushComponent pocketComponent = engine.createComponent(PushComponent.class);
        entity.add(pocketComponent);


        AutoMovementTag autoMovementTag = engine.createComponent(AutoMovementTag.class);
        entity.add(autoMovementTag);

        engine.addEntity(entity);

        Mappers.MOVEMENT.get(entity).setDirection(Direction.generateRandomDirection());
    }






    /*For Lanterns*/

    public Entity makeLantern(float x, float y, TextureRegion[] textureRegions, KeyListMap<Integer,Entity> posMap) {
        TextureComponent texture = engine.createComponent(TextureComponent.class);
//        texture.setRegion(labAtlas.findRegion(regionName));

        Entity entity = engine.createEntity();
        LanternTag lanternTag = engine.createComponent(LanternTag.class);
        lanternTag.setState(LanternTag.State.DORMANT);

        entity.add(lanternTag);//adds identifier
        entity.add(texture);
        engine.addEntity(entity);

        addMovementComponents(engine,entity,x,y,posMap);



        BlockComponent blockComponent = engine.createComponent(BlockComponent.class);
        entity.add(blockComponent);


        AutoMovementTag autoMovementTag = engine.createComponent(AutoMovementTag.class);
        entity.add(autoMovementTag);




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





    private void addMovementComponents(PooledEngine engine, Entity entity, float x, float y, KeyListMap<Integer, Entity> posMap){
        addPositionComponents(engine,entity,x,y,posMap);

        VelocityComponent movement = engine.createComponent(VelocityComponent.class);
        movement.setSpeed(SpeedManager.DEFAULT_SPEED,SpeedManager.DEFAULT_SPEED);
        entity.add(movement);

        MovementLimitationComponent movementLimitationComponent = engine.createComponent(MovementLimitationComponent.class);
        entity.add(movementLimitationComponent);

        WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class); worldWrap.setBoundsOfMovement(MapMaker.getMapBounds());
        entity.add(worldWrap);

        //todo for ghostification remove movementLimitationComponent and block component
//        BlockComponent blockComponent = engine.createComponent(BlockComponent.class);
//        entity.add(blockComponent);//adding block component to entity causes lag cuz of the setPosition taht is used with the blocks.
    }

    public static void addRoundPositionComponents(PooledEngine engine,Entity entity,float x, float y, KeyListMap<Integer,Entity> posMap){

        CircularBoundsComponent bounds = engine.createComponent(CircularBoundsComponent.class);
        entity.add(bounds);
        addPositionComponents(engine,entity,x,y,posMap);
    }

     private static void addPositionComponents(PooledEngine engine, Entity entity, float x, float y, KeyListMap<Integer, Entity> posMap){
        Position position = engine.createComponent(Position.class);
        position.set(x,y);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.set(SizeManager.maxObjWidth,SizeManager.maxObjHeight);

        RectangularBoundsComponent bounds = engine.createComponent(RectangularBoundsComponent.class);
        bounds.setBounds(x,y-dimension.getHeight()/2,dimension.getWidth(),dimension.getHeight());

         PositionTrackerComponent positionTrackerComponent = engine.createComponent(PositionTrackerComponent.class);
         positionTrackerComponent.setPositionKeyListMap(posMap);
         entity.add(positionTrackerComponent);

        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
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
//        spriteAnimationComponent.init(labAtlas.findRegion(RegionNames.CIVILIAN));
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
