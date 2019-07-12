package exp.zhen.zayta.main.game.wake.map.my_generated_map;

import exp.zhen.zayta.main.game.wake.map.my_generated_map.models.GeneratedCell;

/**
 * Created by dave on 10/12/2016.
 */

public class MapTools {

    static GeneratedCell[] cornerNeighbours = new GeneratedCell[4];
    static final int[][] cornerPos = {{-1,1},{1,1},{1,-1},{-1,-1}};

    static GeneratedCell[] edgeNeighbours = new GeneratedCell[4];
    static final int[][] edgePos = {{-1,0},{0,1},{1,0},{0,-1}};

    public static boolean outsideTheWorld(int i, int j, GeneratedCell[][] map) {
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
     * These return the corner neighbors in a cross shape around a generatedCell, working
     * their way around clockwise. (see crossPos)
     *
     */
    public static GeneratedCell[] getCornerNeighbours(GeneratedCell generatedCell, World world) {
        for (int i = 0; i < 4; i++){
            int xN = generatedCell.x + cornerPos[i][0];
            int yN = generatedCell.y + cornerPos[i][1];
            if (withinBounds(xN, yN, world)) {
                cornerNeighbours[i] = world.map[xN][yN];
            } else {
                cornerNeighbours[i] = null;
            }
        }
        return cornerNeighbours;
    }

    /**
     * These return the edge neighbors in a plus shape around a generatedCell, working
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
    public static GeneratedCell[] getEdgeNeighbours(GeneratedCell generatedCell, World world) {
        for (int i = 0; i < 4; i++){
            int xN = generatedCell.x + edgePos[i][0];
            int yN = generatedCell.y + edgePos[i][1];
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

    static GeneratedCell[] fullNeighbors = new GeneratedCell[8];
    static final int[][] fullPos = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};

    /**
     * These returns all neighbors, clockwise from the left.
     *
     * @param generatedCell
     * @param world
     * @return
     */
    public static GeneratedCell[] getFullNeighbors(GeneratedCell generatedCell, World world) {
        for (int i = 0; i < 8; i++){
            int xN = generatedCell.x + fullPos[i][0];
            int yN = generatedCell.y + fullPos[i][1];
            if (withinBounds(xN, yN, world)) {
                fullNeighbors[i] = world.map[xN][yN];
            } else {
                fullNeighbors[i] = null;
            }
        }
        return fullNeighbors;
    }
}
