package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;

import chessgame.chess.Game;
import chessgame.chess.board.Board;
import chessgame.chess.pieces.Pawn;
import chessgame.chess.pieces.Piece;


public class TakeBackTest {
    @Test
    public void pawnTest() {
        Game game = new Game("empty.txt");
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
