package menu;

import biuoop.DrawSurface;
import game.Ass7Game;
import sprites.Sprite;

import java.awt.Color;

/**
 * MenuBackground class.
 *
 * @author shinake
 */
public class MenuBackground implements Sprite {

    private int numOfTasks;

    /**
     * constructor.
     * @param num the number of tasks in menu.
     */
    public MenuBackground(int num) {
        this.numOfTasks = num;
    }
    @Override
    public void drawOn(DrawSurface d) {
        int letterWidth = 70;
        int letterHeight = 90;
        int x = 85;

        d.setColor(new Color(148, 201, 169));
        d.fillRectangle(0, 0, Ass7Game.WIDTH, Ass7Game.HEIGHT);
        for (int i = 0; i < 8; i++) {
            d.setColor(new Color(255, 160, 122));
            d.fillRectangle(x, 10, letterWidth, letterHeight);
            d.setColor(new Color(250, 185, 147));
            d.fillRectangle(x, 10, letterWidth, 20);

            d.setColor(new Color(148, 201, 169));

            if (i <= 3) {
                d.fillRectangle(x + ((letterWidth - 10) / 2), 40, 10, 30);
                d.fillRectangle(x + ((letterWidth - 10) / 2), 80, 10, 20);
                if (i > 0 && i < 3) {
                    d.fillRectangle(x + letterWidth - 10, 70, 10, 10);
                }
            }
            if (i == 2 || i == 4) {
                d.fillRectangle(x + ((letterWidth - 10) / 2), 10, 10, 20);
                if (i == 2) {
                    d.fillRectangle(x + ((letterWidth - 10) / 2), 30, 10, 10);
                } else {
                    d.fillRectangle(x + ((letterWidth - 10) / 2) - 10, 10, 20, 10);
                    d.fillRectangle(x + ((letterWidth - 10) / 2), 80, 10, 20);
                    d.fillRectangle(x + ((letterWidth - 10) / 2), 90, 20, 10);
                }
            }
            if (i == 5 || i == 7) {
                d.fillRectangle(x + ((letterWidth - 10) / 2), 30, 10, 60);
                if (i == 7) {
                    d.fillRectangle(x + ((letterWidth + 10) / 2), 40, 10, 40);
                    d.fillRectangle(x + letterWidth - 10, 10, 10, 10);
                    d.fillRectangle(x + letterWidth - 10, 90, 10, 10);
                }
            }
            if (i == 6) {
                d.fillRectangle(x, 40, 20, 30);
                d.fillRectangle(x + letterWidth - 20, 40, 20, 30);
            }
            x += letterWidth + 10;
        }

        d.setColor(new Color(250, 185, 147));
            d.fillRectangle(0, 220, Ass7Game.WIDTH, 20);
            d.fillRectangle(0, 410, Ass7Game.WIDTH, 20);
    }

    @Override
    public void timePassed() {

    }
}
