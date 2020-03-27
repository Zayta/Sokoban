package snow.zhen.zayta.versions_unused.conquest.soldiers.utsubyo;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;

import snow.zhen.zayta.versions_unused.arcade_style_game.characters.nur.Undead;
import snow.zhen.zayta.versions_unused.conquest.assets.CQRegionNames;

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

    public static HashMap<snow.zhen.zayta.versions_unused.arcade_style_game.characters.nur.Undead, snow.zhen.zayta.versions_unused.conquest.soldiers.utsubyo.Monster> monsters = new HashMap<Undead, snow.zhen.zayta.versions_unused.conquest.soldiers.utsubyo.Monster>();

    public static snow.zhen.zayta.versions_unused.conquest.soldiers.utsubyo.Monster generateMonster(int lvl){
        int index = lvl% snow.zhen.zayta.versions_unused.conquest.assets.CQRegionNames.MONSTERS.length;
            return new Monster(conquestAtlas.findRegion(CQRegionNames.MONSTERS[index]), lvl*10+10, lvl+5, lvl+2);
    }

    public TextureAtlas getConquestAtlas(){
        return conquestAtlas;
    }


}
