package sprites;

import biuoop.DrawSurface;
import collision.Collidable;
import collision.HitListener;
import collision.HitNotifier;
import collision.Velocity;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Block class.
 *
 * @author shinake
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle blockShape;
    private int hits;
    private Map<Integer, Color> colors;
    private Map<Integer, Image> images;
    private Color stroke;
    private GameLevel gameLevel;
    private List<HitListener> hitListeners = new ArrayList<HitListener>();

    /**
     * contractor.
     *
     * @param blockShape the block's shape (a rectangle).
     * @param colors      the block's colors.
     * @param images      the block's images.
     * @param stroke      the block's stroke's color.
     * @param hits        the block's number of hits.
     */
    public Block(Rectangle blockShape, Map<Integer, Color> colors, Map<Integer, Image> images, Color stroke, int hits) {
        this.blockShape = blockShape;
        this.colors = colors;
        this.images = images;
        this.stroke = stroke;
        this.hits = hits;
    }

    /**
     * @return the number of hits that the block holds.
     */
    public int numOfHits() {
        return this.hits;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.blockShape;
    }

    /**
     * Sets the gameLevel to block.
     *
     * @param gameLevel1 this gameLevel.
     */
    public void setGameLevel(GameLevel gameLevel1) {
        this.gameLevel = gameLevel1;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDX();
        double dy = currentVelocity.getDY();
        Velocity newVelocity;

        if (this.blockShape.getUpperLine().pointOnLine(collisionPoint)
                || this.blockShape.getLowerLine().pointOnLine(collisionPoint)
                || (collisionPoint == this.blockShape.leftLowerPoint())
                || (collisionPoint == this.blockShape.rightLowerPoint())) {
            newVelocity = new Velocity(dx, -dy);
        } else if (this.blockShape.getLeftLine().pointOnLine(collisionPoint)
                || this.blockShape.getRightLine().pointOnLine(collisionPoint)
                || (collisionPoint == this.blockShape.getUpperLeft())
                || (collisionPoint == this.blockShape.rightUpperPoint())) {
            newVelocity = new Velocity(-dx, dy);
        } else {
            newVelocity = new Velocity(-dx, -dy);
        }

        notifyHit(hitter);
        return newVelocity;
    }

    @Override
    public void drawOn(DrawSurface d) {
        int x = (int) this.blockShape.getUpperLeft().getX();
        int y = (int) this.blockShape.getUpperLeft().getY();
        int width = (int) this.blockShape.getWidth();
        int height = (int) this.blockShape.getHeight();

        if (this.colors.containsKey(this.hits)) {
            d.setColor(this.colors.get(this.hits));
            d.fillRectangle(x, y, width, height);
        } else if (this.colors.containsKey(1)) {
            d.setColor(this.colors.get(1));
            d.fillRectangle(x, y, width, height);
        } else if (this.images.containsKey(this.hits)) {
            d.drawImage(x, y, this.images.get(this.hits));
        } else {
            d.drawImage(x, y, images.get(1));
        }

        if (this.stroke != null) {
            d.setColor(this.stroke);
            d.drawRectangle(x, y, width, height);
        }
    }

    @Override
    public void timePassed() {
    }

    /**
     * add the block to the list of sprites and collidables.
     */
    public void addToGame() {
        gameLevel.addCollidable(this);
        gameLevel.addSprite(this);

    }

    /**
     * removes this block from the gameLevel.
     *
     * @param gameLevel1 this gameLevel.
     */
    public void removeFromGame(GameLevel gameLevel1) {
        gameLevel1.removeCollidable(this);
        gameLevel1.removeSprite(this);
    }

    /**
     * sets the number of hits.
     * @param num the number we want to put instead the number of hits we have.
     */
    public void setHits(int num) {
        this.hits = num;
    }
    @Override
    public void addHitListener(HitListener hl) {
        if (hl == null) {
            System.exit(-3);
        } else {
            this.hitListeners.add(hl);
        }
    }

    /**
     * @return the hitListeners list that the block holds.
     */
    public List<HitListener> hitListeners() {
        return this.hitListeners;
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * will be called whenever a hit() occurs, and notifiers all
     * of the registered HitListener objects by calling their hitEvent method.
     *
     * @param hitter the ball that hit the block.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
}
