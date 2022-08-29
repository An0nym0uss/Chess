package chessgame.pieces;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.lang.Math;

import chessgame.Board;

public class King extends Piece {

    private boolean isChecked;

    public King(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
        this.isChecked = false;
        try {
            this.image = isWhite ? ImageIO.read(getClass().getResource(PieceImages.KING_W))
                    : ImageIO.read(getClass().getResource(PieceImages.KING_B));
        } catch (IOException e) {
            System.out.println("Image file not found: " + e.getMessage());
        }
    }

    @Override
    public boolean canMove(int toX, int toY, Board board) {
        if (board.getPiece(toX, toY) != null && this.isWhite == board.getPiece(toX, toY).isWhite) {
            return false;
        }

        if (isCastling(toX, toY, board)) {
            return true;
        }

        return (Math.abs(toX - getX()) == 1 && toY == getY())
                || (Math.abs(toY - getY()) == 1 && toX == getX())
                || (Math.abs(toX - getX()) == 1 && Math.abs(toY - getY()) == 1);
    }

    /**
     * Castling consists of moving the king two squares towards a rook,
     * then placing the rook on the other side of the king, adjacent to it.
     * This method places the rook next to king.
     * 
     * @param toX
     * @param toY
     * @param board
     * @return {@code true} if current move is castling, {@code false} otherwise
     */
    private boolean isCastling(int toX, int toY, Board board) {
        if (isMoved || isChecked || toY != y) {
            return false;
        }

        if (toX == x + 2) {
            // castle kingside
            // check if rook has not moved
            Piece rook = board.getPiece(x + 3, y);
            if (rook == null || !(rook instanceof Rook) || rook.isMoved || rook.isWhite != isWhite) {
                return false;
            }

            // if tiles between king and rook are empty, return true
            return board.getPiece(x + 1, y) == null
                    && board.getPiece(x + 2, y) == null;
        } else if (toX == x - 2) {
            // castle queenside
            // check if rook has not moved
            Piece rook = board.getPiece(x - 4, y);
            if (rook == null || !(rook instanceof Rook) || rook.isMoved || rook.isWhite != isWhite) {
                return false;
            }

            // if tiles between king and rook are empty, return true
            return board.getPiece(x - 1, y) == null
                    && board.getPiece(x - 2, y) == null
                    && board.getPiece(x - 3, y) == null;
        }

        return false;
    }

    /**
     * @return the isChecked
     */
    public boolean isChecked() {
        return isChecked;
    }

    /**
     * @param isChecked the isChecked to set
     */
    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

}
