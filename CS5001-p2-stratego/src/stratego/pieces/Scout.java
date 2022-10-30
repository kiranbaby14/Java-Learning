package stratego.pieces;

import stratego.Player;
import stratego.Square;
import java.util.ArrayList;

public class Scout extends Piece {
    public final Player owner;
    public final Square square;

    public Scout(Player owner, Square square){
        super();
        this.owner = owner;
        this.square = square;
    }
}