package levels;

import java.io.IOException;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.LineNumberReader;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * LevelSets class.
 * @author shinake
 */
public class LevelSets {

    private String path;
    private Map<String, String> keyToLevelSet;
    private Map<String, String> keyToLevelsPath;

    /**
     * constructor.
     * @param path the path to level_sets.txt file.
     */
    public LevelSets(String path) {
        this.path = path;
        this.keyToLevelSet = new TreeMap<>();
        this.keyToLevelsPath = new TreeMap<>();
    }

    /**
     * @param setKey key of the requested type of level set (difficulty)
     * @return a list of ready to play levels.
     */
    public List<LevelInformation> getLevels(String setKey) {
        try {
            java.io.Reader reader = new FileReader("resources/" + this.keyToLevelsPath.get(setKey));
            LevelSpecificationReader levelSpecificationReader = new LevelSpecificationReader();
            List<LevelInformation> levels = levelSpecificationReader.fromReader(reader);
            return levels;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException f) {
            f.printStackTrace();
            return null;
        }
    }

    /**
     * reads a level_sets.txt file and maps the information given to the appropriate map.
     */
    public void analyzeLevels() {
        LineNumberReader reader;
        try {
            reader = new LineNumberReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        try {
            String thisLine = reader.readLine();
            String key = null;
            while (thisLine != null) {
                if (thisLine.length() != 0) {
                    if (reader.getLineNumber() % 2 == 1) {
                        key = Character.toString(thisLine.charAt(0));
                        this.keyToLevelSet.put(key, thisLine.substring(2));
                    } else if (key != null) {
                        this.keyToLevelsPath.put(key, thisLine);
                    }
                }
                thisLine = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @return key->LevelSet map.
     */
    public Map<String, String> getKeyToLevelSetMap() {
        if (this.keyToLevelSet.isEmpty()) {
            this.analyzeLevels();
        }
        return keyToLevelSet;
    }
}
