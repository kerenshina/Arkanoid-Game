package collision;

import geometry.Point;

/**
 * Velocity class.
 *
 * @author shinake
 */
public class Velocity {
    private double dx;
    private double dy;

    // constructor

    /**
     * @param dx change in position on the `x` axe.
     * @param dy change in position on the `y` axe.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * @param angle the angle of the velocity vector.
     * @param speed the "size" of the velocity vector.
     * @return velocity with dx and dy fields.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        speed = speed / 60;
        double dx = speed * Math.cos(Math.toRadians(angle - 90));
        double dy = speed * Math.sin(Math.toRadians(angle - 90));
        return new Velocity(dx, dy);
    }

    /**
     * @param p a point with position (x,y).
     * @return a new point with position (x+dx, y+dy)
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + dx, p.getY() + dy);
    }

    /**
     * @return change of the x value.
     */
    public double getDX() {
        return this.dx;
    }

    /**
     * @return change of the y value.
     */
    public double getDY() {
        return this.dy;
    }

    /**
     * @return the angle of the Velocity.
     */
    public double getAngle() {
        return Math.toDegrees(Math.atan(this.dx / this.dy));
    }
}