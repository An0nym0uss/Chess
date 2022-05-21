package chessgame;

import chessgame.pieces.Piece;

public class Move {
    private Piece piece;
    private int fromX, fromY, toX, toY;
    private boolean isCastling, isPromotion;

    public Move(int fromX, int fromY, int toX, int toY, Piece piece) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.piece = piece;
    }

    public Move(int fromX, int fromY, int toX, int toY, 
                Piece piece, boolean isCastling, boolean isPromotion) {
        this(fromX, fromY, toX, toY, piece);
    }

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
     * @param isCastling the isCastling to set
     */
    public void setCastling(boolean isCastling) {
        this.isCastling = isCastling;
    }

    /**
     * @return the isPromotion
     */
    public boolean isPromotion() {
        return isPromotion;
    }

    /**
     * @param isPromotion the isPromotion to set
     */
    public void setPromotion(boolean isPromotion) {
        this.isPromotion = isPromotion;
    }
}
