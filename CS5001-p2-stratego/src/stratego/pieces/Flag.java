package stratego.pieces;

import stratego.Player;
import stratego.Square;

import java.util.List;

import static java.util.Collections.EMPTY_LIST;

/**
 * Flag
 * Package to initialise the flag into the board.
 * It inherits from the ImmobilePiece class.
 *
 * @author: Student ID: 220015821
 */
public class Flag extends ImmobilePiece {

    /**
     * Constructor of the Flag class
     *
     * @param owner the player who owns the piece.
     * @param square the square in which the piece is located.
     */
    public Flag(Player owner, Square square) {
        super(owner, square);
    }

    /**
     * Method to get all the legal moves of the flag.
     *
     * @return list of all the legal moves (here 0, since flag cannot move).
     */
    @Override
    public List<Square> getLegalMoves() {
        return (List<Square>) EMPTY_LIST;
    }

    /**
     * Method to get all the legal attacks of the flag.
     *
     * @return list of all the legal attacks (here 0, since flag cannot attack).
     */
    @Override
    public List<Square> getLegalAttacks() {
        return (List<Square>) EMPTY_LIST;
    }

    /**
     * Method invoked when flag is captured.
     */
    @Override
    public void beCaptured() {
        this.getSquare().setPiece(null);
    }
}
