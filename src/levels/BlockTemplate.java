package levels;

import sprites.Block;
import geometry.Rectangle;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * BlockTemplate class.
 * @author shinake
 */
public class BlockTemplate implements BlockCreator {

    private Map<String, String> defaultDetails;
    private Map<String, String> specificDetails;
    private String symbol;
    private Map<Integer, Color> fillColors;
    private Map<Integer, Image> fillImages;
    private Color stroke;
    private int width;
    private int height;
    private int hitPoints;

    /**
     * Constructor.
     * @param defaultDetails   - the information listed in the 'default' section of the information file
     * @param specificDetails - the information listed in a single 'bdef' line
     */
    public BlockTemplate(Map<String, String> defaultDetails, Map<String, String> specificDetails) {
        this.defaultDetails = defaultDetails;
        this.specificDetails = specificDetails;
        this.fillColors = new TreeMap<>();
        this.fillImages = new TreeMap<>();
    }

    @Override
    public Block create(int xpos, int ypos) {
        try {
            this.setSymbol();
            this.setBlockFillMaps();
            this.setStroke();
            this.setHeight();
            this.setWidth();
            this.setHitPoints();
            Rectangle r = new Rectangle(xpos, ypos, this.width, this.height);
            Block block = new Block(r, this.fillColors, this.fillImages, this.stroke, this.hitPoints);
            block.setHits(this.hitPoints);
            return block;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * sets the block's symbol.
     * @throws NullPointerException if we don't find a symbol.
     */
    public void setSymbol() throws NullPointerException {
        this.symbol = this.specificDetails.get("symbol");
    }

    /**
     * Getting the mapped information from the maps and if is fill information - add to the appropriate map.
     *
     * @throws NullPointerException if both of the maps don't contain the appropriate information
     */
    private void setBlockFillMaps() throws NullPointerException {
        if (this.defaultDetails != null) {
            for (Map.Entry<String, String> entry : this.defaultDetails.entrySet()) {
                this.addFill(entry.getKey() + ":" + entry.getValue());
            }
        }
        for (Map.Entry<String, String> entry : this.specificDetails.entrySet()) {
            this.addFill(entry.getKey() + ":" + entry.getValue());
        }

        if (this.fillImages.size() == 0 && this.fillColors.size() == 0) {
            throw new NullPointerException("Missing information: " + "'fill");
        }
    }

    /**
     * if the given string is regarding fill information about the block,
     * adds the information to the appropriate fill map.
     * @param data - string of a key:value from a map.
     */
    private void addFill(String data) {
        if (data.startsWith("fill")) {
            String key = data.substring(0, data.indexOf(':'));
            String value = data.substring(data.indexOf(':') + 1);
            if (value.contains("color")) {
                ColorsParser parser = new ColorsParser();
                this.fillColors.put(this.getFillNumber(key), parser.colorFromString(value));
            } else {
                String imageFilePath = value.substring(value.indexOf('(') + 1, value.length() - 1);
                try {
                    Image image = ImageIO.read(new File("resources/" + imageFilePath));
                    this.fillImages.put(this.getFillNumber(key), image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Receives a key as a string.
     * if the key's form is 'fill-k', checks the value of 'k'.
     * @param s String
     * @return the value of 'k'. if doesn't contain '-k' returns 1.
     */
    private int getFillNumber(String s) {
        int fillNumber = 1;
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
            fillNumber = Integer.parseInt(s.substring(matcher.start(), matcher.end()));
        }
        return fillNumber;
    }

    /**
     * sets the stroke's color as given in the details maps.
     */
    private void setStroke() {
        ColorsParser parser = new ColorsParser();
        this.stroke = parser.colorFromString(this.searchValueInMaps("stroke"));
    }

    /**
     * sets the height of the block as given in the details maps.
     * @throws NullPointerException if both maps don't contains it.
     */
    public void setHeight() throws NullPointerException {
        this.height = Integer.parseInt(this.searchValueInMaps("height"));
    }

    /**
     * sets the width of the block as given in the details maps.
     * @throws NullPointerException if both maps don't contains it.
     */
    public void setWidth() throws NullPointerException {
        this.width = Integer.parseInt(this.searchValueInMaps("width"));
    }

    /**
     * sets the hit points value of the block as given in the details maps.
     * @throws NullPointerException if both maps don't contains it.
     */
    public void setHitPoints() throws NullPointerException {
        this.hitPoints = Integer.parseInt(this.searchValueInMaps("hit_points"));
    }

    /**
     * searches for the given key in the details maps.
     * @param key the key we want to search for its value.
     * @return if exists - return the key's value as String, otherwise return null.
     */
    public String searchValueInMaps(String key) {
        if (this.specificDetails.containsKey(key)) {
            return this.specificDetails.get(key);
        } else if (this.defaultDetails != null && this.defaultDetails.containsKey("stroke")) {
            return this.defaultDetails.get(key);
        } else {
            return null;
        }
    }
}
