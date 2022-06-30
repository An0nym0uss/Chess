package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import chessgame.pieces.*;
import chessgame.Board;

public class bishopTest {
    @Test
    public void testInitial_PathClear() {
        Board board = new Board();
        Piece pawn = board.getPiece(1, 1);
        Piece bishop = board.getPiece(2, 0);
        pawn.move(1, 3, board);
        bishop.allLegalMoves(board);
        assertEquals(0, bishop.getLegalMoves().get(0).getToX());
        assertEquals(2, bishop.getLegalMoves().get(0).getToY());
        assertEquals(1, bishop.getLegalMoves().get(1).getToY());
        assertEquals(1, bishop.getLegalMoves().get(1).getToY());
    }
}
