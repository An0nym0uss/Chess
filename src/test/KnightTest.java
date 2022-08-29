package test;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import chessgame.pieces.*;
import chessgame.Board;

public class KnightTest {
    @Test
    public void testInitial() {
        Board board = new Board();
        Piece knight = board.getPiece(1, 0);
        knight.allLegalMoves(board);
        assertEquals(0, knight.getLegalMoves().get(0).getToX());
        assertEquals(2, knight.getLegalMoves().get(0).getToY());
        assertEquals(2, knight.getLegalMoves().get(1).getToX());
        assertEquals(2, knight.getLegalMoves().get(1).getToY());
    }

}
