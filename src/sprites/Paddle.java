package sprites;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import collision.Collidable;
import collision.Velocity;
import game.Ass7Game;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;

/**
 * Paddle class.
 *
 * @author shinake
 */
public class Paddle implements Collidable, Sprite {
    private biuoop.KeyboardSensor keyboard;
    private int paddleSpeed;
    private int paddleWidth;
    private Rectangle paddleShape;

    /**
     * constructor.
     *
     * @param paddleWidth the paddle width.
     * @param keyboard    keyboard sensor.
     * @param paddleSpeed the speed of the paddle.
     */
    public Paddle(KeyboardSensor keyboard, int paddleSpeed, int paddleWidth) {
        this.keyboard = keyboard;
        this.paddleWidth = paddleWidth;
        this.paddleShape = new Rectangle((Ass7Game.WIDTH - paddleWidth) / 2, Ass7Game.HEIGHT - 50,
                (double) paddleWidth, 20);
        this.paddleSpeed = (int) paddleSpeed / 60;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.paddleShape;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Rectangle[] paddleHits = new Rectangle[5];
        double hitWidth = paddleWidth / 5;
        Point temp = this.paddleShape.getUpperLeft();
        double curDX = currentVelocity.getDX();
        double curDY = currentVelocity.getDY();
        double speed = Math.sqrt(Math.pow(curDX, 2) + Math.pow(curDY, 2));
        double angle = currentVelocity.getAngle();
        int i = 0;

        if (!(paddleShape.getLeftLine().pointOnLine(collisionPoint)
                || paddleShape.getRightLine().pointOnLine(collisionPoint))) {
            while (i < 5) {
                paddleHits[i] = new Rectangle(temp.getX(), temp.getY(), temp.getX() + hitWidth, 20);
                temp = paddleHits[i].rightUpperPoint();
                if (paddleHits[i].getUpperLine().pointOnLine(collisionPoint)) {
                    return Velocity.fromAngleAndSpeed(angle + (300 + i * 30), speed * 60);
                }
                i++;
            }
            return Velocity.fromAngleAndSpeed(angle + 180, speed);
        } else {
            return new Velocity(-curDX, curDY);
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.PINK);
        d.fillRectangle((int) paddleShape.getUpperLeft().getX(), (int) paddleShape.getUpperLeft().getY(),
                paddleWidth, (int) paddleShape.getHeight());
        d.setColor(Color.BLACK);
        d.drawRectangle((int) paddleShape.getUpperLeft().getX(), (int) paddleShape.getUpperLeft().getY(),
                paddleWidth, (int) paddleShape.getHeight());
    }

    @Override
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }


    /**
     * move the paddle left.
     */
    public void moveLeft() {
        double x = this.paddleShape.getUpperLeft().getX() - paddleSpeed;
        double y = this.paddleShape.getUpperLeft().getY();
        int tempSpeed;
        Point newPosition = new Point(x, y);
        if (newPosition.getX() >= 25) {
            this.paddleShape.changePosition(newPosition);
        } else {
            tempSpeed = paddleSpeed;
            while (newPosition.getX() < 25) {
                tempSpeed--;
                x = this.paddleShape.getUpperLeft().getX() - (tempSpeed);
                newPosition = new Point(x, y);
            }
            this.paddleShape.changePosition(newPosition);
        }
    }

    /**
     * moves the paddle right.
     */
    public void moveRight() {
        double x = this.paddleShape.getUpperLeft().getX() + paddleSpeed;
        double y = this.paddleShape.getUpperLeft().getY();
        int tempSpeed;
        Point newPosition = new Point(x, y);
        if (newPosition.getX() <= (Ass7Game.WIDTH - this.paddleShape.getWidth() - 25)) {
            this.paddleShape.changePosition(newPosition);
        } else {
            tempSpeed = paddleSpeed;
            while (newPosition.getX() > (Ass7Game.WIDTH - this.paddleShape.getWidth() - 25)) {
                tempSpeed--;
                x = this.paddleShape.getUpperLeft().getX() + (tempSpeed);
                newPosition = new Point(x, y);
            }
            this.paddleShape.changePosition(newPosition);
        }
    }

    /**
     * Add this paddle to the game.
     *
     * @param g The game.
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * Remove this paddle from the game.
     *
     * @param g The GameLevel.
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
        g.removeCollidable(this);
    }
}