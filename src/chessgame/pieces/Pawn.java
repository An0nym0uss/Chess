package chessgame.pieces;

import javax.imageio.ImageIO;
import java.io.IOException;

import chessgame.Board;

public class Pawn extends Piece {

    Pawn(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
        try {
            if (isWhite) {
                this.image = ImageIO.read(getClass().getResource(PieceImages.PAWN_W));
            } else{
                this.image = ImageIO.read(getClass().getResource(PieceImages.PAWN_B));
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

    /**
     * Check if there is available En Passant move. 
     * En Passant llows an enemy pawn that has just advanced two squares
     * to be captured by a horizontally adjacent pawn.
     * @param board
     */
    private boolean isEnPassant(Board board) {
        // TODO 
        return false; 
    }
    
}
