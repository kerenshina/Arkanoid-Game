package levels;


import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * LevelSpecificationReader class.
 * @author shinake
 */
public class LevelSpecificationReader {

    /**
     * get information from reader.
     * @param reader java.io.Reader
     * @return list of levels.
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        List<LevelInformation> levelsList = new ArrayList<>();
        List<String> records = new ArrayList<String>();
        boolean loadLevelD = false;
        boolean loadBlocksD = false;
        Map<String, String> levelDefinitions = null;
        List<String> blocksDefinitions = null;

        try {
            BufferedReader buff = new BufferedReader(reader);
            String line;
            while ((line = buff.readLine()) != null) {
                records.add(line);
            }
            buff.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String s : records) {
            if (s.length() == 0 || s.startsWith("#")) {
                continue;
            } else {
                if (s.equals("START_LEVEL")) {
                    loadLevelD = true;
                    levelDefinitions = new TreeMap<>();
                } else if (s.equals("START_BLOCKS")) {
                    loadLevelD = false;
                    loadBlocksD = true;
                    blocksDefinitions = new ArrayList<>();
                } else if (s.equals("END_BLOCKS")) {
                    loadBlocksD = false;
                } else if (s.equals("END_LEVEL")) {
                    levelsList.add(new LevelCreator(levelDefinitions, blocksDefinitions));
                } else {
                    if (loadLevelD) {
                        String detailName = s.substring(0, s.indexOf(':'));
                        String detailValue = s.substring(s.indexOf(':') + 1);
                        levelDefinitions.put(detailName, detailValue);
                    } else if (loadBlocksD) {
                        blocksDefinitions.add(s);
                    } else {
                        return null;
                    }
                }
            }
        }
        return levelsList;
    }
}
