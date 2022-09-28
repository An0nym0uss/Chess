package chessgame.chess.pieces;

import java.io.IOException;
import javax.imageio.ImageIO;

import chessgame.chess.board.Board;

/**
 * Knight is a type of {@code Piece} that can move to any of the closest squares
 * that are not on the same row, column, or diagonal. (in an 'L' shape)
 */
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
