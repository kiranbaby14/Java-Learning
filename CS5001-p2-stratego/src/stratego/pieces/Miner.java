package stratego.pieces;

import stratego.CombatResult;
import stratego.Player;
import stratego.Square;

/**
 * Miner
 * Package to initialise the characteristics of the piece Miner.
 * It inherits from the StepMover class.
 *
 * @author: Student ID: 220015821
 */
public class Miner extends StepMover {

    /**
     * Constructor of the Miner class.
     *
     * @param owner the player who owns the piece.
     * @param square the square in which the piece is located.
     */
    public Miner(Player owner, Square square) {
        super(owner, square, 3);

    }

    /**
     * Method to get the results when Miner attacks other pieces.
     *
     * @param targetPiece the piece which miner attacks.
     * @return the status of the attack.
     */
    @Override
    public CombatResult resultWhenAttacking(Piece targetPiece) {

        if (this.getRank() > targetPiece.getRank()) {
            return CombatResult.WIN;
        } else if (this.getRank() < targetPiece.getRank()) {
            return CombatResult.LOSE;
        }
        return null;
    }
}
