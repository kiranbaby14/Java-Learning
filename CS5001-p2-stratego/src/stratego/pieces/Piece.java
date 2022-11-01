package stratego.pieces;

import stratego.CombatResult;
import stratego.Player;
import stratego.Square;
import stratego.Game;

import java.util.List;

public abstract class Piece {
    private final int rank;
    private Square square;
    private final Player owner;


    public Piece(Player owner, Square square, int rank) {
        this.owner = owner;
        this.square = square;
        this.rank = rank;
        this.square.piece = this;

        if (this.square.getGame() != null) {
            Game.board[this.square.getRow()][this.square.getCol()] = this.square;
        }

    }

    public abstract List<Square> getLegalMoves();

    public abstract List<Square> getLegalAttacks();

    public void move(Square toSquare) {
        this.square.piece = null;
        this.square = toSquare;
        toSquare.piece = this;
    }

    public void attack(Square targetSquare) {
        if (targetSquare.piece instanceof Bomb) {
            if (this.rank == 3) {
                this.square.piece = null;
                targetSquare.piece.square = null;
                targetSquare.piece = null;
                this.square = targetSquare;
                this.square.piece = this;
            } else {
                targetSquare.piece.square = null;
                targetSquare.piece = null;
                this.square = null;
            }

        } else if (targetSquare.piece instanceof Flag) {
            targetSquare.piece.owner.loseGame();
            this.square.piece = null;
            targetSquare.piece.square = null;
            targetSquare.piece = null;
            this.square = targetSquare;
            this.square.piece = this;
        } else if (this.rank > targetSquare.piece.getRank()) {
            this.square.piece = null;
            targetSquare.piece.square = null;
            targetSquare.piece = null;
            this.square = targetSquare;
            this.square.piece = this;
        } else if (this.rank < targetSquare.piece.getRank()) {
            targetSquare = this.square;
            targetSquare.piece = null;
            this.square.piece = null;
            this.square = null;
        } else if (this.rank == targetSquare.piece.getRank()) {
            targetSquare.piece = null;
            this.square.piece = null;
            this.square = null;
        }

    }

    public CombatResult resultWhenAttacking(Piece targetPiece) {
        if (targetPiece instanceof Bomb) {
            if (this.rank == 3) {
                return CombatResult.WIN;
            } else {
                return CombatResult.DRAW;
            }
        } else if (this.rank > targetPiece.rank) {
            return CombatResult.WIN;
        } else if (this.rank == targetPiece.rank) {
            return CombatResult.DRAW;
        } else if (this.rank < targetPiece.rank) {
            return CombatResult.LOSE;
        }
        return null;
    }

    public void beCaptured() {
        this.square.piece = null;
        this.square = null;
    }

    public Square getSquare() {
        return this.square;
    }

    public Player getOwner() {
        return this.owner;
    }

    public int getRank() {
        return this.rank;
    }
}
