package collision;

import geometry.Point;
import geometry.Rectangle;
import sprites.Ball;

/**
 * Collidable interface.
 *
 * @author shinake
 */
public interface Collidable {

    /**
     * @return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();


    /**
     * @param collisionPoint  the collision point
     * @param currentVelocity the given velocity.
     * @param hitter          the ball that's doing the hitting.
     * @return the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}