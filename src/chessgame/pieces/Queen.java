package chessgame.pieces;

import javax.imageio.ImageIO;
import java.io.IOException;

import chessgame.Board;

public class Queen extends Piece{
    public Queen(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
        try {
            if (isWhite) {
                this.image = ImageIO.read(getClass().getResource(PieceImages.QUEEN_W));
            } else{
                this.image = ImageIO.read(getClass().getResource(PieceImages.QUEEN_B));
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
