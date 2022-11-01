package stratego.pieces;

import stratego.CombatResult;
import stratego.Player;
import stratego.Square;

/**
 * Spy
 * Package to initialise the characteristics for the piece Spy.
 * It inherits from StepMover class
 *
 * @author: Student ID: 220015821
 */
public class Spy extends StepMover {

    /**
     * Constructor of the class Spy.
     *
     * @param owner the player who owns the piece
     * @param square the square in which that piece is going to be placed
     */
    public Spy(Player owner, Square square) {
        super(owner, square, 1);
    }

    /**
     * Method to get the results when the spy piece attacks other pieces.
     *
     * @param targetPiece the piece that gets attacked
     * @return the result whether the attack was successful or not.
     */
    @Override
    public CombatResult resultWhenAttacking(Piece targetPiece) {

        if (targetPiece.getRank() == 10) {
            return CombatResult.WIN;
        } else { //        add for a flag case here
            return CombatResult.LOSE;
        }
    }
}
