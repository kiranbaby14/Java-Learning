package stratego.pieces;

import stratego.Player;
import stratego.Square;

import java.util.ArrayList;

public class StepMover extends Piece {
    public StepMover(Player owner, Square square) {
        super(owner, square);

    }
    public StepMover(Player owner, Square square, int rank) {
        super(owner, square, rank);

    }

    @Override
    public ArrayList<Integer> getLegalMoves() {
        return null;
    }

    @Override
    public ArrayList<Integer> getLegalAttacks() {
        return null;
    }

}