package stratego.pieces;

import stratego.Player;
import stratego.Square;

import java.util.List;

import static java.util.Collections.EMPTY_LIST;

public class Bomb extends ImmobilePiece {

    public Bomb(Player owner, Square square) {
        super(owner, square);
    }

    @Override
    public List<Square> getLegalMoves() {
        return (List<Square>) EMPTY_LIST;
    }

    @Override
    public List<Square> getLegalAttacks() {
        return (List<Square>) EMPTY_LIST;
    }
}
