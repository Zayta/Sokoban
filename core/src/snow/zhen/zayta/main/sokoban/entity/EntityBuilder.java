package snow.zhen.zayta.main.sokoban.entity;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Hashtable;

import snow.zhen.zayta.main.assets.RegionNames;
import snow.zhen.zayta.main.sokoban.entity.units.Goal;
import snow.zhen.zayta.util.BiMap;
import snow.zhen.zayta.main.sokoban.entity.units.Crate;
import snow.zhen.zayta.main.sokoban.entity.units.Nighter;
import snow.zhen.zayta.main.sokoban.entity.units.Wall;

public class EntityBuilder {
    private TextureAtlas sokobanAtlas;
    public EntityBuilder(TextureAtlas sokobanAtlas){
        this.sokobanAtlas = sokobanAtlas;

        characters = new BiMap<snow.zhen.zayta.main.sokoban.entity.CharacterName, snow.zhen.zayta.main.sokoban.entity.units.Nighter>();
        characters.put(snow.zhen.zayta.main.sokoban.entity.CharacterName.TENYU, new snow.zhen.zayta.main.sokoban.entity.units.Nighter
                            (sokobanAtlas.findRegion(RegionNames.TENYU)));
        characters.put(snow.zhen.zayta.main.sokoban.entity.CharacterName.LETRA, new snow.zhen.zayta.main.sokoban.entity.units.Nighter
                (sokobanAtlas.findRegion(RegionNames.LETRA)));
        characters.put(snow.zhen.zayta.main.sokoban.entity.CharacterName.LORALE, new snow.zhen.zayta.main.sokoban.entity.units.Nighter
                (sokobanAtlas.findRegion(RegionNames.LORALE)));
        characters.put(snow.zhen.zayta.main.sokoban.entity.CharacterName.TARIA, new snow.zhen.zayta.main.sokoban.entity.units.Nighter
                (sokobanAtlas.findRegion(RegionNames.TARIA)));
        characters.put(snow.zhen.zayta.main.sokoban.entity.CharacterName.XIF, new snow.zhen.zayta.main.sokoban.entity.units.Nighter
                (sokobanAtlas.findRegion(RegionNames.XIF)));

    }
    public BiMap<snow.zhen.zayta.main.sokoban.entity.CharacterName, snow.zhen.zayta.main.sokoban.entity.units.Nighter> characters;

    public snow.zhen.zayta.main.sokoban.entity.units.Nighter getCharacter(CharacterName characterName, float x, float y){
        Nighter nighter = characters.get(characterName);
        nighter.initPos(x,y);
        return nighter;
    }

    public Goal buildGoal(float x, float y){
        Goal goal = new Goal(sokobanAtlas.findRegion(RegionNames.OVERLAY[3]),x,y);

        return goal;
    }

    public snow.zhen.zayta.main.sokoban.entity.units.Wall buildWall(float x, float y, int lvl){
        snow.zhen.zayta.main.sokoban.entity.units.Wall wall = new Wall(sokobanAtlas.findRegion(RegionNames.BRICK_STONE_CRATE),x,y);
        if(lvl<10){
            wall.setTextureRegion(sokobanAtlas.findRegion(RegionNames.BRICK_STONE_CRATE));
        }
        return wall;
    }


    public snow.zhen.zayta.main.sokoban.entity.units.Crate buildCrate(float x, float y, snow.zhen.zayta.main.sokoban.entity.units.Crate.State state, int lvl){
        Hashtable<snow.zhen.zayta.main.sokoban.entity.units.Crate.State,TextureRegion> crateRegions= new Hashtable<snow.zhen.zayta.main.sokoban.entity.units.Crate.State, TextureRegion>();
        crateRegions.put(snow.zhen.zayta.main.sokoban.entity.units.Crate.State.NORMAL,sokobanAtlas.findRegion(RegionNames.CRATE_BLUE_NORMAL));
        crateRegions.put(snow.zhen.zayta.main.sokoban.entity.units.Crate.State.IN_GOAL,sokobanAtlas.findRegion(RegionNames.CRATE_BLUE_SOLVED));
        crateRegions.put(snow.zhen.zayta.main.sokoban.entity.units.Crate.State.IN_HOLE,sokobanAtlas.findRegion(RegionNames.CRATE_BLUE_HOLE));
        crateRegions.put(snow.zhen.zayta.main.sokoban.entity.units.Crate.State.IMMOVABLE,sokobanAtlas.findRegion(RegionNames.CRATE_BLUE_BLOCK));

        snow.zhen.zayta.main.sokoban.entity.units.Crate crate = new Crate(crateRegions,state,x,y);

        return crate;
    }
}
