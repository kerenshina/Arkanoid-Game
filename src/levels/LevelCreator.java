package levels;

import collision.Velocity;
import sprites.Block;
import sprites.Sprite;

import java.io.FileReader;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * LevelCreator class.
 * @author shinake
 */
public class LevelCreator implements LevelInformation {

    private Map<String, String> levelDetails;
    private List<String> blocksLayout;
    private BlocksFromSymbolsFactory blocksFactory;
    private String name;
    private Sprite background;
    private List<Velocity> velocities;
    private int paddleSpeed;
    private int paddleWidth;
    private int blocksStartX;
    private int blocksStartY;
    private int rowHeight;
    private int numBlocks;

    /**
     * constructor.
     * @param details the details Map of the level.
     * @param blocksLayout the details List about the blocks layout.
     */
    public LevelCreator(Map<String, String> details, List<String> blocksLayout) {
        this.levelDetails = details;
        this.blocksLayout = blocksLayout;
        this.velocities = new ArrayList<Velocity>();
    }

    /**
     * creates a level from given information.
     * @return LevelInformation
     */
    public LevelInformation createLevel() {
        try {
            this.paddleSpeed = this.paddleSpeed();
            this.paddleWidth = this.paddleWidth();
            this.name = this.levelName();
            this.background = this.getBackground();
            this.numBlocks = this.numberOfBlocksToRemove();
            return this;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int numberOfBalls() {
        String data = levelDetails.get("ball_velocities");
        Pattern pattern = Pattern.compile("\\,");
        Matcher matcher = pattern.matcher(data);
        int num = 0;

        while (matcher.find()) {
            num++;
        }
        return num;
    }

    @Override
    public List<Velocity> initialBallVelocities() throws NullPointerException {
        String velocitiesData = this.levelDetails.get("ball_velocities");
        Pattern pattern = Pattern.compile("[-]?\\d+");
        Matcher matcher = pattern.matcher(velocitiesData);

        while (matcher.find()) {
            double angle = Double.parseDouble(velocitiesData.substring(matcher.start(), matcher.end()));
            matcher.find();
            double speed = Double.parseDouble(velocitiesData.substring(matcher.start(), matcher.end()));
            this.velocities.add(Velocity.fromAngleAndSpeed(angle, speed));
        }
        return this.velocities;
    }

    @Override
    public int paddleSpeed() throws NullPointerException {
        return Integer.parseInt(this.levelDetails.get("paddle_speed"));
    }

    @Override
    public int paddleWidth() throws NullPointerException {
        return Integer.parseInt(this.levelDetails.get("paddle_width"));
    }

    @Override
    public String levelName() throws NullPointerException {
        return this.levelDetails.get("level_name");
    }

    @Override
    public Sprite getBackground() throws NullPointerException {
        return new BackgroundCreator(this.levelDetails.get("background"));
    }

    /**
     * sets the blocks information.
     */
    public void setBlocksInformation() {
        String blocksDefinitions = this.levelDetails.get("block_definitions"); // set blocks factory.
        InputStream inputStream = null;
        try {
            java.io.Reader reader = new FileReader("resources/" + blocksDefinitions);
            this.blocksFactory = BlocksDefinitionReader.fromReader(reader);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        this.blocksStartX = Integer.parseInt(this.levelDetails.get("blocks_start_x"));
        this.blocksStartY = Integer.parseInt(this.levelDetails.get("blocks_start_y"));
        this.rowHeight = Integer.parseInt(this.levelDetails.get("row_height"));
    }

    @Override
    public List<Block> blocks() throws NullPointerException {
        List<Block> levelBlocks = new ArrayList<>(this.numberOfBlocksToRemove());
        int i = 1;

        this.setBlocksInformation();

        for (String line : blocksLayout) {
            char[] thisLine = line.toCharArray();
            int thisStartY = this.blocksStartY + (this.rowHeight * i);
            int thisStartX = (this.blocksStartX);
            int j = 1;
            if (line.length() <= 1) {
                i++;
                continue;
            }
            for (char currentChar : thisLine) {
                String symbol = Character.toString(currentChar);
                if (this.blocksFactory.isBlockSymbol(symbol)) {
                    Block block = this.blocksFactory.getBlock(symbol, thisStartX, thisStartY);
                    thisStartX = (int) block.getCollisionRectangle().rightUpperPoint().getX();
                    levelBlocks.add(block);
                } else if (this.blocksFactory.isSpaceSymbol(symbol)) {
                    thisStartX += this.blocksFactory.getSpaceWidth(symbol);
                }
                j++;
            }
            i++;
        }
        return levelBlocks;
    }

    @Override
    public int numberOfBlocksToRemove() throws NullPointerException {
        this.numBlocks = Integer.parseInt(this.levelDetails.get("num_blocks"));
        return this.numBlocks;
    }
}
