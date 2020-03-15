package exp.zhen.zayta.main.arcade_style_game.experiment.engine.map.util;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import java.util.ArrayList;

import exp.zhen.zayta.main.arcade_style_game.config.SizeManager;
import exp.zhen.zayta.main.arcade_style_game.experiment.engine.map.MapMaker;
import exp.zhen.zayta.main.arcade_style_game.experiment.engine.movement.PositionTracker;
import exp.zhen.zayta.util.GdxUtils;

public class Arrangements
{

    private static final Logger log = new Logger(Arrangements.class.getName(),Logger.DEBUG);
//    //todo this occupiedPositions KeyListMap only accounts for initial positions. It is not like PositionTracker which has a system that updates. TO make it account for moving positions, do the same as in PositionTracker(Make a system and entities to have a certain component, and put them in teh biMap
    private static ArrayList <Integer> availableKeys = new ArrayList<Integer>();
    public static void initAvailableKeys(float mapWidth,float mapHeight){
        int capacity = PositionTracker.generateKey(mapWidth,mapHeight);//the last key
        //////log.debug("Capacity is "+capacity);
        for(int i = 0; i<capacity; i++){
            availableKeys.add(i);
        }
    }

    //todo when modifying this, make sure to change PositionTracker too
    public static Vector2[] generateRandomUCoordinates(int maxNumCoordinates){
        ArrayList<Vector2> points = new ArrayList<Vector2>();


        Rectangle mapBounds = MapMaker.getMapBounds();
        int maxX = (int)(mapBounds.width);
        int maxY = (int)(mapBounds.height);

        Rectangle rectangle = new Rectangle();
        rectangle.width = SizeManager.maxObjWidth;
        rectangle.height = SizeManager.maxObjHeight;
        for(int i = 0; i<maxNumCoordinates;i++) {
            if (availableKeys.isEmpty()){//if no more available keys, dont generate.
                //////log.debug("availableKeys all taken");
                break;
        }
            int keyIndex = GdxUtils.RANDOM.nextInt(availableKeys.size());
            int key = availableKeys.get(keyIndex);

            //make rectangle from key
            Vector2 decodedPos = PositionTracker.getPositionFromKey(key);
            rectangle.setPosition(decodedPos.x,decodedPos.y);
            availableKeys.remove(keyIndex);
            points.add(new Vector2(rectangle.x,rectangle.y));
            //////log.debug("Point "+i+" should be: ("+rectangle.x+","+rectangle.y+"), and availKey used was: "+key+"but PositionTracker key is "+PositionTracker.generateKey(rectangle.x,rectangle.y));
        }
        //////log.debug("Available Keys: "+availableKeys);
        Vector2[] ret= points.toArray(new Vector2[points.size()]);
        return ret;
    }


    public static Vector2[] generateRandomCoordinates(int numCoordinates){
        Vector2[] points = new Vector2[numCoordinates];

        Rectangle mapBounds = MapMaker.getMapBounds();
        int maxX = (int)(mapBounds.width);
        int maxY = (int)(mapBounds.height);

        Rectangle rectangle = new Rectangle();
        rectangle.width = SizeManager.maxObjWidth;
        rectangle.height = SizeManager.maxObjHeight;
        int key = 0;
        for(int i = 0; i<numCoordinates;i++) {
            do {
                rectangle.x = GdxUtils.RANDOM.nextInt(maxX) + GdxUtils.RANDOM.nextFloat();
                rectangle.y = GdxUtils.RANDOM.nextInt(maxY) + GdxUtils.RANDOM.nextFloat();
//                key = PositionTracker.generateKey(rectangle.x,rectangle.y);
            } while ((!withinBounds(rectangle) ));
//            generatedCoordinates.add(key);
            points[i] = new Vector2(rectangle.x,rectangle.y);
        }
        return points;
    }

    //todo this method goes into a long loop when there is not enough spots left and the probability of picking out unoccupied spots is minimum
//    private static boolean keyIsGeneratedForArea(Rectangle rectangle){
//        float right = rectangle.x+rectangle.width, top = rectangle.y+rectangle.height;
//        int [] keys = {
//                PositionTracker.generateKey(rectangle.x,rectangle.y),
//                PositionTracker.generateKey(rectangle.x,top),
//                PositionTracker.generateKey(right,rectangle.y),
//                PositionTracker.generateKey(right,top)
//        };
//        boolean ret = false;
//        for(int k: keys){
//            ret = ret || generatedCoordinates.contains(k);
//        }
//        //////log.debug("Ret has been "+ret);
//        return ret;
//    }
    private static boolean withinBounds(Rectangle objBounds){
        return MapMaker.getMapBounds().contains(objBounds);
    }


    public static Vector2[] generateCircleArrangementCoordinates(int numCoordinates){
        float radius = 2.0f;
        Rectangle mapBounds = MapMaker.getMapBounds();
        int maxX = (int)(mapBounds.width);
        int maxY = (int)(mapBounds.height);

        Circle circle = new Circle();
        float minDim = Math.max(SizeManager.maxObjWidth,SizeManager.maxObjHeight);

        do {
            float centerX = GdxUtils.RANDOM.nextInt(maxX)+GdxUtils.RANDOM.nextFloat();
            float centerY = GdxUtils.RANDOM.nextInt(maxY)+GdxUtils.RANDOM.nextFloat();
//            float radius = GdxUtils.RANDOM.nextInt(Math.min(maxX, maxY))+GdxUtils.RANDOM.nextFloat();
            circle.set(centerX,centerY,radius);
            //////log.debug("mapBounds Contain Radius "+mapBounds.contains(circle));

        }while((!mapBounds.contains(circle)||circle.radius<=minDim));


        Vector2[] points = circle(numCoordinates,circle.x,circle.y,circle.radius);
        return points;
    }

    private static Vector2[] circle(int numNodes, float centerX, float centerY, float radius){
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
