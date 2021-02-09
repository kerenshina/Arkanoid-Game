package animation;
import biuoop.DrawSurface;
import game.Ass7Game;

import java.awt.Color;

/**
 * PauseScreen class.
 *
 * @author shinake
 */
public class PauseScreen implements Animation {
    private boolean stop;

    /**
     * constructor.
     */
    public PauseScreen() {
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.setColor(Color.GRAY);
        d.fillRectangle(0, 0, Ass7Game.WIDTH, Ass7Game.HEIGHT);
        d.setColor(Color.white);
        d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
