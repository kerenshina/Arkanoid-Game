package animation;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import game.Ass7Game;
import game.Counter;
import sprites.SpriteCollection;
import java.awt.Color;


/**
 * CountdownAnimation class.
 *
 * @author shinake
 */
public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private Counter countDown;
    private Boolean stop;


    /**
     * constructor.
     * @param numOfSeconds number of second to display gameScreen.
     * @param countFrom the number of seconds we want to display as countdown.
     * @param gameScreen the given game screen we want to display.
     */
    public CountdownAnimation(double numOfSeconds,
                              int countFrom,
                              SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.countDown = new Counter(countFrom);
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.gameScreen.drawAllOn(d);
        Sleeper sleep = new Sleeper();
        int i = this.countDown.getValue() + 1;
        if (i == 0) {
            this.stop = true;
            return;
        } else {
            d.setColor(Color.WHITE);
            d.drawText(Ass7Game.WIDTH / 2, Ass7Game.HEIGHT / 2, String.valueOf(i - 1), 70);
        }
        sleep.sleepFor((long) (1000 / (int) (this.countFrom / this.numOfSeconds)));
        this.countDown.decrease(1);
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}