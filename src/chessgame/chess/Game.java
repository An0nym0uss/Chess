package chessgame.chess;

import java.awt.Graphics;
import java.util.List;
import javax.swing.JPanel;

import chessgame.chess.board.Board;
import chessgame.chess.pieces.Bishop;
import chessgame.chess.pieces.King;
import chessgame.chess.pieces.Knight;
import chessgame.chess.pieces.Piece;
import chessgame.chess.pieces.Queen;
import chessgame.chess.pieces.Rook;
import chessgame.chess.util.Move;

/**
 * Class {@code Game} contains a chess board and a chosen piece.
 * It provides methods that manipulates the chosen piece and
 * allows Chess game to run.
 */
public class Game implements Drawable {
    private Board board;
    private Piece chosen;
    private int gameState;

    public static int SELECT = 0;
    public static int MOVE = 1;

    /**
     * Construct a game with default Chess board.
     */
    public Game() {
        this.board = new Board();
        changeSide();
    }

    /**
     * Construct a game with a board of given file.
     * 
     * @param empty {@code true} creates an empty board
     */
    public Game(String fileName) {
        this.board = new Board(fileName);
        changeSide();
    }

    /**
     * Select the piece on tile or
     * move chosen piece to the tile.
     * 
     * @param x column
     * @param y row
     * @return {@code SELECT} if select, or {@code MOVE} if move.
     */
    public int selectOrMove(int x, int y) {
        Piece target = board.getPiece(x, y);
        if (target != null && target.isWhite() == isWhiteTurn()) {
            selectPiece(x, y);
            return SELECT;
        } else {
            move(x, y);
            return MOVE;
        }
    }

    /**
     * Choose the piece on the board with given x and y coordinates.
     * Assuming that given tile has piece of correct color.
     * 
     * @param x column
     * @param y row
     */
    public void selectPiece(int x, int y) {
        chosen = board.getPiece(x, y);
    }

    /**
     * Move the chosen piece to the tile of given coordinates if is valid.
     * 
     * @param x column
     * @param y row
     */
    public void move(int toX, int toY) {
        if (chosen != null && isValidMove(toX, toY)) {
            if (!chosen.isMoved()) {
                chosen.setMoved(true);
                chosen.setTurnFirstMoved(board.getTurn());
            }

            chosen.move(toX, toY, board);
            board.incrementTurn();
            changeSide();
        }
    }

    /**
     * @param toX column
     * @param toY row
     * @return {@code true} if the chosen piece can move to the tile, {@code false}
     *         otherwise
     */
    private boolean isValidMove(int toX, int toY) {
        Move move = new Move(chosen.getX(), chosen.getY(), toX, toY, chosen);

        return chosen.getValidMoves().contains(move);
    }

    /**
     * Generate legal moves of all white pieces.
     */
    public void generateAllWhiteMoves() {
        board.getwPieces().forEach(p -> p.allLegalMoves(board));
    }

    /**
     * Generate legal moves of all black pieces.
     */
    public void generateAllBlackMoves() {
        board.getbPieces().forEach(p -> p.allLegalMoves(board));
    }

    /**
     * Check if checkmate and generate available moves of pieces.
     */
    public void changeSide() {
        board.setKingsChecked();

        List<Piece> pieces;
        King king;
        if (isWhiteTurn()) {
            pieces = board.getwPieces();
            king = board.getWKing();

            generateAllWhiteMoves();
            pieces.forEach(p -> p.getValidMoves().clear());
        } else {
            pieces = board.getbPieces();
            king = board.getBKing();

            generateAllBlackMoves();
            pieces.forEach(p -> p.getValidMoves().clear());
        }

        for (Piece piece : pieces) {
            piece.allValidMoves(board, king);
        }

        checkmate();
    }

    /**
     * Promote pawn to given target, where
     * {@code 0} is Queen, {@code 1} is Rook, {@code 2} is Knight and {@code 3} is
     * Bishop.
     * 
     * @param target piece that the pawn promotes to
     */
    public void promotion(int target) {
        int x = chosen.getX();
        int y = chosen.getY();
        boolean isWhite = chosen.isWhite();

        board.removePieceFromBoard(x, y);

        Piece newPiece;
        switch (target) {
            case 1 -> newPiece = new Rook(x, y, isWhite);
            case 2 -> newPiece = new Knight(x, y, isWhite);
            case 3 -> newPiece = new Bishop(x, y, isWhite);
            default -> newPiece = new Queen(x, y, isWhite);
        }

        board.setTile(x, y, newPiece);

        checkmate();
    }

    /**
     * Set current state of the game to
     * {@code Checkmate.CONTINUE},
     * {@code Checkmate.WHITE_WINS},
     * {@code Checkmate.BLACK_WINS}, or
     * {@code Checkmate.STALE_MATE}.
     */
    public void checkmate() {
        if ((isWhiteTurn() && board.getWKing() == null) ||
                (!isWhiteTurn() && board.getBKing() == null)) {
            // king not found, do nothing
            return;
        }

        List<Piece> pieces = isWhiteTurn() ? board.getwPieces() : board.getbPieces();
        King king = isWhiteTurn() ? board.getWKing() : board.getBKing();
        gameState = isWhiteTurn() ? GameState.BLACK_WINS : GameState.WHITE_WINS;

        if (pieces.stream().anyMatch(p -> !p.getValidMoves().isEmpty())) {
            gameState = GameState.CONTINUE;
        }

        if (gameState != GameState.CONTINUE && !king.isChecked()) {
            gameState = GameState.STALE_MATE;
        }
    }

    /**
     * Recover the board to pevious step.
     */
    public void takeBackMove() {
        board.takeBackMove();
    }

    @Override
    public void draw(Graphics g, JPanel panel) {
        board.draw(g, panel);

        if (chosen != null) {
            highlightTile(g, panel, chosen);
            chosen.draw(g, panel);
            drawValidMoves(g, panel, chosen);
        }
    }

    /**
     * Highlight the tile occupied by given piece.
     * 
     * @param g      the Graphics
     * @param panel  the panel
     * @param chosen given chess piece
     */
    private void highlightTile(Graphics g, JPanel panel, Piece chosen) {
        GamePanel gamePanel = (GamePanel) panel;
        int x = chosen.getX();
        int y = chosen.getY();

        g.setColor(TileColor.HIGHLIGHT_TILE);
        g.fillRect(x * gamePanel.getTileWidth(), y * gamePanel.getTileWidth(),
                gamePanel.getTileWidth(), gamePanel.getTileWidth());
    }

    /**
     * Draw valid moves of chosen piece.
     * 
     * @param g      the Graphics
     * @param panel  the panel
     * @param chosen given chess piece
     */
    private void drawValidMoves(Graphics g, JPanel panel, Piece chosen) {
        GamePanel gamePanel = (GamePanel) panel;

        for (Move move : chosen.getValidMoves()) {
            int x = move.getToX();
            int y = move.getToY();
            if (board.getPiece(x, y) == null) {
                g.setColor(TileColor.MOVE);
            } else {
                g.setColor(TileColor.CAPTURE);
            }

            int size = gamePanel.getTileWidth();
            int diameter = gamePanel.getTileWidth() / 2;
            int offset_2 = (int) (2 * diameter * 0.41421);
            int offset = (int) ((size * 1.41421 - diameter - offset_2) / 2);
            g.fillOval(x * size + offset, y * size + offset, diameter, diameter);
        }
    }

    /**
     * @return {@code true} if is white's turn ({@code turn} is even)
     */
    public boolean isWhiteTurn() {
        return board.isWhiteTurn();
    }

    /**
     * Set {@code chosen} piece to null.
     */
    public void deselectChosen() {
        this.chosen = null;
    }

    // getters and setters

    /**
     * @return the game state
     */
    public int getGameState() {
        return gameState;
    }

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
