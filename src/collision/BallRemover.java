package collision;

import game.Counter;
import game.GameLevel;
import sprites.Ball;
import sprites.Block;

/**
 * BallRemover class.
 *
 * @author shinake
 */
public class BallRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter ballsRemaind;

    /**
     * constructor.
     *
     * @param gameLevel      the gameLevel.
     * @param deadBalls how many balls.
     */
    public BallRemover(GameLevel gameLevel, Counter deadBalls) {
        this.gameLevel = gameLevel;
        this.ballsRemaind = deadBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        gameLevel.removeSprite(hitter);
        this.ballsRemaind.decrease(1);
    }
}
