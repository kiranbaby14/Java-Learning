package stratego.pieces;

import stratego.Player;
import stratego.Square;

import java.util.ArrayList;

public class Flag extends ImmobilePiece {
    public final Player owner;
    public final Square square;

    public Flag(Player owner, Square square) {
        super(owner, square);
        this.owner = owner;
        this.square = square;
    }

    @Override
    public ArrayList<Integer> getLegalMoves() {
        return null;
    }

    @Override
    public ArrayList<Integer> getLegalAttacks() {
        return null;
    }

    @Override
    public void beCaptured() {

    }
}