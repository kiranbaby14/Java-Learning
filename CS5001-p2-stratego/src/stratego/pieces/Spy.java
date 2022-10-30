package stratego.pieces;

import stratego.CombatResult;
import stratego.Player;
import stratego.Square;

import java.util.ArrayList;

public class Spy extends StepMover{
    public final Player owner;
    public final Square square;

    public Spy(Player owner, Square square){
        super(owner, square);
        this.owner = owner;
        this.square = square;
    }

    @Override
    public CombatResult resultWhenAttacking(Piece targetPiece) {

        return null;
    }
}