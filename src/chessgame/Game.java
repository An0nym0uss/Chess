package chessgame;

import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import chessgame.pieces.Bishop;
import chessgame.pieces.King;
import chessgame.pieces.Knight;
import chessgame.pieces.Pawn;
import chessgame.pieces.Piece;
import chessgame.pieces.Queen;
import chessgame.pieces.Rook;

public class Game {
    private Board board;
    private Piece chosen;
    private int checkmate;

    public static int SELECT = 0;
    public static int MOVE = 1;

    public Game() {
        this.board = new Board();
        changeSide();
    }

    public Game(boolean empty) {
        this.board = new Board(true);
    }

    /**
     * Select the piece on tile or 
     * move chosen piece to the tile.
     * @param x
     * @param y
     * @return SELECT if select; MOVE if move.
     */
    public int selectOrMove(int x, int y) {
        Piece target = board.getPiece(x, y);
        if (target != null && target.isWhite() == isWhiteTurn()
        ) {
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
     * @param x
     * @param y
     */ 
    public void selectPiece(int x, int y) {
        chosen = board.getPiece(x, y);
    }

    /**
     * Move the chosen piece to the tile of given coordinates if is valid.
     * @param x
     * @param y
     */
    public void move(int toX, int toY) {
        if (chosen != null && isValidMove(toX, toY)) {
            if (chosen.getTurnFirstMoved() == 0) {
                chosen.setMoved(true);
                chosen.setTurnFirstMoved(board.getTurn());
            }

            chosen.move(toX, toY, board);
            board.incrementTurn();
            changeSide();
        }
    }

    /**
     * Check if the chosen piece can move to the tile. 
     * @param toX
     * @param toY
     * @return
     */
    private boolean isValidMove(int toX, int toY) {
        Move move = new Move(chosen.getX(), chosen.getY(), toX, toY, chosen);
        if (chosen.getValidMoves().contains(move)) {
            return true;
        }
        return false;
    }

    public void generateAllWhiteMoves() {
        for (Piece piece : board.getwPieces()) {
            piece.allLegalMoves(board);
        }
    }

    public void generateAllBlackMoves() {
        for (Piece piece : board.getbPieces()) {
            piece.allLegalMoves(board);
        }
    }

    /**
     * Check if checkmate and generate available moves of pieces.
     */
    public void changeSide() {   
        List<Piece> pieces;
        King king;
        if (isWhiteTurn()) {
            pieces = board.getwPieces();
            king = board.getWKing();

            generateAllWhiteMoves();
            for (Piece piece : pieces) {
                piece.getValidMoves().clear();
            }
        } else {
            pieces = board.getbPieces();
            king = board.getBKing();

            generateAllBlackMoves();
            for (Piece piece : pieces) {
                piece.getValidMoves().clear();
            }
        }

        for (Piece piece : pieces) {
            for (Move move : piece.getLegalMoves()) {
                piece.move(move.getToX(), move.getToY(), board);
                board.incrementTurn();

                // valid if the king is not checked after the move
                if (!board.isChecked(king)) {
                    piece.getValidMoves().add(move);
                }
                board.takeBackMove();
            }
        }

        checkmate();
    }

    /**
     * Promote pawn to
     * 0 Queen
     * 1 Rook
     * 2 Knight
     * 3 Bishop
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

    public void checkmate() {
        board.setKingChecked();
        checkmate = Checkmate.checkmate(board);
    }

    /**
     * Recover the board to pevious step.
     */
    public void takeBackMove() {
        board.takeBackMove();
    }

    public void draw(Graphics g, JPanel panel) {
        board.draw(g, panel, chosen);
    }

    public boolean isWhiteTurn() {
        return board.isWhiteTurn();
    }

    public void deselectChosen() {
        this.chosen = null;
    }

    // getters and setters

    /**
     * @param checkmate the checkmate to set
     */
    public void setCheckmate(int checkmate) {
        this.checkmate = checkmate;
    }

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
