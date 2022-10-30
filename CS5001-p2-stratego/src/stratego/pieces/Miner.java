package stratego.pieces;

import stratego.CombatResult;
import stratego.Player;
import stratego.Square;

public class Miner extends StepMover {

    public final Player owner;
    public final Square square;

    public Miner(Player owner, Square square) {
        super(owner, square);
        this.owner = owner;
        this.square = square;
    }

    @Override
    public CombatResult resultWhenAttacking(Piece targetPiece) {

        return null;
    }
}