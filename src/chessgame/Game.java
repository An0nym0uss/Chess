package chessgame;

import chessgame.pieces.Piece;

public class Game {
    private Board board;
    private Piece chosen;

    public Game() {

    }

    /**
     * Choose the piece on the board with given x and y coordinates.
     * @param x
     * @param y
     */ 
    public void selectPiece(int x, int y) {

    }

    /**
     * Move the chosen piece to the tile of given coordinates.
     * @param x
     * @param y
     */
    public void move(int x, int y) {

    }

    /**
     * Check if the game is in checkmate.
     * @return
     */
    public boolean checkMate() {
        return false;
    }

    /**
     * Recover the board to pevious step.
     */
    public void takeBackMove() {

    }

    // getters and setters

    /**
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @return the chosen
     */
    public Piece getChosen() {
        return chosen;
    }
}
