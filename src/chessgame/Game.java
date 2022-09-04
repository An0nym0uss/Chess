package chessgame;

import java.awt.Graphics;
import java.util.List;
import javax.swing.JPanel;

import chessgame.pieces.Bishop;
import chessgame.pieces.King;
import chessgame.pieces.Knight;
import chessgame.pieces.Piece;
import chessgame.pieces.Queen;
import chessgame.pieces.Rook;

/**
 * Class {@code Game} contains a chess board and a chosen piece.
 * It provides methods that manipulates the chosen piece and
 * allows Chess game to run.
 */
public class Game {
    private Board board;
    private Piece chosen;
    private int checkmate;

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
            for (Move move : piece.getLegalMoves()) {
                piece.move(move.getToX(), move.getToY(), board);
                board.incrementTurn();

                // valid if the king is not checked after the move
                if (king != null && !board.isChecked(king)) {
                    piece.getValidMoves().add(move);
                } else if (king == null) {
                    piece.getValidMoves().add(move);
                }
                board.takeBackMove();
            }
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
            case 1:
                newPiece = new Rook(x, y, isWhite);
                break;
            case 2:
                newPiece = new Knight(x, y, isWhite);
                break;
            case 3:
                newPiece = new Bishop(x, y, isWhite);
                break;
            default:
                newPiece = new Queen(x, y, isWhite);
                break;
        }

        board.setTile(x, y, newPiece);
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
        checkmate = isWhiteTurn() ? GameState.BLACK_WINS : GameState.WHITE_WINS;

        if (pieces.stream().anyMatch(p -> !p.getValidMoves().isEmpty())) {
            checkmate = GameState.CONTINUE;
        }

        if (checkmate != GameState.CONTINUE && !king.isChecked()) {
            checkmate = GameState.STALE_MATE;
        }
    }

    /**
     * Recover the board to pevious step.
     */
    public void takeBackMove() {
        board.takeBackMove();
    }

    /**
     * Draw the game board.
     * 
     * @param g
     * @param panel
     */
    public void draw(Graphics g, JPanel panel) {
        board.draw(g, panel, chosen);
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
     * @return the checkmate
     */
    public int getCheckmate() {
        return checkmate;
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
