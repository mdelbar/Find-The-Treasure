
package blocks;

import static findthetreasure.Constants.*;

/**
 * @author Matthias Delbar
 */

public class Block {

    private int x, y;
    private int imgX1, imgX2, imgY1, imgY2;
    private int depth;
    
    private BlockType type;

    private boolean hint;
    private int hintDepth;
    private Direction hintDirection;
    
    private boolean treasure;
    private int treasureDepth;

    public Block(int x, int y, BlockType type) {
        this.x = x;
        this.y = y;
        this.depth = (RANDOM.nextBoolean()) ? (RANDOM.nextInt(MAXDEPTH/2)) : (0);
        this.type = type;
        treasure = false;
        treasureDepth = 0;
        hint = false;
        hintDepth = 0;
        update();
    }

    private void update() {
        setImgCoords();
    }

    private void setImgCoords() {
        switch(type) {
            case DIG:
                imgX1 = 2*BLOCKSIZE + (depth/4)*BLOCKSIZE;
                imgY1 = (depth % 4) * BLOCKSIZE;
                break;
            case DIRT:
                imgX1 = 0;
                imgY1 = 0;
                break;
            case GRASS:
                imgX1 = BLOCKSIZE;
                imgY1 = 0;
                break;
            case WATER:
                imgX1 = 0;
                imgY1 = BLOCKSIZE;
                break;
            case TREE:
                imgX1 = BLOCKSIZE;
                imgY1 = BLOCKSIZE;
                break;
            case SAND:
                imgX1 = 0;
                imgY1 = 2*BLOCKSIZE;
                break;
            case SNOW:
                imgX1 = 0;
                imgY1 = 3*BLOCKSIZE;
                break;
            case TREASURE:
                imgX1 = BLOCKSIZE;
                imgY1 = 2*BLOCKSIZE;
                break;
            case HINT:
                setImgCoordsHint();
                break;
            default:
                break;
        }
        imgX2 = imgX1 + BLOCKSIZE;
        imgY2 = imgY1 + BLOCKSIZE;
    }

    private void setImgCoordsHint() {
        switch(hintDirection) {
            case N:
                imgX1 = 4*BLOCKSIZE;
                imgY1 = 2*BLOCKSIZE;
                break;
            case E:
                imgX1 = 4*BLOCKSIZE;
                imgY1 = 3*BLOCKSIZE;
                break;
            case S:
                imgX1 = 4*BLOCKSIZE;
                imgY1 = 0;
                break;
            case W:
                imgX1 = 4*BLOCKSIZE;
                imgY1 = BLOCKSIZE;
                break;
            case NE:
                imgX1 = 5*BLOCKSIZE;
                imgY1 = 2*BLOCKSIZE;
                break;
            case NW:
                imgX1 = 5*BLOCKSIZE;
                imgY1 = BLOCKSIZE;
                break;
            case SE:
                imgX1 = 5*BLOCKSIZE;
                imgY1 = 3*BLOCKSIZE;
                break;
            case SW:
                imgX1 = 5*BLOCKSIZE;
                imgY1 = 0;
                break;
            default:
                break;
        }
    }

    public void dig() {
        if(shouldDig()) {
            depth++;
            checkForTreasure();
            checkForHint();
            
            update();
        }
    }

    private boolean shouldDig() {
        if(depth >= MAXDEPTH) {
            return false;
        }
        if(type == BlockType.HINT) {
            return false;
        }
        if(type == BlockType.TREASURE) {
            return false;
        }
        
        if(type != BlockType.DIG) {
            type = BlockType.DIG;
            depth--;
        }
        return true;
    }

    private void checkForTreasure() {
        if(treasure && (depth >= treasureDepth)) {
            type = BlockType.TREASURE;
            TREASUREFOUND = true;
        }
    }

    private void checkForHint() {
        if(hint && (depth >= hintDepth)) {
            type = BlockType.HINT;
        }
    }

    public void putTreasure() {
        treasure = true;
        treasureDepth = RANDOM.nextInt(MAXDEPTH) + 1;
    }

    public void putHint(Direction hintDir) {
        this.hintDirection = hintDir;
        this.hintDepth = RANDOM.nextInt(MAXDEPTH);
        this.hint = true;
//        System.out.println("Hint (" + hintDir + "): (" + x + "," + y + "). Depth: " + depth + ". HDepth: " + hintDepth);
    }

    public int getImgX1() {
        return imgX1;
    }

    public int getImgX2() {
        return imgX2;
    }

    public int getImgY1() {
        return imgY1;
    }

    public int getImgY2() {
        return imgY2;
    }

    public BlockType getType() {
        return type;
    }
}
