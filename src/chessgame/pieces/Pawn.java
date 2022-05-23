package chessgame.pieces;

import javax.imageio.ImageIO;
import java.io.IOException;

import chessgame.Board;

public class Pawn extends Piece {

    private boolean firstMove;

    public Pawn(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
        firstMove = true;
        try {
            if (isWhite) {
                this.image = ImageIO.read(getClass().getResource(PieceImages.PAWN_W));
            } else {
                this.image = ImageIO.read(getClass().getResource(PieceImages.PAWN_B));
            }
        } catch (IOException e) {
            System.out.println("Image file not found: " + e.getMessage());
        }
    }

    @Override
    public boolean canMove(int toX, int toY, Board board) {
        // toggle off first move
        if (firstMove && this.y != this.getStartX()) {
            this.firstMove = false;
        }

        // The pawn is able to move diagonally for 1 tile when attacking
        if (
            (getStartY() >= 5 && toX == getX() - 1 && toY == getY() - 1 && board.getPiece(toX, toY) != null)
            || 
            (getStartY() >= getY() && toX == getX() + 1 && toY == getY() - 1 && board.getPiece(toX, toY) != null)) {
            return true;
        }
        if (
            (getStartY() <= 4 && toX == getX() - 1 && toY == getY() + 1 && board.getPiece(toX, toY) != null) 
            || 
            (getStartY() >= getY() && toX == getX() + 1 && toY == getY() + 1 && board.getPiece(toX, toY) != null)) {
            return true;
        }

        // The pawn is unable to move horizontally without having a piece availbe for an attack
        if (getStartX() != toX) {
            return false;
        }

        // The pawn is unable to advance beyond and including the tile occupied by a piece of any color 
        if (isObstructed(toX, toY, board)) {
            return false;
        }

        // The pawn is able to move 2 tile with its first move
        if (getStartY() >= 5 && toY < getY()) {
            if (this.firstMove && getY() - toY <= 2) {
                return true;
            } else if (getY() - toY <= 1) {
                return false;
            }
        }
        if (getStartY() <= 4 && toY > getY()) {
            if (this.firstMove && toY - getY() <= 2) {
                return true;
            } else if (toY - getY() <= 1) {
                return false;
            }
        }
        return false;
    }


    private boolean isObstructed(int toX, int toY, Board board) {
        // Case when the pawn is of the color that start at the top
        if (getStartY() >= 5) {
            for (int i = 1; i < Board.ROWS; i++) {
                if (board.getPiece(getX(), getY() + i) != null && toY >= getY() + i) {
                    return true;
                }
            }
        }
        // Case when the pawn is of the color that start at the bottom
        if (getStartY() <= 4) {
            for (int i = 1; i < Board.ROWS - getY(); i++) {
                if (board.getPiece(getX(), getY() + i) != null && toY >= getY() + i) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isPromotable(int x, int y, Board board) {
        if (y == board.ROWS) {
            return true;
        }
        return false;
    }

    /**
     * Check if there is available En Passant move.
     * En Passant llows an enemy pawn that has just advanced two squares
     * to be captured by a horizontally adjacent pawn.
     * 
     * @param board
     */
    private boolean isEnPassant(Board board) {
        // TODO
        return false;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }
}
