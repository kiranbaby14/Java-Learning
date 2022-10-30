package stratego.pieces;

import stratego.CombatResult;
import stratego.Player;
import stratego.Square;
import java.util.ArrayList;

public abstract class Piece {
    public int rank;
    public final Square square;
    public final Player owner;

    public Piece(Player owner, Square square) {
        this.owner = owner;
        this.square = square;
    }
    public Piece(Player owner, Square square, int rank) {
        this.owner = owner;
        this.square = square;
        this.rank = rank;
    }

    public abstract ArrayList<Integer> getLegalMoves();

    public abstract ArrayList<Integer> getLegalAttacks();

    public void move(Square toSquare) {

    }

    public void attack(Square targetSquare) {

    }

    public CombatResult resultWhenAttacking(Piece targetPiece) {

        return null;
    }

    public void beCaptured() {

    }

    public Square getSquare() {

        return null;
    }

    public Player getOwner() {

        return null;
    }

    public int getRank() {

        return 0;
    }
}