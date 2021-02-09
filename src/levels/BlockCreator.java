package levels;

import sprites.Block;

/**
 * BlockCreator interface.
 * @author shinake
 */
public interface BlockCreator {

    /**
     * Create a block at the specified location.
     * @param xpos x position of the block.
     * @param ypos y position of the block.
     * @return a block.
     */
    Block create(int xpos, int ypos);
}
