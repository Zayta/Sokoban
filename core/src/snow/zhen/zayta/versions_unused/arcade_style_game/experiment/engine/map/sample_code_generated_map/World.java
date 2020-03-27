package snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.sample_code_generated_map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

//import snow.zhen.zayta.main.game.wake.map.map.jumppointpathsearch.Heap;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.sample_code_generated_map.models.Pair;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.sample_code_generated_map.models.Tile;
import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.sample_code_generated_map.models.TileSprite;

public class World {
	/*
	 *  Contains and manages the world
	 */
	private int worldWidth, worldHeight;

	// The different types of map available
	protected Pair[][] map;

//	protected Heap heap;

	// Map functions and info
	protected snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.sample_code_generated_map.WorldGenerator worldGen;

	public World(int worldWidth, int worldHeight,TextureAtlas mapGeneratorAtlas) {
	    this.worldWidth = worldWidth; this.worldHeight = worldHeight;
	    setUpGraphics(mapGeneratorAtlas);
		initiateWorld();
	}
    private void setUpGraphics(TextureAtlas mapGeneratorAtlas) {
        // Set up graphics here for passing between classes
        // Try to keep everything here to make for easy disposal at the end.
        Tile.setSprites(mapGeneratorAtlas);
    }

	public void initiateWorld() {
		Pair.setWorld(this);

		// First, generate the land
		worldGen = new WorldGenerator(worldWidth,worldHeight);
		setMap(worldGen.getMap());
		setupTiles();
//		heap = new Heap();
	}


	protected void setupTiles() {
		for (Pair[] pS : getMap()) {
			for (Pair p : pS) {
				p.setupTile();
			}
		}
	}

	public Pair[][] getMap() {
		return map;
	}
	public void setMap(Pair[][] map) {
		this.map = map;
	}

	public Pair getPair(int x, int y) {
		return map[x][y];
	}
	
	public int getWidth() {
		return worldWidth;
	}
	
	public int getHeight() {
		return worldHeight;
	}
	
	public Color getColorFromId(Integer level) {
		if (level == Pair.WATERLEVEL) {
			return TileSprite.waterColor;
		}  else {
			return TileSprite.landColor;
		}
	}

	public void draw(SpriteBatch batch){
	 renderTiles(batch);
    }
    private void renderTiles(SpriteBatch batch) {
        for (int x = 0; x < worldWidth; x++) {
            for (int y = 0; y < worldHeight; y++) {
                Pair p = getPair(x, y);
                p.draw(this, batch);
            }
        }
    }
//
//	public Vector2 getRandomStartPosition() {
//
//		Random r = new Random();
//
//		while(true) {
//			GeneratedCell p = getPair(r.nextInt(getWidth()), r.nextInt(getHeight()));
//			if (p.isLand) {
//				return new Vector2(p.x, p.y);
//			}
//		}
//	}

//	public void resetPathStuff() {
//		for (int i=0; i<map.length; i++) {
//			for (int j=0; j<map[0].length; j++) {
//				map[i][j].resetPathStuff();
//			}
//		}
//	}

//	//--------------------------HEAP-----------------------------------//
//	/**
//	 * Adds a node's (x,y,f) to the heap. The heap is sorted by 'f'.
//	 *
//	 * @param node (GeneratedCell) node to be added to the heap
//	 */
//	public void heapAdd(GeneratedCell node){
//		float[] tmp = {node.x,node.y,node.f};
//		heap.add(tmp);
//	}

	/**
	 * @return (int) size of the heap
	 */
//	public int heapSize(){
//		return heap.getSize();
//	}
	/**
	 * @return (GeneratedCell) takes data from popped float[] and returns the correct node
	 */
//	public GeneratedCell heapPopPair(){
//		float[] tmp = heap.pop();
//		return getPair((int)tmp[0],(int)tmp[1]);
//	}
	//-----------------------------------------------------------------//

	//---------------------------Passability------------------------------//

	//--------------------------------------------------------------------//


//	public ArrayList<GeneratedCell> pathCreate(GeneratedCell node){
//		ArrayList<GeneratedCell> trail = new ArrayList<GeneratedCell>();
//		while (node.parent!=null){
//			try{
//				trail.add(0,node);
//			}catch (Exception e){}
//			node = node.parent;
//		}
//		return trail;
//	}

//	public float toPointApprox(float x, float y, int tx, int ty){
//		return (float) Math.sqrt(Math.pow(Math.abs(x-tx),2) + Math.pow(Math.abs(y-ty), 2));
//	}

	/**
	 * Tests an x,y node's passability
	 *
	 * @param x (int) node's x coordinate
	 * @param y (int) node's y coordinate
	 * @return (boolean) true if the node is obstacle free and on the map, false otherwise
	 */
	public boolean walkable(int x, int y){

		if (MapTools.outsideTheWorld(x, y, getMap())) {
			return false;
		}

		Pair pair = getMap()[x][y];
		if (pair.isWater() || pair.isBorder()) {
			return false;
		}

		return true;
	}

	/**
	 * returns all adjacent nodes that can be traversed
	 *
	 * @param node (GeneratedCell) finds the neighbors of this node
	 * @return (int[][]) list of neighbors that can be traversed
	 */
	public int[][] getNeighbors(Pair node){
		int[][] neighbors = new int[8][2];
		int x = node.x;
		int y = node.y;
		boolean d0 = false; //These booleans are for speeding up the adding of nodes.
		boolean d1 = false;
		boolean d2 = false;
		boolean d3 = false;

		if (walkable(x,y-1)){
			neighbors[0] = (tmpInt(x,y-1));
			d0 = d1 = true;
		}
		if (walkable(x+1,y)){
			neighbors[1] = (tmpInt(x+1,y));
			d1 = d2 = true;
		}
		if (walkable(x,y+1)){
			neighbors[2] = (tmpInt(x,y+1));
			d2 = d3 = true;
		}
		if (walkable(x-1,y)){
			neighbors[3] = (tmpInt(x-1,y));
			d3 = d0 = true;
		}
		if (d0 && walkable(x-1,y-1)){
			neighbors[4] = (tmpInt(x-1,y-1));
		}
		if (d1 && walkable(x+1,y-1)){
			neighbors[5] = (tmpInt(x+1,y-1));
		}
		if (d2 && walkable(x+1,y+1)){
			neighbors[6] = (tmpInt(x+1,y+1));
		}
		if (d3 && walkable(x-1,y+1)){
			neighbors[7] = (tmpInt(x-1,y+1));
		}
		return neighbors;
	}

	/**
	 * Encapsulates x,y in an int[] for returning. A helper method for the jump method
	 *
	 * @param x (int) point's x coordinate
	 * @param y (int) point's y coordinate
	 * @return ([]int) bundled x,y
	 */
	public int[] tmpInt (int x, int y){
		int[] tmpIntsTmpInt = {x,y};  //create the tmpInt's tmpInt[]
		return tmpIntsTmpInt;         //return it
	}
}
