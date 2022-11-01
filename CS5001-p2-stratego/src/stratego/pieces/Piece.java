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
        this.square.setPiece(this);

        if (this.square.getGame() != null) {
            Game.board[this.square.getRow()][this.square.getCol()] = this.square;
        }

    }

    public abstract List<Square> getLegalMoves();

    public abstract List<Square> getLegalAttacks();

    public void move(Square toSquare) {
        this.square.setPiece(null);
        this.square = toSquare;
        toSquare.setPiece(this);
    }

    public void attack(Square targetSquare) {
        if (targetSquare.getPiece() instanceof Bomb) {
            if (this.rank == 3) {
                this.square.setPiece(null);
                targetSquare.getPiece().square = null;
                targetSquare.setPiece(null);
                this.square = targetSquare;
                this.square.setPiece(this);
            } else {
                targetSquare.getPiece().square = null;
                targetSquare.setPiece(null);
                this.square = null;
            }

        } else if (targetSquare.getPiece() instanceof Flag) {
            targetSquare.getPiece().owner.loseGame();
            this.square.setPiece(null);
            targetSquare.getPiece().square = null;
            targetSquare.setPiece(null);
            this.square = targetSquare;
            this.square.setPiece(this);
        } else if (this.rank > targetSquare.getPiece().getRank()) {
            this.square.setPiece(null);
            targetSquare.getPiece().square = null;
            targetSquare.setPiece(null);
            this.square = targetSquare;
            this.square.setPiece(this);
        } else if (this.rank < targetSquare.getPiece().getRank()) {
            targetSquare = this.square;
            targetSquare.setPiece(null);
            this.square.setPiece(null);
            this.square = null;
        } else if (this.rank == targetSquare.getPiece().getRank()) {
            targetSquare.setPiece(null);
            this.square.setPiece(null);
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
        this.square.setPiece(null);
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
