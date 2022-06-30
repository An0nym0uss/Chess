package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import chessgame.pieces.*;
import chessgame.Board;

public class rockTest {

    @Test
    public void testInitial() {
        Board board = new Board();
        Piece rock = board.getPiece(0, 0);
        rock.allLegalMoves(board);
        assertTrue(rock.getLegalMoves().isEmpty());
    }

    @Test
    public void testInitial_PathClear() {
        Board board = new Board();
        Piece rock = board.getPiece(0, 0);
        board.removePieceFromBoard(0, 1);
        rock.allLegalMoves(board);
        assertEquals(6, rock.getLegalMoves().size());
        assertEquals(0, rock.getLegalMoves().get(0).getToX());
        assertEquals(1, rock.getLegalMoves().get(0).getToY());
        assertEquals(2, rock.getLegalMoves().get(1).getToY());
        assertEquals(3, rock.getLegalMoves().get(2).getToY());
        assertEquals(4, rock.getLegalMoves().get(3).getToY());
        assertEquals(5, rock.getLegalMoves().get(4).getToY());
        assertEquals(6, rock.getLegalMoves().get(5).getToY());
    }

    @Test
    public void testInitial_PathClear_White() {
        Board board = new Board();
        Piece rock = board.getPiece(0, 7);
        board.removePieceFromBoard(0, 6);
        rock.allLegalMoves(board);
        assertEquals(6, rock.getLegalMoves().size());
        assertEquals(0, rock.getLegalMoves().get(0).getToX());
        assertEquals(1, rock.getLegalMoves().get(0).getToY());
        assertEquals(2, rock.getLegalMoves().get(1).getToY());
        assertEquals(3, rock.getLegalMoves().get(2).getToY());
        assertEquals(4, rock.getLegalMoves().get(3).getToY());
        assertEquals(5, rock.getLegalMoves().get(4).getToY());
        assertEquals(6, rock.getLegalMoves().get(5).getToY());
    }

    @Test
    public void testObstruction() {
        Board board = new Board();
        Piece pawn = board.getPiece(0, 6);
        Piece rock = board.getPiece(0, 7);
        pawn.move(0, 4, board);
        rock.allLegalMoves(board);
        assertEquals(0, rock.getLegalMoves().get(0).getToX());
        assertEquals(5, rock.getLegalMoves().get(0).getToY());
        assertEquals(0, rock.getLegalMoves().get(1).getToX());
        assertEquals(6, rock.getLegalMoves().get(1).getToY());
        assertEquals(2, rock.getLegalMoves().size());
    }

    @Test
    public void testMiddle() {
        Board board = new Board();
        Piece rock = board.getPiece(0, 7);
        rock.move(5, 5, board);
        rock.allLegalMoves(board);
        assertEquals(11, rock.getLegalMoves().size());
    }
}
