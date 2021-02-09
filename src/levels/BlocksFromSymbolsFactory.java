package levels;

import sprites.Block;

import java.util.Map;

/**
 * BlocksFromSymbolsFactory class.
 * @author shinake
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * constructor.
     * @param spacerWidths symbols:width.
     * @param blockCreators symbol:BlockCreator associated with symbol.
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }
    /**
     * @param s String
     * @return true if 's' is a valid space symbol, false otherwise.
     */
    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.containsKey(s);
    }

    /**
     * @param s String
     * @return true if 's' is a valid block symbol, false otherwise.
     */
    public boolean isBlockSymbol(String s) {
        return this.blockCreators.containsKey(s);
    }

    /**
     * @param s symbol
     * @param x x position
     * @param y y position
     * @return a block according to the definitions associated.
     */
    public Block getBlock(String s, int x, int y) {
        return this.blockCreators.get(s).create(x, y);
    }

    /**
     * @param s spacer-symbol
     * @return the width in pixels associated with the given spacer-symbol.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }
}
