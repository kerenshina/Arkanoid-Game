package collision;

import game.Counter;
import game.GameLevel;
import sprites.Ball;
import sprites.Block;

/**
 * BlockRemover class.
 *
 * @author shinake
 */
public class BlockRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter availableBlocks;

    /**
     * constructor.
     *
     * @param gameLevel            this gameLevel.
     * @param availableBlocks the number of blocks that were removed.
     */
    public BlockRemover(GameLevel gameLevel, Counter availableBlocks) {
        this.gameLevel = gameLevel;
        this.availableBlocks = availableBlocks;
    }

    /**
     * Blocks that are hit and reach 0 hit-points should be removed
     * from the gameLevel. Remember to remove this listener from the block
     * that is being removed from the gameLevel.
     *
     * @param beingHit the block that's being hit.
     * @param hitter   the Ball that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.setHits(beingHit.numOfHits() - 1);
        if (beingHit.numOfHits() == 0) {
            beingHit.removeHitListener(this);
            beingHit.removeFromGame(gameLevel);
            this.availableBlocks.decrease(1);
        }
    }
}
