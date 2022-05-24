package test;

import org.junit.jupiter.api.Test;

import chessgame.pieces.*;
import chessgame.Board;
import chessgame.Move;

public class pawnTest {

    public static void main(String[] args) {
        Board board = new Board();
        Piece pawn = new Pawn(2, 2, true);
        pawn.allLegalMoves(board);
        for (Move move : pawn.getLegalMoves()) {
            System.out.println(move.getToX());
            System.out.println(move.getToY());
        }
    }
}
