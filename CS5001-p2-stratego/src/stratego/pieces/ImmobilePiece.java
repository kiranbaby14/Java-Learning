package stratego.pieces;

import stratego.Player;
import stratego.Square;

/**
 * ImmobilePiece
 * Package to initialise the immobile pieces.
 * It inherits from the Piece class.
 * It is an abstract class which is inherited by other pieces that don't move.
 *
 * @author: Student ID: 220015821
 */
public abstract class ImmobilePiece extends Piece {

    /**
     * Constructor of the Immobile class.
     *
     * @param owner the player who owns the piece.
     * @param square the square in which the piece is located.
     */
    public ImmobilePiece(Player owner, Square square) {
        super(owner, square, 0);
    }
}
