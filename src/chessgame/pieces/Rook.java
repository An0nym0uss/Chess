package chessgame.pieces;

import java.io.IOException;
import javax.imageio.ImageIO;

import chessgame.Board;

public class Rook extends Piece {
    public Rook(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
        try {
            if (isWhite) {
                this.image = ImageIO.read(getClass().getResource(PieceImages.ROOK_W));
            } else{
                this.image = ImageIO.read(getClass().getResource(PieceImages.ROOK_B));
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
        if (isObstructed(toX, toY, board)) {
            return false;
        }
        if (toX == this.getX()) {
            return true;
        }
        if (toY == this.getY()) {
            return true;
        }
        return false;
    }

    private boolean isObstructed(int toX, int toY, Board board) {
        // obstructed by friendly piece including the tile the friendly piece is on, obstructed by hostile piece but not including the tile the hostile piece is on
        for (int i = 1; i < Board.ROWS - getY(); i++) {
            if ((board.getPiece(getX(), getY() + i) != null && this.isWhite == board.getPiece(getX(), getY() + i).isWhite && toY >= getY() + i)
            ||
            (board.getPiece(getX(), getY() + i) != null && this.isWhite != board.getPiece(getX(), getY() + i).isWhite && toY > getY() + i)) {
                return true;
            }
        }
        for (int i = 1; i < getY(); i++) {
            if ((board.getPiece(getX(), getY() - i) != null && this.isWhite == board.getPiece(getX(), getY() - i).isWhite && toY <= getY() - i)
            ||
            (board.getPiece(getX(), getY() - i) != null && this.isWhite != board.getPiece(getX(), getY() - i).isWhite && toY < getY() - i)) {
                return true;
            }
        }
        for (int i = 1; i < Board.COLUMNS - getX(); i++) {
            if ((board.getPiece(getX() + i, getY()) != null && this.isWhite == board.getPiece(getX() + i, getY()).isWhite && toX >= getX() + i)
            ||
            (board.getPiece(getX() + i, getY()) != null && this.isWhite != board.getPiece(getX() + i, getY()).isWhite && toX > getX() + i)) {
                return true;
            }
        }
        for (int i = 1; i < getX(); i++) {
            if ((board.getPiece(getX() - i, getY()) != null && this.isWhite == board.getPiece(getX() - i, getY()).isWhite && toY <= getX() - i)
            ||
            (board.getPiece(getX() - i, getY()) != null && this.isWhite != board.getPiece(getX() - i, getY()).isWhite && toY < getX() - i)) {
                return true;
            }
        }
        
        return false;
    }
}
