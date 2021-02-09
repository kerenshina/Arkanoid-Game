package animation;

import biuoop.DrawSurface;
import game.Ass7Game;

import java.awt.Color;

/**
 * EndScreen class.
 *
 * @author shinake
 */
public class EndScreen implements Animation {
    private boolean stop;
    private boolean win;
    private int score;

    /**
     * constructor.
     * @param score int
     * @param win boolean
     */
    public EndScreen(int score, boolean win) {
        this.stop = false;
        this.score = score;
        this.win = win;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        String scoreStr = Integer.toString(this.score);
        d.setColor(Color.pink);
        d.fillRectangle(0, 0, Ass7Game.WIDTH, Ass7Game.HEIGHT);
        d.setColor(Color.BLACK);
        if (!this.win) {
            String loseStr = "Game Over. Your score is " + scoreStr;
            d.drawText(10, d.getHeight() / 2, loseStr, 32);
        } else {
            String winStr = "You Win! Your score is " + scoreStr;
            d.drawText(10, d.getHeight() / 2, winStr, 32);
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
