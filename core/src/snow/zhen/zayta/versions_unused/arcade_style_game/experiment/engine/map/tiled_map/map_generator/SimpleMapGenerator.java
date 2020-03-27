package snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.tiled_map.map_generator;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Logger;

import snow.zhen.zayta.versions_unused.arcade_style_game.experiment.engine.map.tiled_map.map_generator.noise.SimplexNoise;
import snow.zhen.zayta.util.GdxUtils;

public class SimpleMapGenerator {
    private static final Logger log = new Logger(TiledMapGenerator.class.getName(),Logger.DEBUG);
    private final String COLLISION_LAYER = "collision_tile_set";

    private TextureAtlas mapGeneratorAtlas;
    float waterThreshold = 0.2f;
    private int worldWidth,worldHeight;
    public SimpleMapGenerator(int worldWidth, int worldHeight, TextureAtlas mapGeneratorAtlas, TiledMap tileStorage) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.mapGeneratorAtlas = mapGeneratorAtlas;
    }

//
	private boolean [][] getLandOrWaterUnits(int worldWidth, int worldHeight) {
		// Sometimes simplex noise creates water worlds/land worlds. Make sure there's a bit of both.
		double percentWater;
		boolean [][] isLand;
		do {
			isLand = generateLandOrWaterUnits(worldWidth,worldHeight);
			percentWater = calcPercentWater(
					generateLandOrWaterUnits(worldWidth,worldHeight));
		} while (percentWater <10 || percentWater > 80);
		return isLand;
	}

	private boolean[][] generateLandOrWaterUnits(int worldWidth, int worldHeight) {
		boolean[][] freeLand; // true = land.
		// Simplex noise gen
		int n = GdxUtils.RANDOM.nextInt(3) + 1; //2-7

		float[] freqs = new float[n];
		float[] amps  = new float[n];

		for (int i = 0; i < n; i++) {
			freqs[i] = GdxUtils.RANDOM.nextFloat()*15 + 7;
			amps[i] = GdxUtils.RANDOM.nextFloat()*1000;
		}

		SimplexNoise simplexNoise = new SimplexNoise(worldWidth, worldHeight,freqs,amps);

		float[][] fDryArea = simplexNoise.getMap();

		freeLand = thresholdDryArea(fDryArea);
		return freeLand;
	}

	private boolean[][] thresholdDryArea(float[][] dryArea) {
		boolean[][] thresholdedDryArea = new boolean[dryArea.length][dryArea[0].length];

		// Use the thresholds to fill in the return dryArea
		for(int row = 0; row < dryArea.length; row++){
			for(int col = 0; col < dryArea[row].length; col++){
				thresholdedDryArea[row][col] = (dryArea[row][col] < waterThreshold);
			}
		}
		return thresholdedDryArea;
	}

	private double calcPercentWater(boolean [][] dryArea) {
		double percentWater = 0;

		// Use the thresholds to fill in the return dryArea

		for(int row = 0; row < dryArea.length; row++){
			for(int col = 0; col < dryArea[row].length; col++){
				if (!dryArea[row][col]) {
					percentWater++;
				}
			}
		}
		percentWater/= (dryArea.length * dryArea[0].length);
		percentWater *= 100;
		return percentWater;
	}


}