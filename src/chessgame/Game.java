package chessgame;

import chessgame.pieces.Bishop;
import chessgame.pieces.Knight;
import chessgame.pieces.Piece;
import chessgame.pieces.Queen;
import chessgame.pieces.Rook;

public class Game {
    private Board board;
    private Piece chosen;

    public Game() {
        this.board = new Board();
    }

    /**
     * Choose the piece on the board with given x and y coordinates.
     * @param x
     * @param y
     */ 
    public void selectPiece(int x, int y) {
        Piece piece = board.getPiece(x, y);
        if (piece.isWhite() == board.isWhiteTurn()) {
            chosen = board.getPiece(x, y);
        } else {
            chosen = null;
        }
    }

    /**
     * Move the chosen piece to the tile of given coordinates.
     * @param x
     * @param y
     */
    public void move(int toX, int toY) {
        if (chosen != null) {
            chosen.move(toX, toY, board);
            board.incrementTurn();
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
        if (chosen instanceof Piece) {
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

        chosen = null;
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
