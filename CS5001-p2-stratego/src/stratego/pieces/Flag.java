package stratego.pieces;

import java.util.ArrayList;

public class Flag extends ImmobilePiece {
    public Flag(Player owner, Square square) {
        this.owner = owner;
        this.square = square;
    }

    @Override
    public void beCaptured() {

    }
}