package game;

import biuoop.GUI;
import biuoop.KeyboardSensor;
import levels.LevelInformation;
import levels.LevelSets;
import menu.Menu;
import menu.Task;
import menu.MenuAnimation;
import menu.ShowHiScoresTask;
import menu.QuitTask;
import animation.AnimationRunner;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Ass7Game class.
 *
 * @author shinake
 */
public class Ass7Game {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    /**
     * @param args commandline arguments.
     */
    public static void main(String[] args) {
        GUI gui = new GUI("Arkanoid Game!", WIDTH, HEIGHT);
        AnimationRunner runner = new AnimationRunner(gui);
        HighScoresTable highScoresTable = new HighScoresTable(4);
        KeyboardSensor sensor = gui.getKeyboardSensor();
        KeyPressStoppableAnimation highScores = new KeyPressStoppableAnimation(sensor, KeyboardSensor.SPACE_KEY,
                new HighScoresAnimation(highScoresTable));

        File scoresFile = new File("highscores");
        try {
            if (!scoresFile.createNewFile()) {
                highScoresTable.load(scoresFile);
            }
        } catch (IOException e) {
            System.out.println("error creating table file");
        }

        String levelSetsPath;
        if (args.length != 0) {
            levelSetsPath = args[0];
        } else {
            levelSetsPath = "level_sets.txt";
        }
        LevelSets levelSets = new LevelSets(levelSetsPath);
        Map<String, String> levelSetsMap = levelSets.getKeyToLevelSetMap();


        Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>("Main Menu:", sensor, runner);
        Menu<Task<Void>> subMenu = new MenuAnimation<>("Choose game difficulty", sensor, runner);

        for (Map.Entry<String, String> entry : levelSetsMap.entrySet()) {
            subMenu.addSelection(entry.getKey(), entry.getValue(), new Task<Void>() {
                @Override
                public Void run() {
                    GameFlow game = new GameFlow(highScoresTable, gui, runner);
                    List<LevelInformation> levels = levelSets.getLevels(entry.getKey());
                    game.runLevels(levels);
                    return null;
                }
            });
        }

        menu.addSubMenu("s", "Start a new game", subMenu);
        menu.addSelection("h", "See the high scores", new ShowHiScoresTask(runner, highScores));
        menu.addSelection("q", "Quit", new QuitTask<Void>(highScoresTable, scoresFile));



        while (true) {
            runner.run(menu);
            Task task = menu.getStatus();
            task.run();
        }
    }
}