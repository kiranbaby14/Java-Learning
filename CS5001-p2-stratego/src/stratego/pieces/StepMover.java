package stratego.pieces;

import java.util.ArrayList;

public class StepMover extends Piece {
    public StepMover(Player owner, Square square, int rank) {
        this.owner = owner;
        this.square = square;
        this.rank =rank;
    }
}