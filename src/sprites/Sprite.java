package sprites;

import biuoop.DrawSurface;

/**
 * Sprite interface.
 *
 * @author shinake
 */
public interface Sprite {

    /**
     * Draw the sprite to the screen.
     *
     * @param d the surface that we want to draw on.
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     */
    void timePassed();
}