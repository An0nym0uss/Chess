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
        if (toX == this.getX() || toY == this.getY()) {
            return true;
        }
        return false;
    }

    private boolean isObstructed(int toX, int toY, Board board) {
        // obstructed by friendly piece including the tile the friendly piece is on, obstructed by hostile piece but not including the tile the hostile piece is on
        if (board.getPiece(toX, toY) != null && this.isWhite == board.getPiece(toX, toY).isWhite) {
            return true;
        }
        if (toY > getY()) {
            for (int i = getY() + 1; i < toY; i++) {
                if (board.getPiece(getX(), i) != null) {
                    return true;
                }
            }
        }
        if (toY < getY()) {
            for (int i = getY() - 1; i > toY; i--) {
                if (board.getPiece(getX(), i) != null) {
                    return true;
                }
            }
        }
        if (toY > getX()) {
            for (int i = getX() + 1; i < toX; i++) {
                if (board.getPiece(i, getY()) != null) {
                    return true;
                }
            }
        }
        if (toY < getX()) {
            for (int i = getX() - 1; i > toX; i--) {
                if (board.getPiece(i, getY()) != null) {
                    return true;
                }
            }
        }
        
        return false;
    }
}
