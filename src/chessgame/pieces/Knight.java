package chessgame.pieces;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.lang.Math;

import chessgame.Board;

public class Knight extends Piece {
    public Knight(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
        try {
            if (isWhite) {
                this.image = ImageIO.read(getClass().getResource(PieceImages.KNIGHT_W));
            } else{
                this.image = ImageIO.read(getClass().getResource(PieceImages.KNIGHT_B));
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
        if (Math.abs(toX - getX()) == 2 && Math.abs(toY - getY()) == 1) {
            if (board.getPiece(toX, toY).isWhite == this.isWhite) {
                return false;
            }
            return true;
        }
        if (Math.abs(toX - getX()) == 1 && Math.abs(toY - getY()) == 2) {
            if (board.getPiece(toX, toY).isWhite == this.isWhite) {
                return false;
            }
            return true;
        }
        return false;
    }
}
