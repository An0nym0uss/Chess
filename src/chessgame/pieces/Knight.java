package chessgame.pieces;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.lang.Math;

import chessgame.Board;

public class Knight extends Piece {
    public Knight(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
        try {
            this.image = isWhite ? ImageIO.read(getClass().getResource(PieceImages.KNIGHT_W))
                    : ImageIO.read(getClass().getResource(PieceImages.KNIGHT_B));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println(this.getClass().getName() + " image file not found.\n");
        }
    }

    @Override
    public boolean canMove(int toX, int toY, Board board) {
        if (Math.abs(toX - getX()) == 2 && Math.abs(toY - getY()) == 1) {
            return board.getPiece(toX, toY) == null || board.getPiece(toX, toY).isWhite != this.isWhite;
        } else if (Math.abs(toX - getX()) == 1 && Math.abs(toY - getY()) == 2) {
            return board.getPiece(toX, toY) == null || board.getPiece(toX, toY).isWhite != this.isWhite;
        }

        return false;
    }
}
