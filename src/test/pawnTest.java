package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import chessgame.pieces.*;
import chessgame.Board;
import chessgame.Game;
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

    @Test
    public void testEnPassant() {
        Game game = new Game(true);
        Board board = game.getBoard();
        Piece wPawn = new Pawn(1, 6, true);
        Piece bPawn = new Pawn(2, 1, false);
        Piece other = new Pawn(6, 1, false);

        board.setTile(1, 6, wPawn);
        board.setTile(2, 1, bPawn);
        board.setTile(6, 1, other);

        // move white pawn
        game.generateAllWhiteMoves();
        game.selectPiece(1, 6);
        game.move(1, 4);
        assertEquals(4, wPawn.getY());

        // move other black piece
        game.selectPiece(6, 1);
        game.move(6, 3);
        assertEquals(3, other.getY());

        // move white pawn
        game.selectPiece(1, 4);
        game.move(1, 3);
        assertEquals(3, wPawn.getY());

        // move black pawn
        game.selectPiece(2, 1);
        game.move(2, 3);
        assertEquals(3, bPawn.getY());

        // en passant, white pawn captures black pawn
        game.selectPiece(1, 3);
        assertEquals(true, wPawn.canMove(2, 2, board));
        game.move(2, 2);
        assertEquals(2, wPawn.getX());
        assertEquals(2, wPawn.getY());
        assertEquals(null, board.getPiece(2, 3));
    }

    public static void main(String[] args) {
        Board board = new Board();
        Piece pawn = board.getPiece(1, 1);
        pawn.move(1, 5, board);
        pawn.allLegalMoves(board);
        for (Move move : pawn.getLegalMoves()) {
            System.out.println(move.getToX());
            System.out.println(move.getToY());
        }
    }
}
