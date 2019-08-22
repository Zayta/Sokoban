package exp.zhen.zayta.main.game.essence_lab.engine.movement;

import static exp.zhen.zayta.util.GdxUtils.RANDOM;

public enum Direction{
    none(0,0),
    up(0,1),
    down(0,-1),
    left(-1,0),
    right(1,0);

    public float directionX,directionY;
    Direction(float directionX, float directionY)
    {
        this.directionX = directionX;
        this.directionY=directionY;
    }
//    public final Vector2 vector2;
//    Direction(Vector2 vector2)
//    {
//        this.vector2 = vector2;
//    }

//    /*Returns direction based on given vector*/
//    public static Direction get(Vector2 vector2)
//    {
//        float mainDirection = Math.max(vector2.x,vector2.y);
//        if(mainDirection==vector2.y)
//        {
//            if(vector2.y>0)
//                return up;
//            if (vector2.y<0)
//                return down;
//        }
//        else
//        {
//            if(vector2.x<0)
//                return left;
//            if(vector2.x>0)
//                return right;
//        }
////        if(vector2.y>0)
////            return up;
////        if (vector2.y<0)
////            return down;
////        if(vector2.x<0)
////            return left;
////        if(vector2.x>0)
////            return right;
////
//        return none;
//    }

    /**
     * Returns a direction given an angle.
     * Directions are defined as follows:
     *
     * Up: [45, 135]
     * Right: [0,45] and [315, 360]
     * Down: [225, 315]
     * Left: [135, 225]
     *
     * @param angle an angle from 0 to 360 - e
     * @return the direction of an angle
     */
    public static Direction get(double angle){
        if(inRange(angle, 45, 135)){
            return Direction.up;
        }
        else if(inRange(angle, 0,45) || inRange(angle, 315, 360)){
            return Direction.right;
        }
        else if(inRange(angle, 225, 315)){
            return Direction.down;
        }
        else{
            return Direction.left;
        }

    }

    public Direction getOpposite()
    {
        Direction ret = none;
        switch (this)
        {
            case up:
                ret = down;
                break;
            case down:
                ret = up;
                break;
            case left:
                ret = right;
                break;
            case right:
                ret = left;
                break;
        }
        return ret;
    }

    /**
     * @param angle an angle
     * @param init the initial bound
     * @param end the final bound
     * @return returns true if the given angle is in the interval [init, end).
     */
    private static boolean inRange(double angle, float init, float end){
        return (angle >= init) && (angle < end);
    }
    private static final Direction[] VALUES = values();
    private static final int SIZE = VALUES.length;

    /***Direction Generators***/
    public static Direction generateRandomDirection()  {
        return VALUES[RANDOM.nextInt(SIZE-1)+1];//excludes none
    }

    public static Direction generateDirectionExcluding(Direction excludedDirection)
    {
        int randir;
        do{
            randir = RANDOM.nextInt(SIZE-1)+1;//excludes excludedDirection and none
        }while(VALUES[randir]==excludedDirection);
        return VALUES[randir];
    }

    public static Direction generateVerticalDirection()
    {
        int randir = RANDOM.nextInt(2)+1;
        return VALUES[randir];
    }
    public static Direction generateHorizontalDirection()
    {
        int randir = RANDOM.nextInt(2)+3;
        return VALUES[randir];
    }
}
