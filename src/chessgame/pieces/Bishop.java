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
        if (isObstructed(toX, toY, board)) {
            return false;
        }

        if (Math.abs(toX - this.getX()) == Math.abs(toY - this.getY())) {
            return true;
        }
        return false;
    }

    private boolean isObstructed(int toX, int toY, Board board) {
        for (int i = 1; i < Math.min(Math.min(getX(),getY()),Math.min(1-getX(),1-getY())); i++) {
            if (board.getPiece(getX() + i, getY() + i) != null && this.isWhite == board.getPiece(getX() + i, getY() + i).isWhite && toY >= getY() + i
            ||
            board.getPiece(getX() + i, getY() + i) != null && this.isWhite != board.getPiece(getX() + i, getY() + i).isWhite && toY > getY() + i) {
                return true;
            }
        }

        for (int i = 1; i < Math.min(Math.min(getX(), getY()), Math.min(1 - getX(), 1 - getY())); i++) {
            if (board.getPiece(getX() - i, getY() + i) != null && this.isWhite == board.getPiece(getX() - i, getY() + i).isWhite && toY >= getY() + i
            ||
            board.getPiece(getX() - i, getY() + i) != null && this.isWhite != board.getPiece(getX() - i, getY() + i).isWhite && toY > getY() + i) {
                return true;
            }
        }

        for (int i = 1; i < Math.min(Math.min(getX(), getY()), Math.min(1 - getX(), 1 - getY())); i++) {
            if (board.getPiece(getX() - i, getY() - i) != null && this.isWhite == board.getPiece(getX() - i, getY() - i).isWhite && toY <= getY() - i
            ||
            board.getPiece(getX() - i, getY() - i) != null && this.isWhite != board.getPiece(getX() - i, getY() - i).isWhite && toY < getY() - i) {
                return true;
            }
        }

        for (int i = 1; i < Math.min(Math.min(getX(), getY()), Math.min(1 - getX(), 1 - getY())); i++) {
            if (board.getPiece(getX() + i, getY() - i) != null && this.isWhite == board.getPiece(getX() - i, getY() - i).isWhite && toY <= getY() - i
            ||
            board.getPiece(getX() + i, getY() - i) != null && this.isWhite != board.getPiece(getX() + i, getY() - i).isWhite && toY < getY() - i) {
                return true;
            }
        }

        return false;
    }
}
