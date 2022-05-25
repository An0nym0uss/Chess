package chessgame;

import java.awt.Graphics;
import javax.swing.JPanel;

import chessgame.pieces.Bishop;
import chessgame.pieces.Knight;
import chessgame.pieces.Piece;
import chessgame.pieces.Queen;
import chessgame.pieces.Rook;

public class Game {
    private Board board;
    private Piece chosen;
    private boolean isCheckmate;

    public static int SELECT = 0;
    public static int MOVE = 1;

    public Game() {
        this.board = new Board();

        generateWhiteMoves();
    }

    /**
     * Determine if the action should be {@code} selectPiece {@code} or
     * {@code} move {@code}.
     * @param x
     * @param y
     * @return {@code} Game.SELECT {@code} or {@code} Game.MOVE {@code}
     */
    public int getAction(int x, int y) {
        Piece target = board.getPiece(x, y);
        if (target != null && target.isWhite() == isWhiteTurn()
        ) {
            return SELECT;
        } else {
            return MOVE;
        }
    }

    /**
     * Select the piece on tile or 
     * move chosen piece to the tile.
     * @param x
     * @param y
     */
    public void selectOrMove(int x, int y) {
        Piece target = board.getPiece(x, y);
        if (target != null && target.isWhite() == isWhiteTurn()
        ) {
            selectPiece(x, y);
        } else {
            move(x, y);
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
        if (chosen.getLegalMoves().contains(move)) {
            return true;
        }
        return false;
    }

    /**
     * Check if checkmate and generate available moves of pieces.
     */
    public void changeSide() {
        isCheckmate = board.checkmate();
        if (isCheckmate) {
            if (!isWhiteTurn()) {
                generateWhiteMoves();
            } else {
                generateBlackMoves();
            }
        } else {    
            if (isWhiteTurn()) {
                generateWhiteMoves();
            } else {
                generateBlackMoves();
            }
        }
    }

    public void generateWhiteMoves() {
        for (Piece piece : board.getwPieces()) {
            piece.allLegalMoves(board);
        }
    }

    public void generateBlackMoves() {
        for (Piece piece : board.getbPieces()) {
            piece.allLegalMoves(board);
        }
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

        board.removePiece(x, y);

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
     * Recover the board to pevious step.
     */
    public void takeBackMove() {
        board.decrementTurn();

        Move prevMove = board.getMoves().pop();
        int fromX = prevMove.getFromX();
        int fromY = prevMove.getFromY();
        int toX = prevMove.getToX();
        int toY = prevMove.getToY();
        Piece piece = prevMove.getPiece();
        Piece deadPiece = board.getDeadPieces().get(board.getTurn());

        // take back last movement
        piece.setX(fromX);
        piece.setY(fromY);
        board.removePiece(toX, toY);
        board.setTile(fromX, fromY, piece);

        // recover captured piece
        if (deadPiece != null) {
            board.setTile(toX, toY, deadPiece);
        }

        if (prevMove.isCastling()) {
            // move rook back to its place
            if (toY - fromY == 2) {
                // Castle kingside
                int yRook = toY - 1;
                Piece rook = board.getPiece(toX, yRook);
    
                // move rook
                board.removePiece(toX, yRook);
                board.setTile(fromX, toY + 1, rook);
            } else if (fromY - toY == 2) {
                // Castle queenside
                int yRook = toY + 1;
                Piece rook = board.getPiece(toX, yRook);
    
                // move rook
                board.removePiece(toX, yRook);
                board.setTile(fromX, 0, rook);
            }
        } else if (prevMove.isPromotion()) {
            // do nothing
        }

        changeSide();
    }

    public void draw(Graphics g, JPanel panel) {
        board.draw(g, panel);
    }

    public boolean isWhiteTurn() {
        return board.isWhiteTurn();
    }

    public void deselectChosen() {
        this.chosen = null;
    }

    public boolean isCheckmate() {
        return this.isCheckmate;
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
