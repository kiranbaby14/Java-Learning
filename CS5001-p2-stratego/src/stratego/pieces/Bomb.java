package stratego.pieces;

import java.util.ArrayList;

public class Bomb extends ImmobilePiece {
    public Bomb(Player owner, Square square) {
        this.owner = owner;
        this.square = square;
        this.rank =rank;
    }
}