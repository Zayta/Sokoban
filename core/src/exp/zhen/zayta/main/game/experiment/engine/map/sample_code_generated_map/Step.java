package exp.zhen.zayta.main.game.experiment.engine.map.sample_code_generated_map;

/**
 * A single step within the path
 *
 * @author Kevin Glass
 */
public class Step {
    /** The x coordinate at the given step */
    private double x;
    /** The y coordinate at the given step */
    private double y;

    /**
     * Create a new step
     *
     * @param x The x coordinate of the new step
     * @param y The y coordinate of the new step
     */
    public Step(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the x coordinate of the new step
     *
     * @return The x coodindate of the new step
     */
    public double getX() {
        return x;
    }

    /**
     * Get the y coordinate of the new step
     *
     * @return The y coodindate of the new step
     */
    public double getY() {
        return y;
    }

    /**
     * @see Object#hashCode()
     */
    public int hashCode() {
        return Double.valueOf(x * y).hashCode();
    }

    /**
     * @see Object#equals(Object)
     */
    public boolean equals(Object other) {
        if (other instanceof Step) {
            Step o = (Step) other;

            return (int)o.x == (int)x && (int)o.y == (int)y;
        }

        return false;
    }
}
