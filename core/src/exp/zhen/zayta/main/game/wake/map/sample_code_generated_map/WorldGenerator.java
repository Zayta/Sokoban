package exp.zhen.zayta.main.game.wake.map.sample_code_generated_map;


import exp.zhen.zayta.main.game.wake.map.sample_code_generated_map.models.Pair;
import exp.zhen.zayta.main.game.wake.map.sample_code_generated_map.noise.SimplexNoise;
import exp.zhen.zayta.util.GdxUtils;

public class WorldGenerator {
	public boolean[][] map; // true = land.
	float waterThreshold = 0.2f;
	private int worldWidth,worldHeight;
	
	public WorldGenerator(int worldWidth, int worldHeight)
	{
		this.worldWidth = worldWidth; this.worldHeight = worldHeight;
		init();
	}
	
	private void init() {
		// Sometimes simplex noise creates water worlds/land worlds. Make sure there's a bit of both.
		double percentWater;
		do {
			generateMap();
			percentWater = calcPercentWater();
		} while (percentWater <10 || percentWater > 80);
	}
	public boolean[][] generateMap() {
		// Simplex noise gen
		int n = GdxUtils.RANDOM.nextInt(3) + 1; //2-7
		
		float[] freqs = new float[n];
		float[] amps  = new float[n];
		
		for (int i = 0; i < n; i++) {
			freqs[i] = GdxUtils.RANDOM.nextFloat()*15 + 7;
			amps[i] = GdxUtils.RANDOM.nextFloat()*1000;
		}
		
		SimplexNoise simplexNoise = new SimplexNoise (worldWidth, worldHeight,freqs,amps);
				
		float[][] fMap = simplexNoise.getMap();
		
		map = thresholdMap(fMap);
		return map;
	}
	
	public boolean[][] thresholdMap(float[][] map) {
		boolean[][] thresholdedMap = new boolean[map.length][map[0].length];

		// Use the thresholds to fill in the return map
		for(int row = 0; row < map.length; row++){
			for(int col = 0; col < map[row].length; col++){
				if (map[row][col] < waterThreshold) {
					thresholdedMap[row][col] = false;
				} else {
					thresholdedMap[row][col] = true;
				}
			}
		}
		return thresholdedMap;
	}
	
	public double calcPercentWater() {
		double percentWater = 0;

		// Use the thresholds to fill in the return map
		
		for(int row = 0; row < map.length; row++){
			for(int col = 0; col < map[row].length; col++){
				if (!map[row][col]) {
					percentWater++;
				}
			}
		}
		percentWater/= (map.length * map[0].length);
		percentWater *= 100;
		return percentWater;
	}

	public Pair[][] getMap() {
		Pair[][] pairMap = new Pair[worldWidth][worldHeight];
		for (int x = 0; x < worldWidth; x ++) {
			for (int y = 0; y < worldHeight; y ++) {
				pairMap[x][y] = new Pair(x,y,map[x][y]);
			}
		}
		return pairMap;
	}	
}