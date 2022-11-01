package stratego.pieces;

import stratego.Player;
import stratego.Square;

import java.util.List;

import static java.util.Collections.EMPTY_LIST;

/**
 * Bomb
 * Package to initialise the bomb piece.
 * It inherits from ImmobilePiece class.
 *
 * @author: Student ID: 220015821
 */
public class Bomb extends ImmobilePiece {

    /**
     * Constructor of the Bomb class
     *
     * @param owner the player who owns the piece.
     * @param square the square in which the piece is located.
     */
    public Bomb(Player owner, Square square) {
        super(owner, square);
    }

    /**
     * Method to get all the legal moves of the Bomb.
     *
     * @return list of all the legal moves.
     */
    @Override
    public List<Square> getLegalMoves() {
        return (List<Square>) EMPTY_LIST;
    }

    /**
     * Method to get all the legal attacks of the Bomb.
     *
     * @return list of all the legal attacks.
     */
    @Override
    public List<Square> getLegalAttacks() {
        return (List<Square>) EMPTY_LIST;
    }
}
