package animation;
import biuoop.DrawSurface;

/**
 * animation interface.
 *
 * @author shinake
 */
public interface Animation {
    /**
     * is in charge of the logic.
     * @param d a DrawSurface.
     */
    void doOneFrame(DrawSurface d);

    /**
     * is in charge of stopping condition.
     * @return true if should stop, false otherwise.
     */
    boolean shouldStop();
}
