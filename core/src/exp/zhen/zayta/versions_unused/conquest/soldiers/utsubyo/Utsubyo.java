package exp.zhen.zayta.versions_unused.conquest.soldiers.utsubyo;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;

import exp.zhen.zayta.main.game.characters.Undead;
import exp.zhen.zayta.versions_unused.conquest.assets.CQRegionNames;

public class Utsubyo {


    private static TextureAtlas conquestAtlas;
    public Utsubyo(TextureAtlas conquestAtlas){
        this.conquestAtlas = conquestAtlas;
        init();
    }
    private void init()
    {
        initMonsters();
    }
    private void initMonsters()
    {
//        nighters.put(Undead.Lorale,
//                new Stats(conquestAtlas.findRegion("portraits/Lorale"),100,10,10));

    }

    public static HashMap<Undead,Monster> monsters = new HashMap<Undead, Monster>();

    public static Monster generateMonster(int lvl){
        int index = lvl%CQRegionNames.MONSTERS.length;
            return new Monster(conquestAtlas.findRegion(CQRegionNames.MONSTERS[index]), lvl*10+10, lvl+5, lvl+2);
    }

    public TextureAtlas getConquestAtlas(){
        return conquestAtlas;
    }


}
