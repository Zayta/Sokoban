package snow.zhen.zayta.versions_unused.arcade_style_game.characters.nur;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Logger;

import snow.zhen.zayta.main.assets.RegionNames;
import snow.zhen.zayta.util.KeyListMap;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.entity.Stats;

public class NUR {
  private static final Logger log = new Logger(NUR.class.getName(),Logger.DEBUG);
    private TextureAtlas labAtlas;
    public KeyListMap <snow.zhen.zayta.versions_unused.arcade_style_game.characters.nur.Undead, Stats> nighters;


    public NUR(TextureAtlas labAtlas){
        this.labAtlas = labAtlas;
        nighters = new KeyListMap<snow.zhen.zayta.versions_unused.arcade_style_game.characters.nur.Undead, Stats>();
//        initNighters();
    }
    
    private void initNighters(){
        nighters.put(snow.zhen.zayta.versions_unused.arcade_style_game.characters.nur.Undead.Tenyu, new Stats("Tenyu",
                labAtlas.findRegion(RegionNames.TENYU),
                100,10,10));
        nighters.put(snow.zhen.zayta.versions_unused.arcade_style_game.characters.nur.Undead.Lorale, new Stats("Lorale",
                labAtlas.findRegion(RegionNames.LORALE),
                100,10,10));
        nighters.put(snow.zhen.zayta.versions_unused.arcade_style_game.characters.nur.Undead.Letra, new Stats("Letra",
                labAtlas.findRegion(RegionNames.LETRA),
                100,10,10));


        nighters.put(Undead.Anonymous, new Stats("???",
                labAtlas.findRegion(RegionNames.CIVILIAN),
                0,0,0));

    }





    
    
    
    
    
    
    
    
    
    
    
    


}
