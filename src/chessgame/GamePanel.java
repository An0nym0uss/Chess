package chessgame;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Dimension;

import chessgame.pieces.Pawn;

public class GamePanel extends JPanel {
    private int width;
    private int tileWidth;  // each tile is a square
    private Game game;

    GamePanel(int width, Game game) {
        this.width = width;
        tileWidth = width / 8;

        this.setFocusable(true);
        this.addMouseListener(new Listner());
        this.addMouseMotionListener(new Listner());
        this.setPreferredSize(new Dimension(width, width));

        this.game = game;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.draw(g, this);
    }

    /**
     * @return the tileWidth
     */
    public int getTileWidth() {
        return tileWidth;
    }

    /**
     * @param game the game to set
     */
    public void setGame(Game game) {
        this.game = game;
    }

    class Listner extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            selectOrMove(e);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            selectOrMove(e);
        }

        /**
         * Determines to select a piece or to move the chosen piece
         * @param e
         */
        private void selectOrMove(MouseEvent e) {
            int xCord = e.getX();
            int yCord = e.getY();
            
            if (xCord < width && yCord < width) {
                int x = xCord / tileWidth;
                int y = yCord / tileWidth;

                int choice = game.selectOrMove(x, y);
                if (choice == Game.MOVE) {
                    promotion();
                    game.deselectChosen();
                }

                revalidate();
                repaint();
                checkmateMessage();
            }
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            int xCord = e.getX();
            int yCord = e.getY();

            if (xCord < width && yCord < width) {
                int x = xCord / tileWidth;
                int y = yCord / tileWidth;

                if (game.getChosen() != null && 
                    game.getChosen().getX() == x && game.getChosen().getY() == y
                ) {
                    // mouse still on selected piece, do nothing
                    return;
                }

                game.move(x, y);
                promotion();
                game.deselectChosen();
                
                revalidate();
                repaint();
                checkmateMessage();
            }
        }

        /**
         * If chosen piece is a pawn and is available for promotion,
         * pop up a window that allows to select queen, rook, knight or bishop.
         */
        private void promotion() {
            if (game.getChosen() != null && game.getChosen() instanceof Pawn) {
                // check promotion, pop up selection window and promote
                Pawn pawn = (Pawn) game.getChosen();
                if (pawn.isPromotable()) {
                    Object[] options = { "Queen", "Rook", "Knight", "Bishop" };
                    String title = "Pawn Promotion";
                    String message = "Choose a desired piece to promote to.";

                    int choice = JOptionPane.showOptionDialog(
                        null, message, title, 
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
                        null, options, options[0]);

                    game.promotion(choice);
                    game.checkmate();
                }
            }
        }

        /**
         * Pop up a message saying which side wins if checmate.
         */
        private void checkmateMessage() {
            Boolean isCheckmate = true;
            String message = "";
            switch (game.getCheckmate()) {
                case GameState.WHITE_WINS: message = "Checkmate. White has won!"; break;
                case GameState.BLACK_WINS: message = "Checkmate. Black has won!"; break;
                case GameState.STALE_MATE: message = "Stalemate!"; break;
                default: isCheckmate = false;
            }

            if (isCheckmate) {
                JOptionPane.showMessageDialog(null, message);
            }
        }
    }
}
