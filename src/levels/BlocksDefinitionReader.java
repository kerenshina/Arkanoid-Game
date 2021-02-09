package levels;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * BlocksDefinitionReader class.
 * @author shinake
 */
public class BlocksDefinitionReader {
    /**
     * creates BlocksFromSymbolsFactory based on the information written in a file linked by given a reader object.
     * @param reader the object that is linked to the information file.
     * @return BlocksFromSymbolsFactory.
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        BlocksDefinitionReader definitionReader = new BlocksDefinitionReader();
        Map<String, Integer> spacerWidths = null;
        Map<String, BlockCreator> blockCreators = new TreeMap<>();
        Map<String, String> defaultBlocksSettings = null;
        Map<String, String> specificBlockSettings;

        BufferedReader buffReader = null;
        try {
            buffReader = new BufferedReader(reader);
            String currentLine = buffReader.readLine();

            while (currentLine != null) {
                if (currentLine.startsWith("default")) {
                    defaultBlocksSettings = definitionReader.getSettingsFromLine(currentLine);
                } else if (currentLine.startsWith("bdef")) {
                    specificBlockSettings = definitionReader.getSettingsFromLine(currentLine);
                    BlockCreator creator = new BlockTemplate(defaultBlocksSettings, specificBlockSettings);
                    blockCreators.put(specificBlockSettings.get("symbol"), creator);

                } else if (currentLine.startsWith("sdef")) {
                    if (spacerWidths == null) {
                        spacerWidths = new TreeMap<>();
                    }
                    Pattern pattern = Pattern.compile(":\\S*");
                    Matcher matcher = pattern.matcher(currentLine);
                    while (matcher.find()) {
                        String symbol = Character.toString(currentLine.charAt(matcher.start() + 1));
                        if (matcher.find()) {
                            int width = Integer.parseInt(currentLine.substring(matcher.start() + 1));
                            spacerWidths.put(symbol, width);
                        } else {
                            spacerWidths.put(symbol, null);
                        }
                    }
                }
                currentLine = buffReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buffReader != null) {
                try {
                    buffReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new BlocksFromSymbolsFactory(spacerWidths, blockCreators);
    }

    /**
     * maps all the information the line contains to a Map (key:value form).
     *
     * @param line - the current line
     * @return a Map of the information
     */
    private Map<String, String> getSettingsFromLine(String line) {
        Map<String, String> map = new TreeMap<>();

        Pattern pattern = Pattern.compile("(\\b[\\w-\\d]*:\\S*|\\)\\b)");
        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            String info = matcher.group(1);
            String defType = info.substring(0, info.indexOf(":"));
            String defValue = info.substring(info.indexOf(":") + 1);
            map.put(defType, defValue);
        }
        return map;
    }
}
