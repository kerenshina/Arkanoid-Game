package game;

import animation.Animation;
import animation.CountdownAnimation;
import animation.KeyPressStoppableAnimation;
import animation.PauseScreen;
import animation.AnimationRunner;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import collision.BallRemover;
import collision.Velocity;
import collision.FrameListener;
import collision.BlockRemover;
import collision.Collidable;
import geometry.Point;
import geometry.Rectangle;
import levels.LevelInformation;
import levels.LevelName;
import sprites.Paddle;
import sprites.Sprite;
import sprites.ScoreIndicator;
import sprites.SpriteCollection;
import sprites.Ball;
import sprites.Block;
import sprites.LivesIndicator;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * GameLevel class.
 *
 * @author shinake
 */
public class GameLevel implements Animation {
    private SpriteCollection sprites;
    private AnimationRunner runner;
    private boolean running;
    private GameEnvironment environment;
    private GUI gui;
    private biuoop.KeyboardSensor keyboardSensor;
    private Counter blocksAvailable;
    private Counter ballsAvailable;
    private Counter score;
    private Counter lives;
    private LevelInformation level;
    private List<Velocity> velocities;

    /**
     * Constructor- creates the sprite collection, environment, gui and keyboard sensor of the game.
     *
     * @param levelInfo  LevelInformation
     * @param numOfLives int
     * @param startScore int
     * @param gui        GUI
     * @param runner     AnimationRunner
     * @param numOfBalls int
     */
    public GameLevel(LevelInformation levelInfo, int numOfLives, int startScore, GUI gui,
                     AnimationRunner runner, int numOfBalls) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = gui;
        this.keyboardSensor = gui.getKeyboardSensor();
        this.level = levelInfo;
        this.blocksAvailable = new Counter(0);
        this.ballsAvailable = new Counter(numOfBalls);
        this.score = new Counter(startScore);
        this.lives = new Counter(numOfLives);
        this.runner = runner;
        this.velocities = levelInfo.initialBallVelocities();
    }

    /**
     * Add a collidable to the game environment.
     *
     * @param c the collidable.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Add a sprite to the sprite collection.
     *
     * @param s the sprite.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Initialize a new game: create the Blocks and Ball (and Paddle) and add them to the game.
     */
    public void initialize() {
        BallRemover ballLis = new BallRemover(this, ballsAvailable);

        addSprite(level.getBackground());
        createFrameBlocks();
        createBlocks();
        createDeathRegion(ballLis);
        addSprite(new ScoreIndicator(score));
        addSprite(new LivesIndicator(lives));
        addSprite(new LevelName(this.level));
    }

    /**
     * creates new frame blocks and add them to game.
     */
    private void createFrameBlocks() {
        int size = 25;
        FrameListener frameListener = new FrameListener();
        Map<Integer, Color> colorMap = new TreeMap<>();
        Map<Integer, Image> imageMap = new TreeMap<>();
        colorMap.put(1, Color.darkGray);
        Block up = new Block(new Rectangle(0, 0, Ass7Game.WIDTH, size),
                colorMap, imageMap, null, 0);
        Block left = new Block(new Rectangle(0, size, size, Ass7Game.HEIGHT - size),
                colorMap, imageMap, null, 0);
        Block right = new Block(new Rectangle(Ass7Game.WIDTH - size, size, size, Ass7Game.HEIGHT - size),
                colorMap, imageMap, null, 0);

        up.setGameLevel(this);
        left.setGameLevel(this);
        right.setGameLevel(this);


        up.addHitListener(frameListener);
        left.addHitListener(frameListener);
        right.addHitListener(frameListener);

        up.addToGame();
        left.addToGame();
        right.addToGame();
    }

    /**
     * creates a death region so when the balls reach there, they'll be removed.
     *
     * @param ballRemoverListener the HitListener that will be notified whenever a ball hits the death-region.
     */
    private void createDeathRegion(BallRemover ballRemoverListener) {
        int size = 10;
        Map<Integer, Color> colorMap = new TreeMap<>();
        Map<Integer, Image> imageMap = new TreeMap<>();
        colorMap.put(1, Color.darkGray);
        Block dB = new Block(new Rectangle(new Point(0, Ass7Game.HEIGHT - size), Ass7Game.WIDTH, size),
                colorMap, imageMap, null, 0);
        dB.setGameLevel(this);
        dB.addHitListener(ballRemoverListener);
        dB.addToGame();
    }

    /**
     * creates the blocks that are not the frame.
     *
     */
    private void createBlocks() {
        List<Block> blocks = this.level.blocks();
        this.blocksAvailable.increase(blocks.size());
        BlockRemover blockLis = new BlockRemover(this, blocksAvailable);
        ScoreTrackingListener scoreListener = new ScoreTrackingListener(score);

        for (Block block : blocks) {
            block.addHitListener(blockLis);
            block.addHitListener(scoreListener);
            block.setGameLevel(this);
            block.addToGame();
        }
    }

    /**
     * Runs one turn of the game -- start the animation loop.
     */
    public void playOneTurn() {
        Paddle paddle = createPaddle();
        List<Ball> balls = createBalls();

        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        this.running = true;
        this.runner.run(this);
        if (blocksAvailable.getValue() == 0) {
            score.increase(100);
        } else {
            paddle.removeFromGame(this);
            for (Ball b : balls) {
                this.removeSprite(b);
            }
        }
    }

    /**
     * Creates the balls.
     *
     * @return List<Ball> list of balls.
     */
    private List<Ball> createBalls() {
        List<Ball> balls = new ArrayList<>();
        int space = (int) (Ass7Game.WIDTH - 50) / (ballsAvailable.getValue() + 1);
        int y = 500;
        int heightDelta = (int) 50 / ballsAvailable.getValue();
        int x = 25 + space;

        for (Velocity v : velocities) {
            Ball b = new Ball(x, y, 5);
            b.setVelocity(v);
            b.setGameEnvironment(this.environment);
            b.addToGame(this);
            balls.add(b);
            if (velocities.indexOf(v) >= (int) ballsAvailable.getValue() / 2) {
                y += heightDelta;
            } else if (velocities.indexOf(v) < (int) (ballsAvailable.getValue() + 1) / 2) {
                y -= heightDelta;
            }
            x += space;
        }
        return balls;
    }

    /**
     * Creates the paddle.
     *
     * @return the paddle that was created.
     */
    private Paddle createPaddle() {
        Paddle paddle = new Paddle(keyboardSensor, level.paddleSpeed(), level.paddleWidth());
        paddle.addToGame(this);
        return paddle;
    }

    /**
     * removes the wanted collidable from list.
     *
     * @param c the wanted collidable.
     */
    public void removeCollidable(Collidable c) {
        environment.removeCollidable(c);
    }

    /**
     * removes the wanted sprite from list.
     *
     * @param s the wanted sprite.
     */
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();

        if (this.ballsAvailable.getValue() == 0) {
            this.running = false;
        }
        if (this.blocksAvailable.getValue() == 0) {
            this.running = false;
        }
        if (this.keyboardSensor.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboardSensor,
                    KeyboardSensor.SPACE_KEY, new PauseScreen()));
        }
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * @return blocksAvailable
     */
    public Counter getBlocksAvailable() {
        return blocksAvailable;
    }

    /**
     * @return lives
     */
    public Counter getLives() {
        return lives;
    }

    /**
     * @return score
     */
    public Counter getScore() {
        return score;
    }

    /**
     * @return ballsAvailable
     */
    public Counter getBallsAvailable() {
        return ballsAvailable;
    }
}