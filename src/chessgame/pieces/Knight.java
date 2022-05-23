package chessgame.pieces;

import java.io.IOException;
import javax.imageio.ImageIO;

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
        // TODO Auto-generated method stub
        return false;
    }
}
