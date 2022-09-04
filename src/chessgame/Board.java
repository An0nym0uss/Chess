package chessgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

import chessgame.pieces.*;

/**
 * Class {@code Board} contains a 8 * 8 board and information of Pieces.
 * It also stores previous moves which allows us to trace back steps.
 */
public class Board {
    public static int ROWS = 8;
    public static int COLUMNS = 8;

    private Piece[][] tiles = new Piece[ROWS][COLUMNS];
    private King wKing;
    private King bKing;
    private List<Piece> wPieces = new ArrayList<>();
    private List<Piece> bPieces = new ArrayList<>();
    private Stack<Move> moves = new Stack<>();
    private Integer turn = 0;
    private Map<Integer, Piece> deadPieces = new HashMap<>();

    // define colors
    private static Color WHITE_TILE = new Color(235, 235, 211);
    private static Color BLACK_TILE = new Color(125, 147, 93);
    private static Color HIGHLIGHT_TILE = new Color(245, 245, 50, 100);
    private static Color MOVE = new Color(90, 90, 90, 200);
    private static Color CAPTURE = new Color(220, 30, 140, 200);
    private static Color CHECKED = new Color(235, 20, 20, 100);

    public Board() {
        this("basic.txt");
    }

    public Board(String fileName) {
        setup(fileName);
    }

    /**
     * Initialise the board to put pieces on tiles.
     * Black pieces on the top (x = 0 || 1)
     * White pieces at the bottom (x = 6 || 7)
     */
    private void setup(String fileName) {
        String boardStr = BoardReader.readBoard(fileName);
        int index = 0;
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                while (index < boardStr.length() && Character.isWhitespace(boardStr.charAt(index))) {
                    ++index;
                }

                if (index >= boardStr.length()) {
                    System.out.println("WARNING: Board text file is not complete\n");
                    break;
                }

                Character ch = boardStr.charAt(index);
                switch (Character.toLowerCase(ch)) {
                    case 'b':
                        Bishop bishop = ch.equals('b') ? new Bishop(x, y, true)
                                : new Bishop(x, y, false);
                        setTile(x, y, bishop);
                        break;
                    case 'k':
                        King king = ch.equals('k') ? new King(x, y, true)
                                : new King(x, y, false);
                        setTile(x, y, king);
                        break;
                    case 'n':
                        Knight knight = ch.equals('n') ? new Knight(x, y, true)
                                : new Knight(x, y, false);
                        setTile(x, y, knight);
                        break;
                    case 'p':
                        Pawn pawn = ch.equals('p') ? new Pawn(x, y, true)
                                : new Pawn(x, y, false);
                        setTile(x, y, pawn);
                        break;
                    case 'q':
                        Queen queen = ch.equals('q') ? new Queen(x, y, true)
                                : new Queen(x, y, false);
                        setTile(x, y, queen);
                        break;
                    case 'r':
                        Rook rook = ch.equals('r') ? new Rook(x, y, true)
                                : new Rook(x, y, false);
                        setTile(x, y, rook);
                        break;
                    case '*':
                        // do nothing
                        break;
                    default:
                        System.out.println("WARNING: Unexpected charater " + ch + " of index " + index + "\n");
                }

                ++index;
            }
        }

        wKing = getWKing();
        bKing = getBKing();
    }

    /**
     * @param x column
     * @param y row
     * @return the piece on the tile of given x and y coordinates
     */
    public Piece getPiece(int x, int y) {
        return tiles[x][y];
    }

    /**
     * Set the position of the piece to the tile.
     * 
     * @param x     column
     * @param y     row
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
     * Remove the piece in the tile.
     * 
     * @param x column
     * @param y row
     */
    public void removePiece(int x, int y) {
        tiles[x][y] = null;
    }

    /**
     * Remove the piece in the tile and remove from the pieces list.
     * 
     * @param x column
     * @param y row
     */
    public void removePieceFromBoard(int x, int y) {
        Piece piece = getPiece(x, y);
        if (piece != null) {
            if (piece.isWhite()) {
                wPieces.remove(piece);
            } else {
                bPieces.remove(piece);
            }
        }
        removePiece(x, y);
    }

    /**
     * Recover the board to pevious step.
     */
    public void takeBackMove() {
        if (getTurn() == 0) {
            // do nothing
            return;
        }

        decrementTurn();

        Move prevMove = getMoves().pop();
        int fromX = prevMove.getFromX();
        int fromY = prevMove.getFromY();
        int toX = prevMove.getToX();
        int toY = prevMove.getToY();
        Piece piece = prevMove.getPiece();
        Piece deadPiece = getDeadPieces().remove(getTurn());

        // take back last movement
        if (prevMove.isPromotion() && !(getPiece(toX, toY) instanceof Pawn)) {
            removePieceFromBoard(toX, toY);
        } else {
            removePiece(toX, toY);
        }

        piece.setX(fromX);
        piece.setY(fromY);
        setTile(fromX, fromY, piece);
        if (piece.getTurnFirstMoved() == getTurn()) {
            piece.setMoved(false);
            piece.setTurnFirstMoved(0);
        }

        // recover captured piece
        if (deadPiece != null) {
            setTile(deadPiece.getX(), deadPiece.getY(), deadPiece);
        }

        if (prevMove.isCastling()) {
            // move rook back to its place
            if (toX == fromX + 2) {
                // Castle kingside
                int xRook = toX - 1;
                Piece rook = getPiece(xRook, toY);

                // move rook
                rook.setX(7);
                removePiece(xRook, toY);
                setTile(7, fromY, rook);
            } else if (toX == fromX - 2) {
                // Castle queenside
                int xRook = toX + 1;
                Piece rook = getPiece(xRook, toY);

                // move rook
                rook.setX(0);
                removePiece(xRook, toY);
                setTile(0, fromY, rook);
            }
        }
    }

    /**
     * Draw tiles and pieces on the board.
     * 
     * @param g
     * @param frame
     */
    public void draw(Graphics g, JPanel panel, Piece chosen) {
        // draw tiles
        GamePanel gamePanel = (GamePanel) panel;
        for (int x = 0; x < COLUMNS; x++) {
            for (int y = 0; y < ROWS; y++) {
                if ((x + y) % 2 == 1) {
                    g.setColor(BLACK_TILE);
                } else {
                    g.setColor(WHITE_TILE);
                }
                g.fillRect(x * gamePanel.getTileWidth(), y * gamePanel.getTileWidth(),
                        gamePanel.getTileWidth(), gamePanel.getTileWidth());
            }
        }

        drawCheckedKing(g, panel);

        if (chosen != null) {
            highlightTile(g, panel, chosen);
        }

        // draw pieces
        wPieces.forEach(p -> p.draw(g, panel));
        bPieces.forEach(p -> p.draw(g, panel));

        if (chosen != null) {
            drawValidMoves(g, panel, chosen);
        }
    }

    /**
     * Highlight the tile occupied by given piece.
     * 
     * @param g
     * @param panel
     * @param chosen given chess piece
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
     * 
     * @param g
     * @param panel
     * @param chosen given chess piece
     */
    private void drawValidMoves(Graphics g, JPanel panel, Piece chosen) {
        GamePanel gamePanel = (GamePanel) panel;

        for (Move move : chosen.getValidMoves()) {
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
     * Mark the king tile red if it's checked.
     * 
     * @param g
     * @param panel
     */
    private void drawCheckedKing(Graphics g, JPanel panel) {
        GamePanel gamePanel = (GamePanel) panel;
        int width = gamePanel.getTileWidth();

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(width / 15));
        g2.setColor(CHECKED);

        if (wKing != null && wKing.isChecked()) {
            g2.drawRect(wKing.getX() * width, wKing.getY() * width, width, width);
        }

        if (bKing != null && bKing.isChecked()) {
            g2.drawRect(bKing.getX() * width, bKing.getY() * width, width, width);
        }
    }

    /**
     * @return {@code true} if is white's turn ({@code turn} is even), {@code false}
     *         otherwise
     */
    public boolean isWhiteTurn() {
        return (turn & 1) == 0 ? true : false;
    }

    /**
     * Check if kings are being checked, and set {@code isChecked}.
     */
    public void setKingsChecked() {
        if (wKing != null) {
            wKing.setChecked(isChecked(wKing));
        }
        if (bKing != null) {
            bKing.setChecked(isChecked(bKing));
        }
    }

    /**
     * @param king
     * @return {@code} true} if king is being checked, {@code false} otherwise
     */
    public boolean isChecked(King king) {
        List<Piece> oponentPieces = king.isWhite() ? getbPieces() : getwPieces();

        return oponentPieces.stream().anyMatch(p -> {
            p.allLegalMoves(this);
            return p.getLegalMoves().stream()
                    .anyMatch(m -> m.getToX() == king.getX() && m.getToY() == king.getY());
        });
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
     * @return white king, or {@code null} if not exist
     */
    public King getWKing() {
        return wKing != null ? wKing
                : (King) wPieces.stream()
                        .filter(p -> p instanceof King)
                        .findFirst()
                        .orElse(null);
    }

    /**
     * @return black king, or {@code null} if not exist
     */
    public King getBKing() {
        return bKing != null ? bKing
                : (King) bPieces.stream()
                        .filter(p -> p instanceof King)
                        .findFirst()
                        .orElse(null);
    }

    /**
     * @return the deadPieces
     */
    public Map<Integer, Piece> getDeadPieces() {
        return deadPieces;
    }
}
