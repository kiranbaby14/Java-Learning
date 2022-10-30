package stratego.pieces;

import stratego.Player;
import stratego.Square;

public class StepMover extends Piece {
    public final Player owner;
    public final Square square;
    public final int rank;

    public StepMover(Player owner, Square square, int rank) {
        super();
        this.owner = owner;
        this.square = square;
        this.rank =rank;
    }
}