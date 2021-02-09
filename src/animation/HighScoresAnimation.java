package animation;

import java.awt.Color;

import biuoop.DrawSurface;
import game.Ass7Game;
import game.HighScoresTable;
import game.ScoreInfo;

/**
 * HighScoresAnimation class.
 *
 * @author shinake
 */
public class HighScoresAnimation implements Animation {
    private boolean stop;
    private HighScoresTable scores;

    /**
     * constructor.
     * @param scores HighScoresTable
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scores = scores;
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        int rank = 0;
        d.setColor(Color.ORANGE);
        d.fillRectangle(0, 0, Ass7Game.WIDTH, Ass7Game.HEIGHT);
        d.setColor(Color.BLACK);
        d.drawText(240, 100, "High - Scores!", 40);

        d.setColor(new Color(204, 0, 0));
        d.drawText(285, 200, "name", 30);
        d.drawText(455, 200, "score", 30);
        d.drawText(280, 200, "_________________", 30);

        d.setColor(Color.BLACK);
        for (ScoreInfo s : this.scores.getHighScores()) {
            String playerData = Integer.toString(rank + 1) + ".  " + s.getName();
            String scoreData = Integer.toString(s.getScore());
            d.drawText(280, 240 + 32 * rank, playerData, 30);
            d.drawText(470, 240 + 32 * rank, scoreData, 30);
            rank++;
        }

        d.setColor(new Color(204, 0, 0));
        d.drawText(250, 520, "Press space to continue", 30);
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
