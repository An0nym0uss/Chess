package test;

import org.junit.jupiter.api.Test;

import chessgame.pieces.*;
import chessgame.Board;

public class pawnTest {

    public static void main(String[] args) {
        Board board = new Board();
        Piece pawn = new Pawn(2, 2, true);
        System.out.println(pawn.canMove(2, 3, board));
    }
}
