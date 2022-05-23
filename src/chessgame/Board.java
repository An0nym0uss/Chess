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
    private Piece wKing;
    private Piece bKing;
    private List<Piece> wPieces = new ArrayList<>();
    private List<Piece> bPieces = new ArrayList<>();
    private Stack<Move> moves = new Stack<>();
    private Integer turn;
    private Map<Integer, Piece> deadPieces = new HashMap<>();


    public Board() {
        tiles = new Piece[ROWS][COLUMNS];
        turn = 0;
        setup();
    }

    /**
     * Initialise the board to put pieces on tiles.
     * Black pieces on the top (x = 0 || 1)
     * White pieces at the bottom (x = 6 || 7)
     */
    public void setup() {
        // setup pawns
        for (int yCord = 0; yCord < COLUMNS; yCord++) {
            setTile(1, yCord, new Pawn(6, yCord, true));
            setTile(6, yCord, new Pawn(1, yCord, false));
        }

        // setup kings
        wKing =  new King(7, 4, true);
        setTile(7, 4, wKing);
        
        bKing = new King(0, 4, false);
        setTile(0, 4, bKing);

        // setup queens
        setTile(7, 3, new Queen(7, 3, true));
        setTile(0, 3, new Queen(0, 3, false));

        // setup rooks
        setTile(7, 0, new Rook(7, 0, true));
        setTile(7, 7, new Rook(7, 7, true));
        setTile(0, 0, new Rook(0, 0, false));
        setTile(0, 7, new Rook(0, 7, false));

        // setup knights
        setTile(7, 1, new Knight(7, 1, true));
        setTile(7, 6, new Knight(7, 6, true));
        setTile(0, 1, new Knight(0, 1, false));
        setTile(0, 6, new Knight(0, 6, false));

        // setup bishops
        setTile(7, 2, new Bishop(7, 2, true));
        setTile(7, 5, new Bishop(7, 5, true));
        setTile(0, 2, new Bishop(0, 2, false));
        setTile(0, 5, new Bishop(0, 5, false));
    }

    /**
     * Get the piece on the tile of given x and y coordinates
     * @param x
     * @param y
     * @return
     */
    public Piece getPiece(int x, int y) {
        return tiles[x][y];
    }

    /**
     * set the position of the piece to the tile
     * @param x
     * @param y
     * @param piece
     */
    public void setTile(int x, int y, Piece piece) {
        tiles[x][y] = piece;
        if (piece.isWhite()) {
            if (!wPieces.contains(piece)) {
                wPieces.add(piece);
            }
        } else {
            if (!bPieces.contains(piece)) {
                bPieces.add(piece);
            }
        }
    }

    /**
     * Remove the piece in the tile
     * @param x
     * @param y
     */
    public void removePiece(int x, int y) {
        Piece piece = getPiece(x, y);
        if (piece != null) {
            if (piece.isWhite()) {
                wPieces.remove(piece);
            } else {
                bPieces.remove(piece);
            }
        }
        tiles[x][y] = null;
    }

    /**
     * If turn is even (including 0), it's white's turn.
     * @return
     */
    public boolean isWhiteTurn() {
        return (turn & 1) == 0 ? true : false;
    }

    /**
     * Check if the game is in checkmate.
     * @return
     */
    public boolean checkMate() {
        return false;
    }

    /**
     * Increase turn by 1.
     */
    public void incrementTurn() {
        this.turn++;
    }

    /**
     * Decrease turn by 1.
     */
    public void decrementTurn() {
        this.turn--;
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
    public Stack<Move> getMoves() {
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
