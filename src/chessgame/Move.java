package chessgame;

import chessgame.pieces.Piece;

public class Move {
    private Piece piece;
    private int fromX, fromY, toX, toY;

    public Move(int fromX, int fromY, int toX, int toY, Piece piece) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.piece = piece;
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
}
