package stratego.pieces;

import java.util.ArrayList;

public class Miner extends StepMover {
    public Miner(Player owner, Square square) {
        this.owner = owner;
        this.square = square;
    }

    @Override
    public CombatResult resultWhenAttacking(Piece targetPiece) {

    }
}