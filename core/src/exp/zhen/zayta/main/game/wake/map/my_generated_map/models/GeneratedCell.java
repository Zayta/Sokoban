package exp.zhen.zayta.main.game.wake.map.my_generated_map.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

import exp.zhen.zayta.main.game.wake.map.my_generated_map.MapTools;

import exp.zhen.zayta.main.game.wake.map.my_generated_map.World;

public class GeneratedCell extends TiledMapTileLayer.Cell {
	private static World world;
	public final int x, y;
	public boolean isLand;

	public static final int WATERLEVEL = Integer.MAX_VALUE;

	// Pathfinding stuff.
	boolean pass = true;
	public GeneratedCell parent = null;
	public float g,h,f;  //g = from start; h = to end, f = both together
	Tile tile;
	
	public GeneratedCell(int x, int y, boolean isLand) {
		this.x = x;
		this.y = y;
		this.isLand = isLand;
	}

	public static void setWorld(World w) {
		world = w;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GeneratedCell)) {
			return false;
		}
		
		if (obj == this) {
			return true;
		}
		
		GeneratedCell generatedCellObj = (GeneratedCell)obj;
		
		if (generatedCellObj.x == this.x && generatedCellObj.y == this.y) {
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
//		tile = new Tile(this);
		makeCorners();
		makeEdges();
	}

//	public void draw(GameController game, SpriteBatch batch) {
//		tile.draw(game, batch,this);
//	}

	public GeneratedCell[] getCornerNeighbours() {
		return MapTools.getCornerNeighbours(this, world);
	}

	public GeneratedCell[] getEdgeNeighbours() {
		return MapTools.getEdgeNeighbours(this, world);
	}

	public boolean isBelow(GeneratedCell generatedCell) {
		return getLevel() < generatedCell.getLevel();
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
		GeneratedCell[] neighbours = MapTools.getFullNeighbors(this, world);

		for(int i=0; i < neighbours.length; i++) {
			if (neighbours[i] != null && neighbours[i].isWater()) {
				return true;
			}
		}

		return false;
	}
//
//    public void draw(World world, SpriteBatch batch) {
//        tile.draw(world, batch,this);
//    }


    /*Tile*/
	static Sprite plainTile;

	// OK, we label according to this:
	/*
	 *    2  3  4
	 *    1  t  5
	 *    8  7  6
	 *
	 */
	static HashMap<Integer, Sprite> edgeSpritesSolid, edgeSpritesLine, edgeSpritesFade;
	static HashMap<Integer, Sprite> cornerSprites, cornerSpritesFade;

	Array<TileSprite> sprites = new Array<TileSprite>();

	static boolean drawFade = false;

	// Use this: http://www.gamedev.net/page/resources/_/technical/game-programming/tilemap-based-game-techniques-handling-terrai-r934


	private void makeCorners() {
		// Build a hashmap of base -> edges
		HashMap<Integer, Integer> tmp = buildNeighborHashMap(getCornerNeighbours());

		// Now, make the edges array, adding lowest-level edges first.
		if (!tmp.isEmpty()) {
			cornerHashMapToSprites(tmp);
		}
	}

	private void makeEdges() {
		// Build a hashmap of base -> edges
		HashMap<Integer, Integer> tmp = buildNeighborHashMap(getEdgeNeighbours());

		// Now, make the edges array, adding lowest-level edges first.
		if (!tmp.isEmpty()) {
			edgeHashMapToSprites(tmp);
		}
	}


	private HashMap<Integer, Integer> buildNeighborHashMap(GeneratedCell[] neighbors) {
		HashMap<Integer, Integer> res = new HashMap<Integer, Integer>();

		int i = 0;
		for (GeneratedCell p : neighbors) {
			if (p!=null && this.isBelow(p)) {
				int v = (int) Math.pow(2, i); // add on the next power of 2 (effectively going up in binary, 1, 10, 100, 1000
				if (res.containsKey(p.getLevel())) {
					res.put(p.getLevel(), res.get(p.getLevel()) + v);
				} else {
					res.put(p.getLevel(), v);
				}
			}
			i++;
		}

		return res;
	}

	private void cornerHashMapToSprites(HashMap<Integer, Integer> tmp) {
		while (!tmp.isEmpty()) {
			Integer v = tmp.keySet().iterator().next(); // Take a random value from the map.
			for (Integer ii : tmp.keySet()) { // check if lower value exists
				if (ii<v) {
					v = ii;
				}
			}
			TileSprite s = new TileSprite(v);
			s.setLinePart(cornerSprites.get(tmp.get(v)));
			sprites.add(s);
			tmp.remove(v);
		}
	}

	private void edgeHashMapToSprites(HashMap<Integer, Integer> tmp) {
		while (!tmp.isEmpty()) {
			Integer v = tmp.keySet().iterator().next(); // Take a random value from the map.
			for (Integer ii : tmp.keySet()) { // check if lower value exists
				if (ii<v) {
					v = ii;
				}
			}
			TileSprite s = new TileSprite(v);
			s.setSolidPart(edgeSpritesSolid.get(tmp.get(v)));
			s.setLinePart(edgeSpritesLine.get(tmp.get(v)));
			sprites.add(s);
			tmp.remove(v);
		}
	}

	public String toString() {
		return "tile has: " + sprites.size;
	}

	public void draw(World world, SpriteBatch batch) {
		// Draw the 'base' first.
		plainTile.setColor(world.getColorFromId(getLevel()));
		plainTile.setBounds(x, y, 1, 1);
		plainTile.draw(batch);

		for (TileSprite s : sprites) {
			s.draw(world, batch, this);
		}
	}

	public static void setSprites(TextureAtlas tiles) {
		plainTile = tiles.createSprite("white");

		// Make sure we do this, it then does it for the whole atlas.
		plainTile.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		// Solid parts of edges
		// edge_0 is first edge
		edgeSpritesSolid = new HashMap<Integer, Sprite>();
		for (int i = 1; i <16; i ++) {
			String fileName = "edgeS-" + (i-1);
			Sprite s = tiles.createSprite(fileName);
			edgeSpritesSolid.put(i, s);
		}

		// Line parts of edges.
		edgeSpritesLine = new HashMap<Integer, Sprite>();
		for (int i = 1; i <16; i ++) {
			String fileName = "edgeL-" + (i-1);
			Sprite s = tiles.createSprite(fileName);
			edgeSpritesLine.put(i, s);
		}


		cornerSprites = new HashMap<Integer, Sprite>();
		for (int i = 1; i <16; i ++) {
			String fileName = "corner-" + (i-1);
			Sprite s = tiles.createSprite(fileName);
			cornerSprites.put(i, s);
		}

		edgeSpritesFade  = new HashMap<Integer, Sprite>();
		cornerSpritesFade = new HashMap<Integer, Sprite>();

		if (drawFade) {
			for (int i = 1; i <16; i ++) {
				String fileName = "edgeF-" + (i-1);
				Sprite s = tiles.createSprite(fileName);
				edgeSpritesFade.put(i, s);
			}

			for (int i = 1; i <16; i ++) {
				String fileName = "cornerF-" + (i-1);
				Sprite s = tiles.createSprite(fileName);
				cornerSpritesFade.put(i, s);
			}
		}

	}
}
