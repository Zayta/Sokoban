package exp.zhen.zayta.mode.quest.entity;

import com.badlogic.gdx.math.Vector2;

import exp.zhen.zayta.config.SizeManager;

public class Arrangements
{
    public static Vector2[] circle(int numNodes, float centerX, float centerY, float radius){
        Vector2[] points = new Vector2[numNodes];
        double a = 2*Math.PI/numNodes;
        for(int i = 0; i<numNodes; i++){
            double ai = a*i;
            float xi = (float)Math.cos(ai)*radius+centerX, yi = (float)Math.sin(ai)*radius+centerY;
            points[i] = new Vector2(xi,yi);
        }
        return points;

    }

}
