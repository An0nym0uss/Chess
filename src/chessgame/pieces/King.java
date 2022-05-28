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
        if (this.getStartY() <=3 && !this.isMoved && board.getPiece(0, 0).isMoved && board.getPiece(1, 0) == null && board.getPiece(2, 0) == null && board.getPiece(3, 0) == null) {
            for (int i = 0; i < Board.ROWS; i++) {
                for (int j = 0; j < Board.COLUMNS; j++) {
                    Piece piece = board.getPiece(i, j);
                    for (Move move : piece.getLegalMoves()) {
                        if (this.isWhite != piece.isWhite && move.getToY() != 0 && move.getToX() != 1 && move.getToX() != 2 && move.getToX() != 3) {
                            return true;
                        }
                    }
                }
            }
        }
        if (this.getStartY() <= 3 && !this.isMoved && board.getPiece(7, 0).isMoved && board.getPiece(6, 0) == null && board.getPiece(5, 0) == null) {
            for (int i = 0; i < Board.ROWS; i++) {
                for (int j = 0; j < Board.COLUMNS; j++) {
                    Piece piece = board.getPiece(i, j);
                    for (Move move : piece.getLegalMoves()) {
                        if (this.isWhite != piece.isWhite && move.getToY() != 0 && move.getToX() != 5 && move.getToX() != 6) {
                            return true;
                        }
                    }
                }
            }
        }
        if (this.getStartY() >= 4 && !this.isMoved && board.getPiece(0, 7).isMoved && board.getPiece(1, 7) == null && board.getPiece(2, 7) == null && board.getPiece(3, 7) == null) {
            for (int i = 0; i < Board.ROWS; i++) {
                for (int j = 0; j < Board.COLUMNS; j++) {
                    Piece piece = board.getPiece(i, j);
                    for (Move move : piece.getLegalMoves()) {
                        if (this.isWhite != piece.isWhite && move.getToY() != 7 && move.getToX() != 1 && move.getToX() != 2 && move.getToX() != 3) {
                            return true;
                        }
                    }
                }
            }
        }
        if (this.isWhite && !this.isMoved && board.getPiece(7, 7).isMoved && board.getPiece(6, 7) == null && board.getPiece(5, 7) == null) {
            for (int i = 0; i < Board.ROWS; i++) {
                for (int j = 0; j < Board.COLUMNS; j++) {
                    Piece piece = board.getPiece(i, j);
                    for (Move move : piece.getLegalMoves()) {
                        if (this.isWhite != piece.isWhite && move.getToY() != 7 && move.getToX() != 5 && move.getToX() != 6) {
                            return true;
                        }
                    }
                }
            }
        }
        return false; 
    }


    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
