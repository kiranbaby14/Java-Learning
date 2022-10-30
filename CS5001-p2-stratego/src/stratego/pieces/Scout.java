package stratego.pieces;

import stratego.Player;
import stratego.Square;
import java.util.ArrayList;

public class Scout extends Piece {

    public Scout(Player owner, Square square){
        super(owner, square);


    }

    @Override
    public ArrayList<Integer> getLegalMoves() {
        return null;
    }

    @Override
    public ArrayList<Integer> getLegalAttacks() {
        return null;
    }
}