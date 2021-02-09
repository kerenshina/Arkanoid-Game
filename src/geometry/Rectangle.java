package geometry;

import biuoop.DrawSurface;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Rectangle class.
 *
 * @author shinake
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;
    // rectangle lines

    /**
     * @param upperLeft the upper left point of the rectangle
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * @param xUpperLeft x value of upper left point.
     * @param yUpperLeft y value of upper left point.
     * @param width      the width of the rectangle
     * @param height     the height of the rectangle
     */
    public Rectangle(double xUpperLeft, double yUpperLeft, double width, double height) {
        this.upperLeft = new Point(xUpperLeft, yUpperLeft);
        this.width = width;
        this.height = height;
    }

    /**
     * @return the upper border of the rectangle.
     */
    public Line getUpperLine() {
        return new Line(this.upperLeft, rightUpperPoint());
    }

    /**
     * @return the lower border of the rectangle
     */
    public Line getLowerLine() {

        return new Line(this.leftLowerPoint(), this.rightLowerPoint());
    }

    /**
     * @return the right border of the rectangle
     */
    public Line getRightLine() {
        return new Line(this.rightUpperPoint(), this.rightLowerPoint());
    }

    /**
     * @return the left border of the rectangle
     */
    public Line getLeftLine() {
        return new Line(this.upperLeft, this.leftLowerPoint());
    }

    /**
     * Return a (possibly empty) List of intersection points
     * with the specified line.
     *
     * @param line the line that being checked.
     * @return a list of points.
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        Line upLine = this.getUpperLine();
        Line lowLine = this.getLowerLine();
        Line rightLine = this.getRightLine();
        Line leftLine = this.getLeftLine();
        java.util.List<Point> intersectionPointsList = new ArrayList<Point>();

        if (upLine.isIntersecting(line)) {
            intersectionPointsList.add(upLine.intersectionWith(line));
        }
        if (lowLine.isIntersecting(line)) {
            intersectionPointsList.add(lowLine.intersectionWith(line));
        }
        if (leftLine.isIntersecting(line)) {
            intersectionPointsList.add(leftLine.intersectionWith(line));
        }
        if (rightLine.isIntersecting(line)) {
            intersectionPointsList.add(rightLine.intersectionWith(line));
        }

        if (intersectionPointsList.size() == 0) {
            return null;
        }

        return intersectionPointsList;
    }

    /**
     * @return the width of the rectangle
     */
    public double getWidth() {

        return this.width;
    }

    /**
     * @return the height of the rectangle
     */
    public double getHeight() {

        return this.height;
    }

    /**
     * @return the upper-left point of the rectangle.
     */
    public Point getUpperLeft() {

        return this.upperLeft;
    }

    /**
     * draws a rectangle.
     *
     * @param surface the surface we draw on.
     * @param c1      color of fill
     */
    public void drawRectangle(DrawSurface surface, java.awt.Color c1) {
        surface.setColor(c1);
        surface.fillRectangle((int) this.upperLeft.getX(), (int) this.upperLeft.getY(),
                (int) this.width, (int) this.height);
        surface.setColor(Color.BLACK);
        surface.drawRectangle((int) this.upperLeft.getX(), (int) this.upperLeft.getY(),
                (int) this.width, (int) this.height);

    }

    /**
     * @return the left Lower Point of the rectangle.
     */
    public Point leftLowerPoint() {
        return new Point(this.getUpperLeft().getX(), this.getUpperLeft().getY() + this.height);
    }

    /**
     * @return the right Lower Point of the rectangle.
     */
    public Point rightLowerPoint() {
        return new Point(this.getUpperLeft().getX() + this.width,
                this.getUpperLeft().getY() + this.height);
    }

    /**
     * @return the right Upper Point of the rectangle.
     */
    public Point rightUpperPoint() {
        return new Point(this.getUpperLeft().getX() + this.width,
                this.getUpperLeft().getY());
    }

    /**
     * changes the rectangle position (by replacing its upper left point with a new one).
     *
     * @param upperLeftNew the new upper left point to set.
     */
    public void changePosition(Point upperLeftNew) {
        this.upperLeft = upperLeftNew;
    }
}
