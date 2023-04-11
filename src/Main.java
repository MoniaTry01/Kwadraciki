import javax.swing.*;
/**
 * @author Monika Trybula
 */

/**
 * Glowna klasa gry
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Okno okno = new Okno();
            }
        });
    }
}
