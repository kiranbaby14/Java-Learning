package stratego.pieces;

import java.util.ArrayList;

public class Spy extends StepMover{
    public Spy(Player owner, Square square){
        this.owner = owner;
        this.square = square;
    }

    @Override
    public CombatResult resultWhenAttacking(Piece targetPiece) {

    }
}