package game;

import collision.HitListener;
import sprites.Ball;
import sprites.Block;

/**
 * ScoreTrackingListener class.
 *
 * @author shinake
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * constructor.
     *
     * @param scoreCounter the counter of the ScoreTrackingListener.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.numOfHits() > 0) {
            currentScore.increase(5);
        } else {
            currentScore.increase(10);
        }
    }
}
