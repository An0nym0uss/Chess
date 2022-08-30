package chessgame.pieces;

import java.io.IOException;
import javax.imageio.ImageIO;

import chessgame.Board;

public class Rook extends Piece {
    public Rook(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
        try {
            this.image = isWhite ? ImageIO.read(getClass().getResource(PieceImages.ROOK_W))
                    : ImageIO.read(getClass().getResource(PieceImages.ROOK_B));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println(this.getClass().getName() + " image file not found.\n");
        }
    }

    @Override
    public boolean canMove(int toX, int toY, Board board) {
        if ((toX == this.x && toY == this.y) || (toX != this.x && toY != this.y)) {
            return false;
        }
        return !isObstructedOrthognally(toX, toY, board);
    }
}
