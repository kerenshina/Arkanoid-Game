package sprites;

import biuoop.DrawSurface;
import game.Ass7Game;
import game.Counter;

import java.awt.Color;

/**
 * ScoreIndicator class.
 *
 * @author shinake
 */
public class ScoreIndicator implements Sprite {
    private Counter score;

    /**
     * Constructor.
     *
     * @param score the counter that counts the score of the player.
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.RED);
        String scoreStr = "Score: " + Integer.toString(this.score.getValue());
        d.drawText(Ass7Game.WIDTH - Ass7Game.WIDTH / 4, 12, scoreStr, 15);
    }

    @Override
    public void timePassed() {
    }
}
