package stratego.pieces;

import stratego.Player;
import stratego.Square;

import java.util.ArrayList;

public abstract class ImmobilePiece extends Piece {

    public ImmobilePiece(Player owner, Square square) {
        super(owner, square, 0);
    }
}
