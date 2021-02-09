package menu;

import game.HighScoresTable;

import java.io.File;
import java.io.IOException;


/**
 * QuitTask class.
 * @param <T> generic
 */
public class QuitTask<T> implements Task {
    private HighScoresTable table;
    private File save;

    /**
     * constructor.
     * @param table the score table we will save.
     * @param fileToSave the file we will save it to.
     */
    public QuitTask(HighScoresTable table, File fileToSave) {
        this.table = table;
        this.save = fileToSave;
    }
    @Override
    public T run() {
        try {
            this.table.save(this.save);
        } catch (IOException e) {
            System.out.println("can't save table");
        }
        System.exit(0);
        return null;
    }
}