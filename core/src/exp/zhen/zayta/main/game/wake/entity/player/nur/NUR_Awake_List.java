package exp.zhen.zayta.main.game.wake.entity.player.nur;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;

import exp.zhen.zayta.main.game.wake.movement.PositionTracker;
import exp.zhen.zayta.main.game.wake.entity.MovingEntityMaker;
import exp.zhen.zayta.UserData;
import exp.zhen.zayta.assets.RegionNames;
import exp.zhen.zayta.main.game.wake.component.labels.PlayerTag;
import exp.zhen.zayta.main.game.wake.entity.EntityPositioner;
import exp.zhen.zayta.main.game.characters.Undead;

public class NUR_Awake_List extends MovingEntityMaker implements EntityPositioner {
    private TextureAtlas wakePlayAtlas;
    private PooledEngine engine;
    public NUR_Awake_List(TextureAtlas wakePlayAtlas, PooledEngine engine){
        this.wakePlayAtlas = wakePlayAtlas;
        this.engine = engine;
        init();
    }
    private void init()
    {
        initConsciousNighters();
    }
    private void initConsciousNighters()
    {
        consciousNighters.put(Undead.Lorale,
                new ConsciousNighter(engine,wakePlayAtlas.findRegion(RegionNames.WakePlay.LORALE)));

    }

    @Override
    public void addEntityInPos(float x, float y) {
        ConsciousNighter consciousNighter = consciousNighters.get(Undead.Lorale);//todo later make ability to choose ConsciousNighter
        PlayerTag playerTag = engine.createComponent(PlayerTag.class);
        consciousNighter.add(playerTag);
        addMovementComponents(engine, consciousNighter,x,y,PositionTracker.PositionBiMap.nightersBiMap);
        engine.addEntity(consciousNighter);
        setUser(consciousNighter);
    }

    public void setUser(ConsciousNighter consciousNighter){
        UserData.Player = consciousNighter;
    }


    public static HashMap<Undead,ConsciousNighter> consciousNighters = new HashMap<Undead, ConsciousNighter>();








}
