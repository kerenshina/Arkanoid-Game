package game;

import java.io.Serializable;

/**
 * ScoreInfo class.
 *
 * @author shinake
 */
public class ScoreInfo implements Comparable, Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int score;

    /**
     * constructor.
     * @param name the player's name.
     * @param score the player's final score.
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * @return the player's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the player's score.
     */
    public int getScore() {
        return this.score;
    }

    @Override
    public int compareTo(Object o) {
        if (this.score > ((ScoreInfo) o).getScore()) {
            return -1;
        } else if (this.score < ((ScoreInfo) o).getScore()) {
            return 1;
        } else {
            return 0;
        }
    }
}
