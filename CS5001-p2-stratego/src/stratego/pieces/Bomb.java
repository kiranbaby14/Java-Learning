package stratego.pieces;

public class Bomb extends ImmobilePiece {
    public Bomb(Player owner, Square square) {
        this.owner = owner;
        this.square = square;
        this.rank =rank;
    }
}