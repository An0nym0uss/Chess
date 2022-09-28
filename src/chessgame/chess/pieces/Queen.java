package chessgame.chess.pieces;

import java.io.IOException;
import java.io.Serial;
import javax.imageio.ImageIO;

import chessgame.chess.board.Board;

/**
 * Queen is a type of {@code Piece} that can move along a column, row or diagnal.
 */
public class Queen extends Piece {
    @Serial
    private static final long serialVersionUID = 0x020005;

    public Queen(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
        try {
            this.image = isWhite ? ImageIO.read(getClass().getResource(PieceImages.QUEEN_W))
                    : ImageIO.read(getClass().getResource(PieceImages.QUEEN_B));
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
        } else if (toX == this.getX() || toY == this.getY()) {
            return !isObstructedOrthognally(toX, toY, board);
        } else if (Math.abs(toX - this.getX()) == Math.abs(toY - this.getY())) {
            return !isObstructedDiagonally(toX, toY, board);
        }
        return false;
    }
}
