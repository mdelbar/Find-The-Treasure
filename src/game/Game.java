
package game;

import static findthetreasure.Constants.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author Matthias Delbar
 */

public class Game {

    public Game() {
        GameFrame gameFrame = new GameFrame();

        gameFrame.setResizable(false);
        gameFrame.setLocation(100, 100);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.pack();
        gameFrame.setVisible(true);

        welcome(gameFrame);
        askForDifficulty(gameFrame);

        registerGame(gameFrame);
    }

    private void welcome(GameFrame gameFrame) {
        JOptionPane.showMessageDialog(gameFrame, "Find the Treasure! Click to dig.");
    }

    private void askForDifficulty(GameFrame gameFrame) {
        String diff = JOptionPane.showInputDialog(gameFrame, "Please enter a difficulty level:\n" +
                "Level 0: The hardest, there are no hints to be found, only treasure.\n" +
                "Level 1: The hints here give you horizontal or vertical directions.\n" +
                "Level 2: Here, you even get diagonal hints. Still having trouble with that treasure?");
        int difficulty = 0;
        try {
            difficulty = Integer.parseInt(diff);
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(gameFrame, "So you thought you were smart?\n" +
                                                     "Have fun with level 0, smartypants.");
            DIFFICULTY = 0;
            return;
        }
        if(difficulty < 0 || difficulty > MAXDIFFICULTY) {
            JOptionPane.showMessageDialog(gameFrame, "So you thought you were smart?\n" +
                                                     "Have fun with level 0, smartypants.");
            DIFFICULTY = 0;
            return;
        }
        DIFFICULTY = difficulty;
        gameFrame.addHints();
    }
}
