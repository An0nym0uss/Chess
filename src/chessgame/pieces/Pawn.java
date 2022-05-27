package chessgame.pieces;

import javax.imageio.ImageIO;
import java.io.IOException;

import chessgame.Board;
import chessgame.Move;

public class Pawn extends Piece {

    public Pawn(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
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
        if (!isMoved && this.y != this.getStartY()) {
            this.isMoved = true;
        }

        if (isEnPassant(toX, toY, board)) {
            return true;
        }

        // The pawn is able to move diagonally for 1 tile when attacking
        if (
            (getStartY() >= 5 && toX == getX() - 1 && toY == getY() - 1 && board.getPiece(toX, toY) != null && board.getPiece(toX, toY).isWhite != this.isWhite)
            || 
            (getStartY() >= 5 && toX == getX() + 1 && toY == getY() - 1 && board.getPiece(toX, toY) != null && board.getPiece(toX, toY).isWhite != this.isWhite)) {
            return true;
        }
        if (
            (getStartY() <= 4 && toX == getX() - 1 && toY == getY() + 1 && board.getPiece(toX, toY) != null && board.getPiece(toX, toY).isWhite != this.isWhite) 
            || 
            (getStartY() <= 4 && toX == getX() + 1 && toY == getY() + 1 && board.getPiece(toX, toY) != null && board.getPiece(toX, toY).isWhite != this.isWhite)) {
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
            if (!isMoved && getY() - toY <= 2) {
                return true;
            } else if (isMoved && getY() - toY <= 1) {
                return true;
            }
        }
        if (getStartY() <= 4 && toY > getY()) {
            if (!isMoved && toY - getY() <= 2) {
                return true;
            } else if (isMoved && toY - getY() <= 1) {
                return true;
            }
        }

        return false;
    }

    private boolean isObstructed(int toX, int toY, Board board) {
        // Case when the pawn is of the color that start at the top
        if (board.getPiece(toX, toY) != null) {
            return true;
        }
        if (getStartY() >= 5 && board.getPiece(getX(), getY() - 1) != null) {
            return true;
        }
        if (getStartY() <= 4 && board.getPiece(getX(), getY() + 1) != null) {
            return true;
        }
        return false;
    }

    public boolean isPromotable() {
        if (this.getY() == Board.ROWS || this.getY() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Check if there is available En Passant move.
     * En Passant allows an enemy pawn that has just advanced two squares
     * to be captured by a horizontally adjacent pawn.
     * 
     * @param board
     */
    private boolean isEnPassant(int toX, int toY, Board board) {
        // target tile is not empty
        if (board.getPiece(toX, toY) != null) {
            return false;
        }

        // target tile is not diagnally forward
        if (isWhite && !(toY == y - 1 && (toX == x + 1 || toX == x - 1))) {
            return false;
        } else if (!isWhite && !(toY == y + 1 && (toX == x + 1 || toX == x - 1))) {
            return false;
        }

        // last move is not enemy pawn
        if (board.getMoves().empty()) {
            return false;
        }
        Move move = board.getMoves().peek();
        Piece piece = move.getPiece();
        if (!(piece instanceof Pawn) || piece.isWhite == this.isWhite) {
            return false;
        }

        // enemy pawn did not move two tiles forward
        if (move.getToY() != move.getFromY() + 2 && move.getToY() != move.getFromY() - 2) {
            return false;
        }

        // enemy pawn is not next to this pawn
        if (piece.getY() != y || piece.getX() != toX) {
            return false;
        }

        return true;
    }
}
