package game;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * HighScoresTable class.
 *
 * @author shinake
 */
public class HighScoresTable implements Serializable {
    private int size;
    private List<ScoreInfo> scoreList;
    private static final long serialVersionUID = 1L;

    /**
     * constructor.
     * @param size the size of the high scores table.
     */
    public HighScoresTable(int size) {
        this.size = size;
        this.scoreList = new ArrayList<ScoreInfo>(size);
    }

    /**
     * Add a high-score.
     * @param score the high-score to be added.
     */
    public void add(ScoreInfo score) {
        int scoreRank;
        scoreRank = this.getRank(score.getScore());
        if (scoreRank <= this.size()) {
            this.scoreList.add(score);
        } else {
            return;
        }
    }

    /**
     * @return table size.
     */
    public int size() {
        return this.size;
    }

    /**
     * sort the high scores list. highest first.
     * @return Return the current high scores list.
     */
    public List<ScoreInfo> getHighScores() {
        Collections.sort(scoreList);
        return this.scoreList;
    }

    /**
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not
     *       be added to the list.
     *
     * @param score the score we want to know it's rank.
     * @return the rank of the current score: where will it
     *         be on the list if added?
     */
    public int getRank(int score) {
        int rank = 1;
        for (ScoreInfo s : this.scoreList) {
            if (score > s.getScore() || s == null) {
                break;
            }
            rank++;
        }
        return rank;
    }

    /**
     * Clears the table.
     */
    public void clear() {
        this.scoreList.clear();
    }

    /**
     * Load table data from file.
     * Current table data is cleared.
     * @param filename the name of the file we want to load the table from.
     * @throws IOException exception.
     */
    public void load(File filename) throws IOException {
        ObjectInputStream objectIn = null;
        try {
            objectIn = new ObjectInputStream(new FileInputStream(filename.getName()));
            HighScoresTable loadedTable = (HighScoresTable) objectIn.readObject();
            this.scoreList = loadedTable.getHighScores();
            this.size = loadedTable.size();
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find file: " + filename.getName());
            HighScoresTable emptyTable = new HighScoresTable(5);
            emptyTable.save(filename);
            this.scoreList = emptyTable.scoreList;
            this.size = emptyTable.size;
        } catch (ClassNotFoundException e) {
            System.err.println("Unable to find class for object in file: " + filename.getName());
        } finally {
            try {
                if (objectIn != null) {
                    objectIn.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename.getName());
            }
        }
    }

    /**
     * Save table data to the specified file.
     * @param filename the file we want to save the table data to.
     * @throws IOException exception.
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream objectOut = null;
        try {
            objectOut = new ObjectOutputStream(new FileOutputStream(filename));
            objectOut.writeObject(this);
            objectOut.flush();
        } catch (IOException e) {
            System.err.println("Failed saving object");
            throw e;
        } finally {
            try {
                if (objectOut != null) {
                    objectOut.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
    }

    /**
     * Read a table from file and return it. If the file does not exist, or
     * there is a problem with reading it, an empty table is returned.
     * @param filename File
     * @return table
     * @throws IOException
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable emptyTable = new HighScoresTable(5);
        try {
            if (!filename.exists()) {
                return emptyTable;
            }
            emptyTable.load(filename);
        } catch (IOException e) {
            System.err.println("Failed closing file: " + filename.getName());
            return new HighScoresTable(5);
        }
        return emptyTable;
    }
}