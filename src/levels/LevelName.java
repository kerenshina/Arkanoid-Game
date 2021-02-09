package levels;

import biuoop.DrawSurface;
import game.Ass7Game;
import sprites.Sprite;

import java.awt.Color;

/**
 * LevelName class.
 *
 * @author shinake
 */
public class LevelName implements Sprite {
    private String name;

    /**
     * Constructor.
     * @param level the level that we want to know it's name.
     */
    public LevelName(LevelInformation level) {
        this.name = level.levelName();
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.RED);
        d.drawText(Ass7Game.WIDTH / 2, 12, this.name, 15);
    }

    @Override
    public void timePassed() {
    }
}
