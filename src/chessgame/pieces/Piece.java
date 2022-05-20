package chessgame.pieces;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import chessgame.Board;
import chessgame.Move;

/**
 * Class {@code} defines a chess piece.
 */
public abstract class Piece {
    protected int x;
    protected int y;
    protected boolean isWhite;
    protected boolean isMoved;
    protected List<Move> legalMoves = new ArrayList<>();
    protected BufferedImage image;

    Piece(int x, int y, boolean isWhite) {
        this.x = x;
        this.y = y;
        this.isWhite = isWhite;
        this.isMoved = false;
    }

    public abstract boolean canMove(int toX, int toY, Board board);

    public abstract void move(int toX, int toY, Board board);

    public void allLegalMoves(Board board) {
        legalMoves = new ArrayList<Move>();
        for (int i = 0; i < Board.ROWS; i++) {
            for (int j = 0; j < Board.COLUMNS; j++) {
                if (canMove(i, j, board)) {
                    legalMoves.add(new Move(x, y, i, j, this));
                }
            }
        }
    }

    // getters and setters

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the isWhite
     */
    public boolean isWhite() {
        return isWhite;
    }

    /**
     * @return the isMoved
     */
    public boolean isMoved() {
        return isMoved;
    }

    /**
     * @return the legalMoves
     */
    public List<Move> getLegalMoves() {
        return legalMoves;
    }
}