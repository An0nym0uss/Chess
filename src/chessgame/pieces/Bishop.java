package chessgame.pieces;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.lang.Math;

import chessgame.Board;

public class Bishop extends Piece {
    public Bishop(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
        try {
            this.image = isWhite ? ImageIO.read(getClass().getResource(PieceImages.BISHOP_W))
                    : ImageIO.read(getClass().getResource(PieceImages.BISHOP_B));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println(this.getClass().getName() + " image file not found.\n");
        }
    }

    @Override
    public boolean canMove(int toX, int toY, Board board) {
        if (toX == this.getX() && toY == this.getY()) {
            return false;
        }

        if (Math.abs(toX - this.getX()) == Math.abs(toY - this.getY())) {
            return !isObstructedDiagonally(toX, toY, board);
        }

        return false;
    }
}
