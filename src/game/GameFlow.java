package game;


import animation.AnimationRunner;
import animation.EndScreen;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.DialogManager;
import levels.LevelInformation;

import java.util.List;

/**
 * GameFlow class.
 * @author shinake
 */
public class GameFlow {
    private HighScoresTable highScores;
    private int lives;
    private int score;
    private GUI gui;
    private KeyboardSensor keyboardSensor;
    private AnimationRunner runner;

    /**
     * constructor.
     * @param highScores HighScoresTable
     * @param gui GUI
     * @param runner AnimationRunner
     */
    public GameFlow(HighScoresTable highScores, GUI gui, AnimationRunner runner) {
        this.highScores = highScores;
        this.lives = 7;
        this.score = 0;
        this.gui = gui;
        this.keyboardSensor = gui.getKeyboardSensor();
        this.runner = runner;
    }

    /**
     * runs the given levels on GameLevel class.
     * @param levels list of levels.
     */
    public void runLevels(List<LevelInformation> levels) {

        for (LevelInformation levelInfo : levels) {

            int numOfBalls = levelInfo.numberOfBalls();
            GameLevel level = new GameLevel(levelInfo, lives, this.score, this.gui, this.runner, numOfBalls);

            level.initialize();
            while (level.getLives().getValue() > 0 && level.getBlocksAvailable().getValue() != 0) {
                level.playOneTurn();
               if (level.getBallsAvailable().getValue() == 0) {
                    level.getLives().decrease(1);
                    level.getBallsAvailable().increase(numOfBalls);
                }
            }
            this.lives = level.getLives().getValue();
            this.score = level.getScore().getValue();
            if (lives == 0) {
                runner.run(new KeyPressStoppableAnimation(gui.getKeyboardSensor(), KeyboardSensor.SPACE_KEY,
                        new EndScreen(score, false)));
                break;
            }
        }
        if (lives != 0) {
            runner = new AnimationRunner(gui);
            runner.run(new KeyPressStoppableAnimation(gui.getKeyboardSensor(), KeyboardSensor.SPACE_KEY,
                    new EndScreen(score, true)));
        }
        DialogManager dialog = gui.getDialogManager();
        String player = dialog.showQuestionDialog("Name", "What is your name?", "");
        this.highScores.add(new ScoreInfo(player, score));
        HighScoresAnimation highScoresA = new HighScoresAnimation(this.highScores);
        KeyPressStoppableAnimation key = new KeyPressStoppableAnimation(keyboardSensor, "space", highScoresA);
        runner.run(key);
    }
}
