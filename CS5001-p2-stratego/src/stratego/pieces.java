package pieces;

import java.util.ArrayList;

public abstract class Piece {
    public Piece(Player owner, Square square, int rank) {
        this.owner = owner;
        this.square = square;
        this.rank = rank;
    }

    public abstract ArrayList<Integer> getLegalMoves() {

    }

    public abstract ArrayList<Integer> getLegalAttacks() {

    }

    public void move(Square toSquare) {

    }

    public void attack(Square targetSquare) {

    }

    public CombatResult resultWhenAttacking(Piece targetPiece) {

    }

    public void beCaptured() {

    }

    public Square getSquare() {

    }

    public Player getOwner() {

    }

    public int getRank() {

    }
}

public class StepMover implements Piece {
    public StepMover(Player owner, Square square, int rank) {
        this.owner = owner;
        this.square = square;
        this.rank =rank;
    }
}

public class Scout implements Piece {
    public Scout(Player owner, Square square){
        this.owner = owner;
        this.square = square;
    }
}

public abstract class ImmobilePiece implements Piece{
    public ImmobilePiece(Player owner, Square square, int rank) {
        this.owner = owner;
        this.square = square;
        this.rank =rank;
    }
}

public class Spy implements StepMover{
    public Spy(Player owner, Square square){
        this.owner = owner;
        this.square = square;
    }

    @Override
    public CombatResult resultWhenAttacking(Piece targetPiece) {

    }
}

public class Miner implements StepMover {
    public Miner(Player owner, Square square) {
        this.owner = owner;
        this.square = square;
    }

    @Override
    public CombatResult resultWhenAttacking(Piece targetPiece) {

    }
}

public class Flag implements ImmobilePiece {
    public Flag(Player owner, Square square) {
        this.owner = owner;
        this.square = square;
    }

    @Override
    public void beCaptured() {

    }
}

public class Bomb implements ImmobilePiece {
    public Bomb(Player owner, Square square) {
        this.owner = owner;
        this.square = square;
        this.rank =rank;
    }
}