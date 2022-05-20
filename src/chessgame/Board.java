package chessgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import chessgame.pieces.*;

/**
 * Class {@code Board} contains a 8 * 8 board and information of Pieces.
 * It also stores previous moves which allows us to trace back steps.
 */
public class Board {
    public static int ROWS = 8;
    public static int COLUMNS = 8;
    private Piece[][] tiles;
    private List<Piece> wPieces = new ArrayList<>();
    private List<Piece> bPieces = new ArrayList<>();
    private Stack<Piece> moves = new Stack<>();
    private Integer turn;
    private Map<Integer, Piece> deadPieces = new HashMap<>();


    public Board() {
        tiles = new Piece[ROWS][COLUMNS];
    }

    /**
     * Initialise the board to put pieces on tiles.
     */
    public void setup() {

    }

    // getters and setters

    /**
     * @return the tiles
     */
    public Piece[][] getTiles() {
        return tiles;
    }

    /**
     * @return the wPieces
     */
    public List<Piece> getwPieces() {
        return wPieces;
    }

    /**
     * @return the bPieces
     */
    public List<Piece> getbPieces() {
        return bPieces;
    }

    /**
     * @return the moves
     */
    public Stack<Piece> getMoves() {
        return moves;
    }

    /**
     * @return the turn
     */
    public Integer getTurn() {
        return turn;
    }

    /**
     * @return the deadPieces
     */
    public Map<Integer, Piece> getDeadPieces() {
        return deadPieces;
    }
}
