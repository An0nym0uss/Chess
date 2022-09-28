package chessgame;

import javax.swing.SwingUtilities;

import chessgame.chess.GameFrame;

/**
 * It runs Chess.
 */
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new GameFrame());
    }
}
