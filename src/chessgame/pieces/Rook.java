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
        for (int i = getY(); i <= Board.ROWS; i++) {
            if (board.getPiece(getX(), i) != null && this.isWhite == board.getPiece(getX(), i).isWhite && toY >= i) {
                return true;
            }
            if (board.getPiece(getX(), i) != null && this.isWhite != board.getPiece(getX(), i).isWhite && toY > i) {
                return true;
            }
        }

        for (int i = getY(); i >= 1; i--) {
            if (board.getPiece(getX(), i) != null && this.isWhite == board.getPiece(getX(), i).isWhite && toY <= i) {
                return true;
            }
            if (board.getPiece(getX(), i) != null && this.isWhite != board.getPiece(getX(), i).isWhite && toY < i) {
                return true;
            }
        }

        for (int i = getX(); i <= Board.COLUMNS; i++) {
            if (board.getPiece(i, getY()) != null && this.isWhite == board.getPiece(i, getY()).isWhite && toX >= i) {
                return true;
            }
            if (board.getPiece(i, getY()) != null && this.isWhite != board.getPiece(i, getY()).isWhite && toX > i) {
                return true;
            }
        }

        for (int i = getX(); i >= 1; i--) {
            if (board.getPiece(i, getY()) != null && this.isWhite == board.getPiece(i, getY()).isWhite && toX <= i) {
                return true;
            }
            if (board.getPiece(i, getY()) != null && this.isWhite != board.getPiece(i, getY()).isWhite && toX < i) {
                return true;
            }
        }

        return false;
    }
}
