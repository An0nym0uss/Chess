package chessgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

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

    // define colors
    private static Color WHITE_TILE = new Color(235, 235, 211);
    private static Color BLACK_TILE = new Color(125, 147, 93);
    private static Color HIGHLIGHT_TILE = new Color(245, 245, 50, 100);
    private static Color MOVE = new Color(90, 90, 90, 200);
    private static Color CAPTURE = new Color(220, 30, 140, 200);

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
        for (int col = 0; col < COLUMNS; col++) {
            setTile(col, 6, new Pawn(col, 6, true));
            setTile(col, 1, new Pawn(col, 1, false));
        }

        // setup kings
        wKing =  new King(4, 7, true);
        setTile(4, 7, wKing);
        
        bKing = new King(4, 0, false);
        setTile(4, 0, bKing);

        // setup queens
        setTile(3, 7, new Queen(3, 7, true));
        setTile(3, 0, new Queen(3, 0, false));

        // setup rooks
        setTile(0, 7, new Rook(0, 7, true));
        setTile(7, 7, new Rook(7, 7, true));
        setTile(0, 0, new Rook(0, 0, false));
        setTile(7, 0, new Rook(7, 0, false));

        // setup knights
        setTile(1, 7, new Knight(1, 7, true));
        setTile(6, 7, new Knight(6, 7, true));
        setTile(1, 0, new Knight(1, 0, false));
        setTile(6, 0, new Knight(6, 0, false));

        // setup bishops
        setTile(2, 7, new Bishop(2, 7, true));
        setTile(5, 7, new Bishop(5, 7, true));
        setTile(2, 0, new Bishop(2, 0, false));
        setTile(5, 0, new Bishop(5, 0, false));
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
     * Draw tiles and pieces on the board
     * @param g
     * @param frame
     */
    public void draw(Graphics g, JPanel panel, Piece chosen) {
        // draw tiles
        GamePanel gamePanel = (GamePanel) panel;
        for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if ((x + y) % 2 == 1) {
					g.setColor(BLACK_TILE);
				} else {
					g.setColor(WHITE_TILE);
				}
				g.fillRect(x * gamePanel.getTileWidth(), y * gamePanel.getTileWidth(), 
                    gamePanel.getTileWidth(), gamePanel.getTileWidth());
			}
		}

        if (chosen != null) {
            highlightTile(g, panel, chosen);
        }

        // draw pieces
        for (Piece piece : wPieces) {
            piece.draw(g, panel);
        }
        for (Piece piece : bPieces) {
            piece.draw(g, panel);
        }

        if (chosen != null) {
            drawValidMoves(g, panel, chosen);
        }
    }

    /**
     * Highlight the tile occupied by given piece
     * @param g
     * @param panel
     * @param chosen
     */
    private void highlightTile(Graphics g, JPanel panel, Piece chosen) {
        GamePanel gamePanel = (GamePanel) panel;
        int x = chosen.getX();
        int y = chosen.getY();
        // yellow with 40% transparency
        g.setColor(HIGHLIGHT_TILE);
        g.fillRect(x * gamePanel.getTileWidth(), y * gamePanel.getTileWidth(), 
            gamePanel.getTileWidth(), gamePanel.getTileWidth());
    }

    /**
     * Draw valid moves of chosen piece.
     * @param g
     * @param panel
     * @param chosen
     */
    private void drawValidMoves(Graphics g, JPanel panel, Piece chosen) {
        GamePanel gamePanel = (GamePanel) panel;

        for (Move move : chosen.getLegalMoves()) {
            int x = move.getToX();
            int y = move.getToY();
            if (getPiece(x, y) == null) {
                g.setColor(MOVE);
            } else {
                g.setColor(CAPTURE);
            }

            int size = gamePanel.getTileWidth();
            int diameter = gamePanel.getTileWidth() / 2;
            int offset_2 = (int) (2 * diameter * 0.41421);
            int offset = (int) ((size * 1.41421 - diameter - offset_2) / 2); 
            g.fillOval(x * size + offset, y * size + offset, diameter, diameter);
        }
    }

    /**
     * If turn is even (including 0), it's white's turn.
     * @return
     */
    public boolean isWhiteTurn() {
        return (turn & 1) == 0 ? true : false;
    }

    /**
     * Check if the game is in checkmate and 
     * generate available moves of pieces that can be done by
     * current side (determined by isWhiteTurn).
     * @return
     */
    public boolean checkmate() {
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
