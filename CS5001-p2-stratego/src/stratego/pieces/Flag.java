package stratego.pieces;

import stratego.Player;
import stratego.Square;

import java.util.ArrayList;

public class Flag extends ImmobilePiece {
    public final Player owner;
    public final Square square;

    public Flag(Player owner, Square square) {
        super();
        this.owner = owner;
        this.square = square;
    }

    @Override
    public void beCaptured() {

    }
}