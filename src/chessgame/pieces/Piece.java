package chessgame.pieces;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import java.awt.Graphics;

import chessgame.Board;
import chessgame.GamePanel;
import chessgame.Move;

/**
 * Class {@code} defines a chess piece.
 */
public abstract class Piece {
    protected int x;
    protected int y;
    protected boolean isWhite;
    protected boolean isMoved;
    protected List<Move> legalMoves = new ArrayList<>();
    protected List<Move> validMoves = new ArrayList<>();
    protected BufferedImage image;
    private int startX;
    private int startY;
    private int turnFirstMoved;

    public Piece(int x, int y, boolean isWhite) {
        this.x = x;
        this.y = y;
        this.isWhite = isWhite;
        this.isMoved = false;
        this.startX = x;
        this.startY = y;
        this.turnFirstMoved = 0;
    }

    /**
     * Check if this piece can move to the tile. 
     * @param toX target column
     * @param toY target row
     * @param board chess board
     * @return {@code true} if given tile is an available move for this piece.
     */
    public abstract boolean canMove(int toX, int toY, Board board);

    /**
     * Move the piece to the tile.
     * Assume that the position given is valid.
     * @param toX column
     * @param toY row
     * @param board chess board
     */
    public void move(int toX, int toY, Board board) {
        int fromX = this.x;
        int fromY = this.y;
        boolean isCastling = false;
        boolean isPromotion = false;
        this.x = toX;
        this.y = toY;

        // move to empty tile
        if (board.getPiece(toX, toY) == null) {
            // pawn en passant move
            if (this instanceof Pawn) {
                enPassant(fromX, fromY, toX, toY, board);
            }

            // king castling
            if (this instanceof King) {
                isCastling = castling(fromX, fromY, toX, toY, board);
            }
        } else if (board.getPiece(toX, toY).isWhite != this.isWhite) {
            // capture enemy piece
            board.getDeadPieces().put(board.getTurn(), board.getPiece(toX, toY));
            board.removePieceFromBoard(toX, toY);
        }

        if (this instanceof Pawn && ((Pawn) this).isPromotable()) {
            isPromotion = true;
        }

        // place piece to new tile
        board.removePiece(fromX, fromY);
        board.setTile(toX, toY, this);

        // store the movement
        Move move = new Move(fromX, fromY, toX, toY, this, isCastling, isPromotion);
        board.getMoves().push(move);
    }

    /**
     * Store all possible moves can be made by this piece.
     * @param board
     */
    public void allLegalMoves(Board board) {
        legalMoves.clear();
        for (int i = 0; i < Board.ROWS; i++) {
            for (int j = 0; j < Board.COLUMNS; j++) {
                if (canMove(i, j, board)) {
                    legalMoves.add(new Move(x, y, i, j, this));
                }
            }
        }
    }

    /**
     * Pawn performs En Passant move if valid.
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @param board
     */
    private void enPassant(int fromX, int fromY, int toX, int toY, Board board) {
        // check if target tile is empty
        if (board.getPiece(toX, toY) != null) {
            // target tile not empty, do nothing
            return;
        }

        // capture enemy pawn if is diagnally forward
        if (isWhite && toY == fromY - 1 && (toX - fromX == 1 || fromX - toX == 1)) {
            board.getDeadPieces().put(board.getTurn(), board.getPiece(toX, fromY));
            board.removePieceFromBoard(toX, fromY);
        } else if (!isWhite && toY == fromY + 1 && (toX - fromX == 1 || fromX - toX == 1)) {
            board.getDeadPieces().put(board.getTurn(), board.getPiece(toX, fromY));
            board.removePieceFromBoard(toX, fromY);
        }
    }

    /**
     * Castling consists of moving the king two squares towards a rook,
     * then placing the rook on the other side of the king, adjacent to it.
     * This method places the rook next to king. 
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @param board
     */
    private boolean castling(int fromX, int fromY, int toX, int toY, Board board) {
        if (toX == fromX + 2) {
            // Castle kingside
            int xRook = 7;
            Piece rook = board.getPiece(xRook, fromY);

            // move rook
            rook.setX(toX - 1);
            board.removePiece(xRook, fromY);
            board.setTile(rook.getX(), rook.getY(), rook);

            return true;
        } else if (toX == fromX - 2) {
            // Castle queenside
            int xRook = 0;
            Piece rook = board.getPiece(xRook, fromY);

            // move rook
            rook.setX(toX + 1);
            board.removePiece(xRook, fromY);
            board.setTile(rook.getX(), rook.getY(), rook);

            return true;
        }
        return false;
    }

    /**
     * Draw image of the piece to panel. 
     * @param g
     * @param frame
     */
    public void draw(Graphics g, JPanel panel) {
        GamePanel gamePanel = (GamePanel) panel;
        g.drawImage(image, x * gamePanel.getTileWidth(), y * gamePanel.getTileWidth(), 
            gamePanel.getTileWidth(), gamePanel.getTileWidth(), panel);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Piece other = (Piece) obj;
        if (this.startX == other.startX && this.startY == other.startY) {
            return true;
        }

        return false;
    }

    // getters and setters

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the isWhite
     */
    public boolean isWhite() {
        return isWhite;
    }

    /**
     * @return the isMoved
     */
    public boolean isMoved() {
        return isMoved;
    }

    /**
     * @param isMoved the isMoved to set
     */
    public void setMoved(boolean isMoved) {
        this.isMoved = isMoved;
    }

    /**
     * @return the legalMoves
     */
    public List<Move> getLegalMoves() {
        return legalMoves;
    }

    /**
     * @return the validMoves
     */
    public List<Move> getValidMoves() {
        return validMoves;
    }

    public int getStartX() {
        return this.startX;
    }

    public int getStartY() {
        return this.startY;
    }

    /**
     * @return the turnFirstMoved
     */
    public int getTurnFirstMoved() {
        return turnFirstMoved;
    }

    /**
     * @param turnFirstMoved the turnFirstMoved to set
     */
    public void setTurnFirstMoved(int turnFirstMoved) {
        this.turnFirstMoved = turnFirstMoved;
    }
}