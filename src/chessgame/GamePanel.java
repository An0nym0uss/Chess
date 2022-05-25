package chessgame;

import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import chessgame.pieces.Pawn;

public class GamePanel extends JPanel {
    private int width;
    private int tileWidth;  // each tile is a square
    private Game game;

    GamePanel(int width) {
        this.width = width;
        tileWidth = width / 8;

        this.setFocusable(true);
        this.addMouseListener(new Listner());
        this.addMouseMotionListener(new Listner());

        game = new Game();
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

                game.selectOrMove(x, y);

                revalidate();
                repaint();
            }
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            int xCord = e.getX();
            int yCord = e.getY();

            if (xCord < width && yCord < width) {
                int x = xCord / tileWidth;
                int y = yCord / tileWidth;

                game.move(x, y);
                promotion();
                game.deselectChosen();

                revalidate();
                repaint();
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
                }
            }
        }
    }
}
