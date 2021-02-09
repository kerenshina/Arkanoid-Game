package levels;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ColorsParser class.
 * @author shinake
 */
public class ColorsParser {

    /**
     * parse color definition and return the specified color.
     * @param s the color definition.
     * @return color.
     */
    public java.awt.Color colorFromString(String s) {
        if (s == null) {
            return null;
        }
        String colorString = s.substring(s.indexOf('(') + 1, s.length() - 1);
        if (colorString.startsWith("RGB")) {
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(colorString);
            matcher.find();
            int r = Integer.parseInt(colorString.substring(matcher.start(), matcher.end()));
            matcher.find();
            int g = Integer.parseInt(colorString.substring(matcher.start(), matcher.end()));
            matcher.find();
            int b = Integer.parseInt(colorString.substring(matcher.start(), matcher.end()));
            return new Color(r, g, b);
        } else {
            switch (colorString) {
                case ("black"):
                    return Color.BLACK;
                case ("blue"):
                    return Color.BLUE;
                case ("cyan"):
                    return Color.CYAN;
                case ("gray"):
                    return Color.GRAY;
                case ("lightGray"):
                    return Color.LIGHT_GRAY;
                case ("green"):
                    return Color.GREEN;
                case ("orange"):
                    return Color.ORANGE;
                case ("pink"):
                    return Color.PINK;
                case ("red"):
                    return Color.RED;
                case ("white"):
                    return Color.WHITE;
                case ("yellow"):
                    return Color.YELLOW;
                default:
                    return null;
            }
        }
    }
}
