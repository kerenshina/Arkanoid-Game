package sprites;

import biuoop.DrawSurface;
import collision.CollisionInfo;
import collision.Velocity;
import game.Ass7Game;
import game.GameLevel;
import game.GameEnvironment;
import geometry.Line;
import geometry.Point;

import java.awt.Color;

/**
 * Ball class.
 *
 * @author shinake
 */
public class Ball implements Sprite {
    private Point center;
    private int r;
    private Velocity velocity;
    private GameEnvironment gameEnvironment;


    // constructors

    /**
     * one of the ball's constructors.
     *
     * @param center the center point of the ball.
     * @param r      the size of the ball.
     */
    public Ball(Point center, int r) {
        this.center = center;
        this.r = r;
    }

    /**
     * one of the ball's constructors.
     *
     * @param x     the center point's x value.
     * @param y     the center point's y value.
     * @param r     the size of the ball.
     */
    public Ball(int x, int y, int r) {
        this.center = new Point(x, y);
        this.r = r;
    }

    // accessors

    /**
     * @return the x value of the center point.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * @return the y value of the center point.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * draw the ball on the given DrawSurface.
     *
     * @param surface the given DrawSurface.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(Color.white);
        surface.fillCircle(getX(), getY(), this.r);
        surface.setColor(Color.BLACK);
        surface.drawCircle(getX(), getY(), this.r);
    }

    /**
     * create velocity out of dx dy.
     *
     * @param dx the difference between x values of the ball.
     * @param dy the difference between y values of the ball.
     */
    public void setVelocity(double dx, double dy) {

        this.velocity = new Velocity(dx, dy);
    }

    /**
     * @return the velocity of the ball.
     */
    public Velocity getVelocity() {

        return this.velocity;
    }

    /**
     * @param v the velocity of the ball.
     */
    public void setVelocity(Velocity v) {

        this.velocity = v;
    }

    /**
     * @param gameEnvironment1 the game environment that the ball should know.
     */
    public void setGameEnvironment(GameEnvironment gameEnvironment1) {
        this.gameEnvironment = gameEnvironment1;
    }

    /**
     * this method makes the ball move one step.
     */
    public void moveOneStep() {
        Point collisionPoint;
        Point temp;
        Line trajectory;
        Velocity newVelocity;
        double distanceFromColission;
        double velocitySize;
        CollisionInfo closestCollision;

        newVelocity = moveInsideFrame();
        if ((newVelocity.getDX() != this.velocity.getDX()) || (newVelocity.getDY() != this.velocity.getDY())) {
            this.setVelocity(newVelocity);
            this.center = this.velocity.applyToPoint(this.center);
        }
        temp = getVelocity().applyToPoint(this.center);
        velocitySize = Math.sqrt(Math.pow(this.getVelocity().getDX(), 2) + Math.pow(this.getVelocity().getDY(), 2));

        trajectory = new Line(this.center, temp);
        closestCollision = this.gameEnvironment.getClosestCollision(trajectory);
        if (closestCollision != null) {
            collisionPoint = closestCollision.collisionPoint();
            distanceFromColission = this.center.distance(collisionPoint);
            if (distanceFromColission <= this.r + velocitySize) {
                newVelocity = closestCollision.collisionObject().hit(this, collisionPoint, this.getVelocity());
                this.setVelocity(newVelocity);
            }
        }
        this.center = this.getVelocity().applyToPoint(this.center);
    }

    /**
     * this method changes the velocity of the ball if touching the frame borders.
     * @return new velocity.
     */
    public Velocity moveInsideFrame() {
        double dx = this.velocity.getDX();
        double dy = this.velocity.getDY();
        double speed = Math.sqrt((Math.pow(dx, 2) + (Math.pow(dy, 2))));
        int signOfDX;


        if (((center.getX() + r + dx) > Ass7Game.WIDTH - 25) && (dx > 0)) {
            dx = -dx;
            if (dy <= 0.0001 || dy >= -0.0001) {
                signOfDX = (int) (dx / Math.abs(dx));
                dy = Math.sqrt(Math.pow(speed, 2) / 2);
                dx = signOfDX * dy;
            }
        } else if (((center.getX() - r + dx) < 25) && (dx < 0)) {
            dx = -dx;
            if (dy <= 0.0001 || dy >= -0.0001) {
                dy = Math.sqrt(Math.pow(speed, 2) / 2);
                signOfDX = (int) (dx / Math.abs(dx));
                dx = signOfDX * dy;
            }
        }
        if (((center.getY() - r + dy) < 25) && (dy < 0)) {
            dy = -dy;
        }


        return new Velocity(dx, dy);
    }

    @Override
    public void timePassed() {
        moveOneStep();
    }

    /**
     * add the ball to the list of sprites.
     *
     * @param g GameLevel
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}