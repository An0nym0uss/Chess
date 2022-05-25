package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import chessgame.pieces.*;
import chessgame.Board;
import chessgame.Move;

public class pawnTest {

    @Test
    public void testInitial() {
        Board board = new Board();
        Piece pawn = board.getPiece(1, 1);
        pawn.allLegalMoves(board);
        assertEquals(1,pawn.getLegalMoves().get(0).getToX());
        assertEquals(2, pawn.getLegalMoves().get(0).getToY());
        assertEquals(1, pawn.getLegalMoves().get(1).getToX());
        assertEquals(3, pawn.getLegalMoves().get(1).getToY());
    }

    @Test
    public void testInitialWhite() {
        Board board = new Board();
        Piece pawn = board.getPiece(1, 6);
        pawn.allLegalMoves(board);
        assertEquals(1,pawn.getLegalMoves().get(0).getToX());
        assertEquals(4, pawn.getLegalMoves().get(0).getToY());
        assertEquals(1, pawn.getLegalMoves().get(1).getToX());
        assertEquals(5, pawn.getLegalMoves().get(1).getToY());
    }

    public static void main(String[] args) {
        Board board = new Board();
        Piece pawn = board.getPiece(1, 6);
        pawn.allLegalMoves(board);
        for (Move move : pawn.getLegalMoves()) {
            System.out.println(move.getToX());
            System.out.println(move.getToY());
        }
    }
}
