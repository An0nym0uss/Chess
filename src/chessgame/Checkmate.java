package chessgame;

import java.util.List;

import chessgame.pieces.King;
import chessgame.pieces.Piece;

public class Checkmate {
    public static final int CONTINUE = 0;
    public static final int WHITE_WINS = 1;
    public static final int BLACK_WINS = 2;
    public static final int STALE_MATE = 3;

    public static int checkmate(Board board) {
        List<Piece> pieces;
        King king;
        int state;

        if (board.isWhiteTurn()) {
            pieces = board.getwPieces();
            king = board.getWKing();
            state = BLACK_WINS;
        } else {
            pieces = board.getbPieces();
            king = board.getBKing();
            state = WHITE_WINS;
        }

        for (Piece piece : pieces) {
            if (!piece.getValidMoves().isEmpty()) {
                return CONTINUE;
            }
        }

        if (state != CONTINUE && !king.isChecked()) {
            return STALE_MATE;
        }

        return state;
    }
}
