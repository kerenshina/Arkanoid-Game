package sprites;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * SpriteCollection class.
 *
 * @author shinake
 */
public class SpriteCollection {
    private List<Sprite> sprites = new ArrayList<Sprite>();
    private Iterator<Sprite> iterator;

    /**
     * Adding a Sprite to the list.
     *
     * @param s the sprite we want to add.
     */
    public void addSprite(Sprite s) {
        sprites.add(s);
    }

    /**
     * Removing a Sprite from the list.
     *
     * @param s the Sprite we want to remove.
     */
    public void removeSprite(Sprite s) {
        makeItr();
        while (iterator.hasNext()) {
            Sprite s1 = iterator.next();
            if (s1.equals(s)) {
                iterator.remove();
                break;
            }
        }
    }

    /**
     * Creating an iterator for this Sprite list.
     */
    private void makeItr() {
        Iterator<Sprite> itr = sprites.iterator();
        this.iterator = itr;
    }

    /**
     * call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        makeItr();
        while (iterator.hasNext()) {
            iterator.next().timePassed();
        }
    }

    /**
     * call drawOn(d) on all sprites.
     *
     * @param d the surface we draw on.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite i : sprites) {
            i.drawOn(d);
        }
    }

    /**
     * @return the sprites list.
     */
    public List<Sprite> spritesList() {
        return this.sprites;
    }
}
