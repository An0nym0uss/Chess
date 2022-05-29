package chessgame.pieces;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.lang.Math;

import chessgame.Board;

public class Bishop extends Piece {
    public Bishop(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
        try {
            if (isWhite) {
                this.image = ImageIO.read(getClass().getResource(PieceImages.BISHOP_W));
            } else{
                this.image = ImageIO.read(getClass().getResource(PieceImages.BISHOP_B));
            }
        } catch (IOException e) {
            System.out.println("Image file not found: " + e.getMessage());
        }
    }

    @Override
    public boolean canMove(int toX, int toY, Board board) {
        if (toX == this.getX() && toY == this.getY()) {
            return false;
        }
        if (Math.abs(toX - this.getX()) == Math.abs(toY - this.getY())) {
            if (isObstructed(toX, toY, board)) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private boolean isObstructed(int toX, int toY, Board board) {
        if (board.getPiece(toX, toY) != null && this.isWhite == board.getPiece(toX, toY).isWhite) {
            return true;
        }

        if (toY > getY() && toX > getX()) {
            for (int i = 1; i < Board.COLUMNS; i++) {
                if (toY - i == getY()) {
                    return false;
                }
                if (board.getPiece(toX-i, toY-i) != null) {
                    return true;
                }
            }
        }
        if (toY > getY() && toX < getX()) {
            for (int i = 1; i < Board.COLUMNS; i++) {
                if (toY - i == getY()) {
                    return false;
                }
                if (board.getPiece(toX + i, toY - i) != null) {
                    return true;
                }
            }
        }
        if (toY < getY() && toX < getX()) {
            for (int i = 1; i < Board.COLUMNS; i++) {
                if (toY + i == getY()) {
                    return false;
                }
                if (board.getPiece(toX + i, toY + i) != null) {
                    return true;
                }
            }
        }
        if (toY < getY() && toX > getX()) {
            for (int i = 1; i < Board.COLUMNS; i++) {
                if (toY + i == getY()) {
                    return false;
                }
                if (board.getPiece(toX - i, toY + i) != null) {
                    return true;
                }
            }
        }
        return false;
    }
}
