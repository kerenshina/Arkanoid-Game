package sprites;

import biuoop.DrawSurface;
import game.Ass7Game;
import game.Counter;

import java.awt.Color;

/**
 * LivesIndicator class.
 *
 * @author shinake
 */
public class LivesIndicator implements Sprite {
    private Counter lives;

    /**
     * Constructor.
     *
     * @param lives the counter that counts the lives of the player.
     */
    public LivesIndicator(Counter lives) {
        this.lives = lives;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.RED);
        String livesStr = "Lives: " + Integer.toString(this.lives.getValue());
        d.drawText(Ass7Game.WIDTH / 4, 12, livesStr, 15);
    }

    @Override
    public void timePassed() {
    }
}
