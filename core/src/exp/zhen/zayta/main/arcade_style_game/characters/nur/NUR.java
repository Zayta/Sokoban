package exp.zhen.zayta.main.arcade_style_game.characters.nur;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Logger;

import exp.zhen.zayta.main.assets.RegionNames;
import exp.zhen.zayta.main.arcade_style_game.experiment.engine.entity.Stats;
import exp.zhen.zayta.util.KeyListMap;

public class NUR {
  private static final Logger log = new Logger(NUR.class.getName(),Logger.DEBUG);
    private TextureAtlas labAtlas;
    public KeyListMap <Undead, Stats> nighters;


    public NUR(TextureAtlas labAtlas){
        this.labAtlas = labAtlas;
        nighters = new KeyListMap<Undead, Stats>();
        initNighters();
    }
    
    private void initNighters(){
        nighters.put(Undead.Tenyu, new Stats("Tenyu",
                labAtlas.findRegion(RegionNames.TENYU),
                100,10,10));
        nighters.put(Undead.Lorale, new Stats("Lorale",
                labAtlas.findRegion(RegionNames.LORALE),
                100,10,10));
        nighters.put(Undead.Letra, new Stats("Letra",
                labAtlas.findRegion(RegionNames.LETRA),
                100,10,10));


        nighters.put(Undead.Anonymous, new Stats("???",
                labAtlas.findRegion(RegionNames.CIVILIAN),
                0,0,0));

    }





    
    
    
    
    
    
    
    
    
    
    
    


}
