package stratego.pieces;

import stratego.CombatResult;
import stratego.Player;
import stratego.Square;

public class Spy extends StepMover {

    public Spy(Player owner, Square square) {
        super(owner, square, 1);
    }

    @Override
    public CombatResult resultWhenAttacking(Piece targetPiece) {

        if (targetPiece.getRank() == 10) {
            return CombatResult.WIN;
        } else { //        add for a flag case here
            return CombatResult.LOSE;
        }
    }
}
