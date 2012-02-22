
package findthetreasure;

import game.Game;
import javax.swing.UIManager;

/**
 * @author Matthias Delbar
 */

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        
        new Game();
    }

}
