package exp.zhen.zayta.main.game.essence_lab.map.sample_code_generated_map;

import java.util.List;

import com.badlogic.gdx.utils.Array;
import exp.zhen.zayta.main.game.essence_lab.map.sample_code_generated_map.models.Pair;

/**
 * A path determined by some path finding algorithm. A series of steps from
 * the starting location to the target location. This includes a step for the
 * initial location.
 *
 * @author Kevin Glass
 */
public class Path {
    /** The list of steps building up this path */
    private Array<Step> steps = new Array<Step>();

    /**
     * Create an empty path
     */
    public Path() {

    }

    public Path(List<Pair> search) {
        if (!(null == search || search.isEmpty())) {
            for (Pair node : search) {
                Step step = new Step(node.x, node.y);
                this.steps.add(step);
            }
        }
    }

    /**
     * Get the length of the path, i.e. the number of steps
     *
     * @return The number of steps in this path
     */
    public int getLength() {
        return this.steps.size;
    }

    /**
     * Get the step at a given index in the path
     *
     * @param index The index of the step to retrieve. Note this should
     * be >= 0 and < getLength();
     * @return The step information, the position on the map.
     */
    public Step getStep(int index) {
        if (index >= getLength()) {
            return (Step) this.steps.get(getLength() - 1);
        }
        return (Step) this.steps.get(index);
    }

    /**
     * Get the x coordinate for the step at the given index
     *
     * @param index The index of the step whose x coordinate should be retrieved
     * @return The x coordinate at the step
     */
    public double getX(int index) {
        return getStep(index).getX();
    }

    /**
     * Get the y coordinate for the step at the given index
     *
     * @param index The index of the step whose y coordinate should be retrieved
     * @return The y coordinate at the step
     */
    public double getY(int index) {
        return getStep(index).getY();
    }

    /**
     * Append a step to the path.
     *
     * @param x The x coordinate of the new step
     * @param y The y coordinate of the new step
     */
    public void appendStep(int x, int y) {
        this.steps.add(new Step(x,y));
    }

    /**
     * Prepend a step to the path.
     *
     * @param x The x coordinate of the new step
     * @param y The y coordinate of the new step
     */
    public void prependStep(int x, int y) {
        final Step step = new Step(x, y);
        if (!this.steps.contains(step, false)) {
            this.steps.add(step);
        }
    }

    /**
     * Check if this path contains the given step
     *
     * @param x The x coordinate of the step to check for
     * @param y The y coordinate of the step to check for
     * @return True if the path contains the given step
     */
    public boolean contains(int x, int y) {
        return this.steps.contains(new Step(x,y),false);
    }

    public void setSteps(Array<Step> steps) {
        this.steps = steps;
    }

    public void setSteps(Step[] steps) {
        setSteps(new Array<Step>(steps));
    }

    public Array<Step> getSteps() {
        return this.steps;
    }

    public boolean isEmpty() {
        return this.steps.size == 0;
    }
}