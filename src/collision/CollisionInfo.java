package collision;

import geometry.Point;


/**
 * CollisionInfo class.
 *
 * @author shinake
 */
public class CollisionInfo {
    private Collidable collisionObject;
    private Point collisionPoint;

    /**
     * Constructor.
     *
     * @param collisionObject the object that collided.
     * @param collisionPoint  the point of collision.
     */
    public CollisionInfo(Collidable collisionObject, Point collisionPoint) {
        this.collisionObject = collisionObject;
        this.collisionPoint = collisionPoint;
    }

    /**
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {

        return this.collisionPoint;
    }

    /**
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}