package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;

import chessgame.Board;
import chessgame.Game;
import chessgame.pieces.Pawn;
import chessgame.pieces.Piece;


public class takeBackTest {
    @Test
    public void pawnTest() {
        Game game = new Game(true);
        Board board = game.getBoard();
        Piece pawn = new Pawn(1, 6, true);
        board.setTile(1, 6, pawn);

        pawn.move(1, 4, board);
        board.incrementTurn();
        assertEquals(4, pawn.getY());

        game.takeBackMove();
        game.changeSide();
        assertEquals(6, pawn.getY());

        pawn.move(1, 4, board);
        assertEquals(4, pawn.getY());
    }
}
