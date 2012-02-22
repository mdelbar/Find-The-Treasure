
package findthetreasure;

import game.Game;
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * @author Matthias Delbar
 */

public class Constants {

    public static final Random RANDOM = new Random();
    public static final int GAMESIZE = 1024;
    public static final int FIELDSIZE = 512;
    public static final int BLOCKSIZE = 16;
    public static final int NUMBLOCKS = FIELDSIZE / BLOCKSIZE;
    public static final int MAXDEPTH = 7;
    public static final int FPS = 15;
    public static final int MAXDIFFICULTY = 2;
    public static int DIFFICULTY = 0; // 0 = highest, 1 = unidirectional hints, 2 = bidirectional hints
    public static boolean TREASUREFOUND = false;

    public static final Image IMAGE_BLOCKS = new ImageIcon(Constants.class.getResource("/images/Blocks.png")).getImage();
    public static final Image IMAGE_CREATURES = new ImageIcon(Constants.class.getResource("/images/Creatures.png")).getImage();

    public static enum Direction {
        N,
        E,
        S,
        W,
        NE,
        NW,
        SE,
        SW
    }
    
    public static enum BlockType {
        DIRT     , // 0
        GRASS    , // 1
        SAND     , // 2
        TREE     , // 3
        WATER    , // 4
        SNOW     , // 5
        DIG      , // 9

        TREASURE , // 10
        HINT     , // 11
    };

    public static BlockType getBlockType(int type) {
        switch(type) {
            case 0:
                return BlockType.DIRT;
            case 1:
                return BlockType.GRASS;
            case 2:
                return BlockType.SAND;
            case 3:
                return BlockType.TREE;
            case 4:
                return BlockType.WATER;
            case 5:
                return BlockType.SNOW;
            default:
                System.err.println("Somehow you managed to get an invalid BlockType in there. Way to break it, hero.");
                System.err.println("You might want to check out getBlockType() in Constants. I'm just saying.");
                return null;
        }
    }

    private static JFrame currentGameFrame;
    
    public static void registerGame(JFrame gameFrame) {
        currentGameFrame = gameFrame;
    }

    public static void startNewGame() {
        currentGameFrame.dispose();
        new Game();
    }
}
