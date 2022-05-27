package chessgame.pieces;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.lang.Math;

import chessgame.Board;
import chessgame.Move;

public class King extends Piece {

    private boolean checked;

    public King(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
        this.checked = false;
        try {
            if (isWhite) {
                this.image = ImageIO.read(getClass().getResource(PieceImages.KING_W));
            } else {
                this.image = ImageIO.read(getClass().getResource(PieceImages.KING_B));
            }
        } catch (IOException e) {
            System.out.println("Image file not found: " + e.getMessage());
        }
    }

    @Override
    public boolean canMove(int toX, int toY, Board board) {
        if (board.getPiece(toX, toY) != null && this.isWhite == board.getPiece(toX, toY).isWhite) {
            return false;
        }
        if ((Math.abs(toX - getX()) == 1 && toY == getY())
        || (Math.abs(toY - getY()) == 1 && toX == getX()) 
        || (Math.abs(toX - getX()) == 1 && Math.abs(toY - getY()) == 1)) {
            return true;
        }
        return false;
    }
    
    private boolean isCastling(Board board) {
        // TODO 
        return false;
    }


    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
