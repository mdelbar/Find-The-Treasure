
package game;

import blocks.Block;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import static findthetreasure.Constants.*;

/**
 * @author Matthias Delbar
 */

public class Field extends JPanel implements MouseListener, ActionListener {

    private Block[][] field;
    private int treasureX, treasureY;
    private Timer repaintTimer;

    public Field() {
        initBlocks();
        addTreasure();
        this.setPreferredSize(new Dimension(FIELDSIZE, FIELDSIZE));
        this.addMouseListener(this);
        repaintTimer = new Timer(1000 / FPS, this);
        repaintTimer.start();
    }

    private void initBlocks() {
        field = new Block[NUMBLOCKS][NUMBLOCKS];

        field[0][0] = new Block(0, 0, getBlockType(RANDOM.nextInt(6)));
        int i = 0;
        int j = 1;
        while(j < NUMBLOCKS) {
            while(i < j) {
                field[i][j] = new Block(i, j, getNextBlockType(i, j));
                i++;
            }
            while(j >= 0) {
                field[i][j] = new Block(i, j, getNextBlockType(i, j));
                j--;
            }
            j = i+1;
            i = 0;
        }
    }

    private BlockType getNextBlockType(int i, int j) {
        // Global chances
        int globalChanceWaterWater = 90;
        int globalChanceWaterSand = 10;

        int globalChanceSandWater = 10;
        int globalChanceSandSand = 75;
        int globalChanceSandDirt = 15;

        int globalChanceDirtSand = 20;
        int globalChanceDirtDirt = 30;
        int globalChanceDirtGrass = 50;

        int globalChanceGrassDirt = 10;
        int globalChanceGrassGrass = 60;
        int globalChanceGrassTree = 20;
        int globalChanceGrassSnow = 10;

        int globalChanceTreeGrass = 60;
        int globalChanceTreeTree = 25;
        int globalChanceTreeSnow = 15;

        int globalChanceSnowGrass = 10;
        int globalChanceSnowSnow = 70;
        int globalChanceSnowTree = 20;

        // Current chances
        int chanceWater = 0;
        int chanceSand = 0;
        int chanceDirt = 0;
        int chanceGrass = 0;
        int chanceTree = 0;
        int chanceSnow = 0;
        Queue<BlockType> blockQueue = new LinkedList<BlockType>();

        // Check what kind of stuff we should look at
        if(i == 0) {
            // West
            blockQueue.add(field[i][j-1].getType());
        }
        else if(i > 0 && i < j - 1) {
            // North-West
            blockQueue.add(field[i-1][j-1].getType());
            // West
            blockQueue.add(field[i][j-1].getType());
            // South-West
            blockQueue.add(field[i+1][j-1].getType());
        }
        else if(i < j) {
            // North-West
            blockQueue.add(field[i-1][j-1].getType());
            // West
            blockQueue.add(field[i][j-1].getType());
        }
        else if(i == j) {
            // North-West
            blockQueue.add(field[i-1][j-1].getType());
        }
        else if(j == 0) {
            // West
            blockQueue.add(field[i-1][j].getType());
        }
        else if(j > 0 && j < i - 1) {
            // North-West
            blockQueue.add(field[i-1][j-1].getType());
            // North
            blockQueue.add(field[i-1][j].getType());
            // North-East
            blockQueue.add(field[i-1][j+1].getType());
        }
        else if(j < i) {
            // North-West
            blockQueue.add(field[i-1][j-1].getType());
            // North
            blockQueue.add(field[i-1][j].getType());
        }

        // Actually calculate the next blocktype (only now, I know)
        int next = RANDOM.nextInt(blockQueue.size() * 100) + 1;
        while(!blockQueue.isEmpty()) {
            switch(blockQueue.remove()) {
                case WATER:
                    chanceWater += globalChanceWaterWater;
                    chanceSand += globalChanceWaterSand;
                    break;
                case SAND:
                    chanceWater += globalChanceSandWater;
                    chanceSand += globalChanceSandSand;
                    chanceDirt += globalChanceSandDirt;
                    break;
                case DIRT:
                    chanceSand += globalChanceDirtSand;
                    chanceDirt += globalChanceDirtDirt;
                    chanceGrass += globalChanceDirtGrass;
                    break;
                case GRASS:
                    chanceDirt += globalChanceGrassDirt;
                    chanceGrass += globalChanceGrassGrass;
                    chanceTree += globalChanceGrassTree;
                    chanceSnow += globalChanceGrassSnow;
                    break;
                case TREE:
                    chanceGrass += globalChanceTreeGrass;
                    chanceTree += globalChanceTreeTree;
                    chanceSnow += globalChanceTreeSnow;
                    break;
                case SNOW:
                    chanceGrass += globalChanceSnowGrass;
                    chanceTree += globalChanceSnowTree;
                    chanceSnow += globalChanceSnowSnow;
                default:
                    break;
            }
        }
        if(next <= chanceWater) {
            return BlockType.WATER;
        }
        next -= chanceWater;
        if(next <= chanceSand) {
            return BlockType.SAND;
        }
        next -= chanceSand;
        if(next <= chanceDirt) {
            return BlockType.DIRT;
        }
        next -= chanceDirt;
        if(next <= chanceGrass) {
            return BlockType.GRASS;
        }
        next -= chanceGrass;
        if(next <= chanceTree) {
            return BlockType.TREE;
        }
        next -= chanceTree;
        if(next <= chanceSnow) {
            return BlockType.SNOW;
        }

        System.err.println("How did you manage to ruin everything again? Field.java, getNextBlockType(int x, int y)");
        return null; // SHOULD NOT HAPPEN
    }


    private void addTreasure() {
        treasureX = RANDOM.nextInt(NUMBLOCKS);
        treasureY = RANDOM.nextInt(NUMBLOCKS);
        field[treasureX][treasureY].putTreasure();
        System.out.println("(" + treasureX + "," + treasureY + ")");
    }

    public void addHints() {
        if(DIFFICULTY == 0) {
            return;
        }

        int hintX;
        int hintY;
        
        // Pointing North
        hintY = RANDOM.nextInt(NUMBLOCKS - treasureY - 1) + treasureY + 1;
        if(hintY > treasureY) {
            if(DIFFICULTY == 2) {
                // Pointing N
                hintX = treasureX;
                field[hintX][hintY].putHint(Direction.N);
            }

            // Pointing NW
            hintX = RANDOM.nextInt(NUMBLOCKS - treasureX - 1) + treasureX + 1;
            if(hintX > treasureX) {
                field[hintX][hintY].putHint(Direction.NW);
            }

            // Pointing NE
            hintX = RANDOM.nextInt(treasureX + 1) - 1;
            if(hintX > 0 && hintX < treasureX) {
                field[hintX][hintY].putHint(Direction.NE);
            }
        }

        // Pointing South
        hintY = RANDOM.nextInt(treasureY);
        if(hintY < treasureY) {
            if(DIFFICULTY == 2) {
                // Pointing S
                hintX = treasureX;
                field[hintX][hintY].putHint(Direction.S);
            }

            // Pointing SW
            hintX = RANDOM.nextInt(NUMBLOCKS - treasureX - 1) + treasureX + 1;
            if(hintX > treasureX) {
                field[hintX][hintY].putHint(Direction.SW);
            }

            // Pointing SE
            hintX = RANDOM.nextInt(treasureX + 1) - 1;
            if(hintX > 0 && hintX < treasureX) {
                field[hintX][hintY].putHint(Direction.SE);
            }
        }

        if(DIFFICULTY == 2) {
            // Pointing East
            hintX = RANDOM.nextInt(treasureX);
            if(hintX < treasureX) {
                hintY = treasureY;
                field[hintX][hintY].putHint(Direction.E);
            }

            // Pointing West
            hintX = RANDOM.nextInt(NUMBLOCKS - treasureX - 1) + treasureX + 1;
            if(hintX > treasureX) {
                hintY = treasureY;
                field[hintX][hintY].putHint(Direction.W);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int i = 0; i < NUMBLOCKS; i++) {
            for(int j = 0; j < NUMBLOCKS; j++) {
                g.drawImage(IMAGE_BLOCKS,
                            i*BLOCKSIZE, j*BLOCKSIZE, (i+1)*BLOCKSIZE, (j+1)*BLOCKSIZE,
                            field[i][j].getImgX1(), field[i][j].getImgY1(), field[i][j].getImgX2(), field[i][j].getImgY2(),
                            this);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void mouseClicked(MouseEvent e) {
        // Do nothing.
    }

    public void mousePressed(MouseEvent e) {
        // Do nothing.
    }

    public void mouseReleased(MouseEvent e) {
        int fieldLocX = e.getX() / BLOCKSIZE;
        int fieldLocY = e.getY() / BLOCKSIZE;
        int extraClicks = 2;
        for(int i = -extraClicks; i <= extraClicks; i++) {
            for(int j = -extraClicks; j <= extraClicks; j++) {
                if(fieldLocX + j >= 0 && fieldLocX + j < NUMBLOCKS &&
                   fieldLocY + i >= 0 && fieldLocY + i < NUMBLOCKS) {
                    field[fieldLocX + j][fieldLocY + i].dig();
                }
            }
        }
//        field[e.getX() / BLOCKSIZE][e.getY() / BLOCKSIZE].dig();
        
        if(TREASUREFOUND) {
            TREASUREFOUND = false;
            int answer = JOptionPane.showConfirmDialog(this, "Congratulations! You managed to find the treasure! The loot is yours!\nWould you like to start a new game?", "Congratulations!", JOptionPane.YES_NO_OPTION);
            if(answer == JOptionPane.YES_OPTION) {
                startNewGame();
            }
        }
    }

    public void mouseEntered(MouseEvent e) {
        // Do nothing.
    }

    public void mouseExited(MouseEvent e) {
        // Do nothing.
    }
}
