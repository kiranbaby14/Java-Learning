package stratego.pieces;
import stratego.Player;
import stratego.Square;

public class Bomb extends ImmobilePiece {
    public final Player owner;
    public final Square square;

    public Bomb(Player owner, Square square) {
        super();
        this.owner = owner;
        this.square = square;
    }
}