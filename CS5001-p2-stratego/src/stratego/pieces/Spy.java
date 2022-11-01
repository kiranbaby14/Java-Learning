package stratego.pieces;

import stratego.CombatResult;
import stratego.Player;
import stratego.Square;

import java.util.ArrayList;

public class Spy extends StepMover {

    public Spy(Player owner, Square square) {
        super(owner, square, 1);
    }

    @Override
    public CombatResult resultWhenAttacking(Piece targetPiece) {

        if (targetPiece.getRank() == 10)
            return CombatResult.WIN;
//        add or a flag case here
        else
            return CombatResult.LOSE;
    }
}
