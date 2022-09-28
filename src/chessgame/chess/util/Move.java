package chessgame.chess.util;

import java.io.Serial;
import java.io.Serializable;

import chessgame.chess.pieces.Piece;

/**
 * Class {@code Move} stores the movement of a chess piece.
 */
public class Move implements Serializable {
    @Serial
    private static final long serialVersionUID = 0x030001;

    private Piece piece;
    private int fromX, fromY, toX, toY;
    private boolean isCastling, isPromotion;

    public Move(int fromX, int fromY, int toX, int toY, Piece piece) {
        this(fromX, fromY, toX, toY, piece, false, false);
    }

    public Move(int fromX, int fromY, int toX, int toY, Piece piece, boolean isCastling, boolean isPromotion) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.piece = piece;
        this.isCastling = isCastling;
        this.isPromotion = isPromotion;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Move other = (Move) obj;
        if (this.fromX == other.fromX && this.fromY == other.fromY &&
            this.toX == other.toX && this.toY == other.toY &&
            this.piece == other.piece
        ) {
            return true;
        }

        return false;
    }

    // getters and setters

    /**
     * @return the piece
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * @return the fromX
     */
    public int getFromX() {
        return fromX;
    }

    /**
     * @return the fromY
     */
    public int getFromY() {
        return fromY;
    }

    /**
     * @return the toX
     */
    public int getToX() {
        return toX;
    }

    /**
     * @return the toY
     */
    public int getToY() {
        return toY;
    }

    /**
     * @return the isCastling
     */
    public boolean isCastling() {
        return isCastling;
    }

    /**
     * @return the isPromotion
     */
    public boolean isPromotion() {
        return isPromotion;
    }
}
