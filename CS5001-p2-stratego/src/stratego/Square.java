package stratego;

import stratego.pieces.Piece;

import java.util.ArrayList;

// class Square
public class Square {
    public final Game game;
    public final int col;
    public final int row;
    public final boolean isWater;

    public Square(Game game, int row, int col, boolean isWater) {
        this.game = game;
        this.row = row;
        this.col = col;
        this.isWater = isWater;
    }

    public void placePiece(Piece piece) {

    }

    public void removePiece() {

    }

    public Game getGame() {

        return null;
    }

    public Piece getPiece() {

        return null;
    }

    public int getRow() {

        return 0;
    }

    public int getCol() {

        return 0;
    }

    public boolean canBeEntered() {

        return false;
    }
}
