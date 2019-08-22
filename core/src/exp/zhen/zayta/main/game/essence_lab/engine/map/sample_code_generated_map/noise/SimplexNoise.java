package exp.zhen.zayta.main.game.essence_lab.engine.map.sample_code_generated_map.noise;

import java.util.Random;

import exp.zhen.zayta.util.GdxUtils;


/**
 * From http://stackoverflow.com/questions/18279456/any-simplex-noise-tutorials-or-resources
 * @author Richard Tingle, george
 *
 */

public class SimplexNoise implements MapNoiseGenerator{

    SimplexNoise_octave[] octaves;
    float[] frequencys;
    float[] amplitudes;
    
    
    int width, height;

    public SimplexNoise(int width, int height, float[] frequencys, float[] amplitudes){
        this.width = width;
        this.height = height;
        this.frequencys = frequencys;
        this.amplitudes = amplitudes;
        
        if (frequencys.length != amplitudes.length) {
        	System.err.println("Freq and amp need to match.");
        }
       
        double tot = 0;
        for (double d : amplitudes) {
        	if (d<0) {
            	System.err.println("Amp must be > 0");
        	}
        	tot = tot + d;
        }
        
        for (int i = 0; i < amplitudes.length; i++) {
        	amplitudes[i] /= tot;
        }
        
        
        //recieves a number (eg 128) and calculates what power of 2 it is (eg 2^7)
        int numberOfOctaves=frequencys.length;

        octaves=new SimplexNoise_octave[numberOfOctaves];

        Random rnd= GdxUtils.RANDOM;

        for(int i=0;i<numberOfOctaves;i++){
            octaves[i]=new SimplexNoise_octave(rnd.nextInt());
        }

    }


    public float getNoise(int x, int y){

        float result=0;

        for(int i=0;i<octaves.length;i++){
          result=result+octaves[i].noise(x/frequencys[i], y/frequencys[i])* amplitudes[i];
          
        }


        return result;

    }



	@Override
	public float[][] getMap() {
		float[][] map = new float[width][height];
		for (int w = 0; w < width; w++) {
			for (int h = 0; h < height; h ++) {
				map[w][h] = getNoise(w,h);
				map[w][h]++;
				map[w][h]/=2;
			}
		}
		
		
		
		return map;
	}
} 