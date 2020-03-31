package snow.zhen.zayta.main.sokoban.entity;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Hashtable;

import snow.zhen.zayta.main.assets.RegionNames;
import snow.zhen.zayta.main.sokoban.entity.units.Crate;
import snow.zhen.zayta.main.sokoban.entity.units.Nighter;
import snow.zhen.zayta.main.sokoban.entity.units.Wall;
import snow.zhen.zayta.main.sokoban.entity.units.Goal;
import snow.zhen.zayta.util.BiMap;
import snow.zhen.zayta.main.sokoban.entity.CharacterName;
public class EntityBuilder {
    private TextureAtlas sokobanAtlas;
    public EntityBuilder(TextureAtlas sokobanAtlas){
        this.sokobanAtlas = sokobanAtlas;

        characters = new BiMap<CharacterName, Nighter>();
//        characters.put(CharacterName.TENYU, new Nighter
//                            (sokobanAtlas.findRegions(RegionNames.TENYU)));
//        characters.put(CharacterName.LETRA, new Nighter
//                (sokobanAtlas.findRegions(RegionNames.LETRA)));
        characters.put(CharacterName.LORALE, new Nighter
                (sokobanAtlas.findRegions(RegionNames.LORALE)));
//        characters.put(CharacterName.TARIA, new Nighter
//                (sokobanAtlas.findRegions(RegionNames.TARIA)));
//        characters.put(CharacterName.XIF, new Nighter
//                (sokobanAtlas.findRegions(RegionNames.XIF)));

    }
    public BiMap<CharacterName, Nighter> characters;

    public Nighter getCharacter(CharacterName characterName, float x, float y){
        Nighter nighter = characters.get(characterName);
        nighter.initPos(x,y);
        return nighter;
    }

    public Goal buildGoal(float x, float y){
        Goal goal = new Goal(sokobanAtlas.findRegion(RegionNames.OVERLAY[3]),x,y);

        return goal;
    }

    public Wall buildWall(float x, float y, int lvl){
        Wall wall = new Wall(sokobanAtlas.findRegion(RegionNames.BRICK_STONE_CRATE),x,y);
        if(lvl<10){
            wall.setTextureRegion(sokobanAtlas.findRegion(RegionNames.FUTURE_INDUSTRY));
        }
        return wall;
    }


    public Crate buildCrate(float x, float y, Crate.State state, int lvl){

        Crate crate = new Crate(sokobanAtlas.findRegions(RegionNames.CRATE_LBLUE),state,x,y);

        return crate;
    }
}
