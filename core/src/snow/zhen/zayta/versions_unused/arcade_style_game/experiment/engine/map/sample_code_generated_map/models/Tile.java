package snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.sample_code_generated_map.models;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.sample_code_generated_map.World;

public class Tile {	
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

	Array<snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.sample_code_generated_map.models.TileSprite> sprites = new Array<snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.sample_code_generated_map.models.TileSprite>();

	static boolean drawFade = false;

	// Use this: http://www.gamedev.net/page/resources/_/technical/game-programming/tilemap-based-game-techniques-handling-terrai-r934
	public Tile (snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.sample_code_generated_map.models.Pair pair) {
		makeCorners(pair);
		makeEdges(pair);
	}

	private void makeCorners(snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.sample_code_generated_map.models.Pair pair) {
		// Build a hashmap of base -> edges
		HashMap<Integer, Integer> tmp = buildNeighborHashMap(pair, pair.getCornerNeighbours());

		// Now, make the edges array, adding lowest-level edges first.
		if (!tmp.isEmpty()) {
			cornerHashMapToSprites(tmp);
		}
	}

	private void makeEdges(snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.sample_code_generated_map.models.Pair pair) {
		// Build a hashmap of base -> edges
		HashMap<Integer, Integer> tmp = buildNeighborHashMap(pair, pair.getEdgeNeighbours());

		// Now, make the edges array, adding lowest-level edges first.
		if (!tmp.isEmpty()) {
			edgeHashMapToSprites(tmp);
		}
	}


	private HashMap<Integer, Integer> buildNeighborHashMap(snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.sample_code_generated_map.models.Pair pair,
                                                           snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.sample_code_generated_map.models.Pair[] neighbors) {
		HashMap<Integer, Integer> res = new HashMap<Integer, Integer>();

		int i = 0;
		for (snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.sample_code_generated_map.models.Pair p : neighbors) {
			if (p!=null && pair.isBelow(p)) {
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
			snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.sample_code_generated_map.models.TileSprite s = new snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.sample_code_generated_map.models.TileSprite(v);
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
			snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.sample_code_generated_map.models.TileSprite s = new snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.sample_code_generated_map.models.TileSprite(v);
			s.setSolidPart(edgeSpritesSolid.get(tmp.get(v)));
			s.setLinePart(edgeSpritesLine.get(tmp.get(v)));
			sprites.add(s);
			tmp.remove(v);
		}
	}

	public String toString() {
		return "tile has: " + sprites.size;
	}

	public void draw(World world, SpriteBatch batch, Pair pair) {
		// Draw the 'base' first.
		plainTile.setColor(world.getColorFromId(pair.getLevel()));
		plainTile.setBounds(pair.x, pair.y, 1, 1);
		plainTile.draw(batch);

		for (TileSprite s : sprites) {
			s.draw(world, batch, pair);
		}
	}

	public static void setSprites(TextureAtlas tiles) {
		plainTile = tiles.createSprite("white");

		// Make sure we do this, it then does it for the whole atlas.
		plainTile.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

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
