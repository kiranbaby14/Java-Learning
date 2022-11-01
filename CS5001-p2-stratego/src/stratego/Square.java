package stratego;

import stratego.pieces.Piece;

// class Square
public class Square {
    private final Game game;
    private final int col;
    private final int row;
    private final boolean isWater;
    private Piece piece;

    public Square(Game game, int row, int col, boolean isWater) {
        this.game = game;
        this.row = row;
        this.col = col;
        this.isWater = isWater;
        this.piece = null;
    }

    public void placePiece(Piece piece) {
        if (this.piece == null) {
            this.piece = piece;
            if (this.game != null) {
                Game.board[this.row][this.col] = this;
            }
        } else {
            throw new IllegalArgumentException("Square already occupied!!");
        }
    }

    public void removePiece() {
        this.piece = null;
    }

    public Game getGame() {
        return this.game;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public boolean canBeEntered() {
        // checks if the square is water filled or if it is already occupied by piece
        if ((this.isWater) || (this.piece != null)) {
            return false;
        }
        return true;
    }

    public boolean isSquareWater() {
        return this.isWater;
    }

    public void setPiece(Piece pieceParam) {
        this.piece = pieceParam;
    }
}
