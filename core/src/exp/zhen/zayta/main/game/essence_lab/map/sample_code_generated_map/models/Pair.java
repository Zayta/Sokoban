package exp.zhen.zayta.main.game.essence_lab.map.sample_code_generated_map.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import exp.zhen.zayta.main.game.essence_lab.map.sample_code_generated_map.MapTools;

import exp.zhen.zayta.main.game.essence_lab.map.sample_code_generated_map.World;

public class Pair {
	private static World world;
	public final int x, y;
	public boolean isLand;

	public static final int WATERLEVEL = Integer.MAX_VALUE;

	// Pathfinding stuff.
	boolean pass = true;
	public Pair parent = null;
	public float g,h,f;  //g = from start; h = to end, f = both together
	Tile tile;
	
	public Pair(int x, int y, boolean isLand) {
		this.x = x;
		this.y = y;
		this.isLand = isLand;
	}

	public static void setWorld(World w) {
		world = w;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Pair)) {
			return false;
		}
		
		if (obj == this) {
			return true;
		}
		
		Pair pairObj = (Pair)obj;
		
		if (pairObj.x == this.x && pairObj.y == this.y) {
			return true;
		}
		
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + x;
		hash = 31 * hash + y;
		return hash;
	}

	public boolean isWater() {
		return !isLand;
	}

	public int getLevel() {
		if (isWater()) {
			return WATERLEVEL;
		}
		return -1;
	}

	public Vector2 asVector() {
		return new Vector2(x, y);
	}

	public void setupTile() {
		tile = new Tile(this);
	}

//	public void draw(GameController game, SpriteBatch batch) {
//		tile.draw(game, batch,this);
//	}

	public Pair[] getCornerNeighbours() {
		return MapTools.getCornerNeighbours(this, world);
	}

	public Pair[] getEdgeNeighbours() {
		return MapTools.getEdgeNeighbours(this, world);
	}

	public boolean isBelow(Pair pair) {
		return getLevel() < pair.getLevel();
	}

	/* Pathfinding methods */

//	public void resetPathStuff() {
//		g = 0;
//		f = 0;
//		h = 0;
//		parent = null;
//		pass = true;
//	}
//
//	public void updateGHFP(float g, float h, GeneratedCell parent){
//		this.parent = parent;
//		this.g = g;
//		this.h = h;
//		f = g+h;
//	}

	public boolean isBorder() {
		Pair[] neighbours = MapTools.getFullNeighbors(this, world);

		for(int i=0; i < neighbours.length; i++) {
			if (neighbours[i] != null && neighbours[i].isWater()) {
				return true;
			}
		}

		return false;
	}

    public void draw(World world, SpriteBatch batch) {
        tile.draw(world, batch,this);
    }
}
