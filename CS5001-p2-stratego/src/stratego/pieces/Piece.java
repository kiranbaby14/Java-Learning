package stratego.pieces;

import java.util.ArrayList;

public abstract class Piece {
    public Piece(Player owner, Square square, int rank) {
        this.owner = owner;
        this.square = square;
        this.rank = rank;
    }

    public abstract ArrayList<Integer> getLegalMoves() {

    }

    public abstract ArrayList<Integer> getLegalAttacks() {

    }

    public void move(Square toSquare) {

    }

    public void attack(Square targetSquare) {

    }

    public CombatResult resultWhenAttacking(Piece targetPiece) {

    }

    public void beCaptured() {

    }

    public Square getSquare() {

    }

    public Player getOwner() {

    }

    public int getRank() {

    }
}