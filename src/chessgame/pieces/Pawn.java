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
        if (isEnPassant(toX, toY, board)) {
            return true;
        }

        if (toX == this.x + 1 || toX == this.x - 1) {
            // pawn moving diagnolly
            if (isWhite && toY != this.y - 1) {
                return false;
            } else if (!isWhite && toY != this.y + 1) {
                return false;
            }
            // target tile is not occupied by enemy piece
            if (board.getPiece(toX, toY) == null || board.getPiece(toX, toY).isWhite == isWhite) {
                return false;
            }
        } else if ((isWhite && toY == this.y - 1) || (!isWhite && toY == this.y + 1)) {
            // moving one tile forward
            if (toX != this.x) {
                return false;
            }
            // target tile is not empty
            if (board.getPiece(toX, toY) != null) {
                return false;
            }
        } else if ((isWhite && toY == this.y - 2) || (!isWhite && toY == this.y + 2)) {
            // moving two tiles forward
            if (toX != this.x) {
                return false;
            }
            // pawn has moved
            if (isMoved) {
                return false;
            }
            // pawn is obsetructed by other pieces
            if (isWhite) {
                if (board.getPiece(toX, toY) != null || 
                    board.getPiece(this.x, this.y - 1) != null
                ) {
                    return false;
                }
            } else {
                if (board.getPiece(toX, toY) != null || 
                    board.getPiece(this.x, this.y + 1) != null
                ) {
                    return false;
                }
            }
        } else {
            return false;
        }

        return true;
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
