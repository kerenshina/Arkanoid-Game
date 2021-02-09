package geometry;

import java.util.List;

/**
 * Line class.
 *
 * @author shinake
 */
public class Line {
    private Point start;
    private Point end;

    // constructors

    /**
     * @param start start point.
     * @param end   end point.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * @param x1 x value of start point.
     * @param y1 y value of start point.
     * @param x2 x value of end point.
     * @param y2 y value of end point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);

    }

    /**
     * @return the length of the line
     */
    public double length() {
        double length = start.distance(end);
        return length;
    }

    /**
     * @return the middle point of the line.
     */
    public Point middle() {
        double middleX, middleY;

        middleX = (start.getX() + end.getX()) / 2;
        middleY = (start.getY() + end.getY()) / 2;

        Point middle = new Point(middleX, middleY);
        return middle;
    }

    /**
     * @return the start point of the line
     */
    public Point start() {
        return this.start;
    }

    /**
     * @return the end point of the line
     */
    public Point end() {
        return this.end;
    }

    /**
     * @param other other line.
     * @return true if the lines intersect, false otherwise.
     */
    public boolean isIntersecting(Line other) {
        Point m = this.intersectionWith(other);
        if (m != null) {
            return true;
        }
        return false;
    }

    /**
     * calculates slope for line if there is.
     *
     * @return the slope of the line if exists.
     */
    /**
     * calculates slope for line if there is.
     *
     * @return the slope of the line if exists.
     */
    /*public double findSlope() {
        double dx = this.start().getX() - this.end().getX();
        double dy = this.start().getY() - this.end().getY();
        if (dx != 0) {
            return dy / dx;
        }
        return Double.MAX_VALUE;

    }*/

    /**
     * @param other other line.
     * @return the intersection point if the lines intersect, and null otherwise.
     */
    public Point intersectionWith(Line other) {
        double xValue;
        double yValue;
        double m1;
        double m2;
        Point startOther;
        Point endOther;

        if (other != null) {
            startOther = other.start();
            endOther = other.end();
        } else {
            return null;
        }


        if (this.length() == 0) {
            m1 = 0;
        } else if (start.getX() == end.getX()) {
            m1 = Double.MAX_VALUE;
        } else {
            m1 = (start.getY() - end.getY()) / (start.getX() - end.getX());
        }

        if (other.length() == 0) {
            m2 = 0;
        } else if (startOther.getX() == endOther.getX()) {
            m2 = Double.MAX_VALUE;
        } else {
            m2 = (startOther.getY() - endOther.getY()) / (startOther.getX() - endOther.getX());
        }

        double y1 = start.getY() - (m1 * start.getX());
        double y2 = startOther.getY() - (m2 * startOther.getX());

        if ((this.length() == 0) && (other.length() == 0) && startOther.equals(this.start)) {
            return this.start;
        }

        if (m1 == m2) {
            if (this.pointOnLine(startOther)) {
                return startOther;
            } else if (other.pointOnLine(this.start)) {
                return this.start;
            } else {
                return null;
            }
        } else if (m1 == Double.MAX_VALUE) {
            xValue = this.start.getX();
            yValue = m2 * xValue + y2;
        } else if (m2 == Double.MAX_VALUE) {
            xValue = startOther.getX();
            yValue = m1 * xValue + y1;
        } else {
            xValue = (y2 - y1) / (m1 - m2);
            yValue = m1 * xValue + y1;
        }

        Point intersection = new Point(xValue, yValue);
        if (pointOnLine(intersection) && other.pointOnLine(intersection)) {
            return intersection;
        } else {
            return null;
        }
    }


    /**
     * Checks if a given point is on a given line.
     *
     * @param p the given point.
     * @return true if it is, false if isn't.
     */
    public boolean pointOnLine(Point p) {
        if (p.getX() <= Math.max(this.start.getX(), this.end.getX())
                && p.getX() >= Math.min(this.start.getX(), this.end.getX())
                && p.getY() <= Math.max(this.start.getY(), this.end.getY())
                && p.getY() >= Math.min(this.start.getY(), this.end.getY())) {
            return true;
        }
        return false;
    }

    /**
     * @param other other line.
     * @return true if the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        Point startOther = other.start();
        Point endOther = other.end();

        return (start.getX() == startOther.getX())
                && (start.getY() == startOther.getY())
                && (end.getX() == endOther.getX())
                && (end.getY() == endOther.getY());
    }

    /**
     * @param rect a rectangle.
     * @return If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the start of the line.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        double minDistance = this.length();
        List<Point> intersectionPointsList = rect.intersectionPoints(this);
        Point minIntersectionPoint = null;
        if (intersectionPointsList == null) {
            return null;
        }
        for (Point p : intersectionPointsList) {
            if (this.start.distance(p) <= minDistance) {
                minIntersectionPoint = p;
                minDistance = this.start.distance(p);
            }
        }
        return minIntersectionPoint;
    }

    /**
     * This method changes the line's end point as needed.
     *
     * @param changeX the change of x value of end point.
     * @param changeY the change of y value of end point.
     */
    public void changeLineLength(double changeX, double changeY) {
        this.end = new Point(changeX, changeY);
    }

}
