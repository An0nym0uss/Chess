package chessgame.chess.pieces;

import java.io.IOException;
import java.io.Serial;
import javax.imageio.ImageIO;

import chessgame.chess.board.Board;

/**
 * Bishop is a type of {@code Piece} that can move diagnally.
 */
public class Bishop extends Piece {
    @Serial
    private static final long serialVersionUID = 0x020001;

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
