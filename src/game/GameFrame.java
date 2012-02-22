
package game;

import findthetreasure.Constants;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * @author Matthias Delbar
 */

public class GameFrame extends JFrame {

    private Field field;

    public GameFrame() {
        super("Find The Treasure!");
        field = new Field();
        this.setContentPane(field);
        initMenuBar();
    }

    private void initMenuBar() {
        // Creation
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_G);
        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.setAction(new AbstractAction("New game") {
            public void actionPerformed(ActionEvent e) {
                Constants.startNewGame();
            }
        });
        newGameItem.setMnemonic(KeyEvent.VK_N);
        newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));

        // Addition
        gameMenu.add(newGameItem);
        menuBar.add(gameMenu);
        this.setJMenuBar(menuBar);
    }

    public void addHints() {
        field.addHints();
    }
}
