package exp.zhen.zayta.main.game.wake.map.sample_code_generated_map;

import exp.zhen.zayta.main.game.wake.map.sample_code_generated_map.models.Pair;

/**
 * Created by dave on 10/12/2016.
 */

public class MapTools {

    static Pair[] cornerNeighbours = new Pair[4];
    static final int[][] cornerPos = {{-1,1},{1,1},{1,-1},{-1,-1}};

    static Pair[] edgeNeighbours = new Pair[4];
    static final int[][] edgePos = {{-1,0},{0,1},{1,0},{0,-1}};

    public static boolean outsideTheWorld(int i, int j, Pair[][] map) {
        if (i < 0 || j < 0) {
            return true;
        }

        if (i >= map.length) {
            return true;
        }

        if (j >= map[0].length) {
            return true;
        }

        return false;
    }

    /**
     * These return the corner neighbors in a cross shape around a pair, working
     * their way around clockwise. (see crossPos)
     *
     */
    public static Pair[] getCornerNeighbours(Pair pair, World world) {
        for (int i = 0; i < 4; i++){
            int xN = pair.x + cornerPos[i][0];
            int yN = pair.y + cornerPos[i][1];
            if (withinBounds(xN, yN, world)) {
                cornerNeighbours[i] = world.map[xN][yN];
            } else {
                cornerNeighbours[i] = null;
            }
        }
        return cornerNeighbours;
    }

    /**
     * These return the edge neighbors in a plus shape around a pair, working
     * their way around clockwise. (see crossPos)
     *
     *
     *       2
     *    1  t  3
     *       4
     *
     *
     *
     */
    public static Pair[] getEdgeNeighbours(Pair pair, World world) {
        for (int i = 0; i < 4; i++){
            int xN = pair.x + edgePos[i][0];
            int yN = pair.y + edgePos[i][1];
            if (withinBounds(xN, yN, world)) {
                edgeNeighbours[i] = world.map[xN][yN];
            } else {
                edgeNeighbours[i] = null;
            }
        }
        return edgeNeighbours;
    }

    private static boolean withinBounds(int x, int y, World world) {
        return (x < world.getWidth() && y < world.getHeight() && x > -1 && y > -1);
    }

    static Pair[] fullNeighbors = new Pair[8];
    static final int[][] fullPos = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};

    /**
     * These returns all neighbors, clockwise from the left.
     *
     * @param pair
     * @param world
     * @return
     */
    public static Pair[] getFullNeighbors(Pair pair, World world) {
        for (int i = 0; i < 8; i++){
            int xN = pair.x + fullPos[i][0];
            int yN = pair.y + fullPos[i][1];
            if (withinBounds(xN, yN, world)) {
                fullNeighbors[i] = world.map[xN][yN];
            } else {
                fullNeighbors[i] = null;
            }
        }
        return fullNeighbors;
    }
}
