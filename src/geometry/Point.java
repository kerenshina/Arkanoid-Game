package geometry;

/**
 * Point class.
 *
 * @author shinake
 */
public class Point {
    private double x;
    private double y;

    // constructor

    /**
     * @param x x value of the point.
     * @param y y value of the point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param other other point.
     * @return the distance of this point to the other point
     */
    public double distance(Point other) {
        double x1 = this.x;
        double x2 = other.getX();
        double y1 = this.y;
        double y2 = other.getY();

        return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
    }

    /**
     * @param other the other point.
     * @return true is the points are equal, false otherwise.
     */
    public boolean equals(Point other) {
        double x1 = this.x;
        double x2 = other.getX();
        double y1 = this.y;
        double y2 = other.getY();

        return ((x1 == x2) && (y1 == y2));

    }

    /**
     * @return x value of this point
     */
    public double getX() {
        return this.x;
    }

    /**
     * @return y value of this point
     */
    public double getY() {
        return this.y;
    }
}
