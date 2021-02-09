package collision;

import sprites.Ball;
import sprites.Block;

/**
 * HitListener interface.
 *
 * @author shinake
 */
public interface HitListener {

    /**
     * This method is called whenever the beingHit object is hit.
     *
     * @param beingHit the block that's being hit.
     * @param hitter   the Ball that's doing the hitting.
     */
    void hitEvent(Block beingHit, Ball hitter);
}
