package levels;
import biuoop.DrawSurface;
import game.Ass7Game;
import sprites.Sprite;

import java.awt.Color;
import java.awt.Image;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * BackgroundCreator class.
 *
 * @author shinake
 */
public class BackgroundCreator implements Sprite {
    private Image image;
    private Color color;

    /**
     * Constructor that Sets the background for this level.
     * @param background the background definition. either a color or a path to an image.
     */
    public BackgroundCreator(String background) {
        String typeOfBackground = background.substring(0, background.indexOf('('));
        if (typeOfBackground.equals("color")) {
            this.color = new ColorsParser().colorFromString(background);
        } else {
            String imageFilePath = background.substring(background.indexOf('(') + 1, background.length() - 1);
            try {
                this.image = ImageIO.read(new File("resources/" + imageFilePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        if (this.color == null) {
            d.drawImage(0, 0, this.image);
        } else {
            d.setColor(this.color);
            d.fillRectangle(0, 0, Ass7Game.WIDTH, Ass7Game.HEIGHT);
        }
    }

    @Override
    public void timePassed() {

    }
}
