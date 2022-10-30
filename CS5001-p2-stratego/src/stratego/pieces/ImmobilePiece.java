package stratego.pieces;

import stratego.Player;
import stratego.Square;

import java.util.ArrayList;

public abstract class ImmobilePiece extends Piece{
    public final Player owner;
    public final Square square;
    public int rank;

    public ImmobilePiece(Player owner, Square square) {
        super(owner, square);
        this.owner = owner;
        this.square = square;
    }

    public ImmobilePiece(Player owner, Square square, int rank) {
        super(owner, square, rank);
        this.owner = owner;
        this.square = square;
        this.rank = rank;
    }

}