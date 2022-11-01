package stratego.pieces;

import stratego.CombatResult;
import stratego.Player;
import stratego.Square;
import stratego.Game;

import java.util.List;

/**
 * Piece
 * Package to initialise the pieces of the game.
 * It is an Abstract class
 *
 * @author: Student ID: 220015821
 */
public abstract class Piece {
    private final int rank;
    private Square square;
    private final Player owner;


    /**
     * Constructor for the class Piece.
     *
     * @param owner the player who owns the piece
     * @param square the square where the piece is placed
     * @param rank the rank of the particular piece
     */
    public Piece(Player owner, Square square, int rank) {
        this.owner = owner;
        this.square = square;
        this.rank = rank;
        this.square.setPiece(this);

        if (this.square.getGame() != null) { // places the piece to the square
            this.square.getGame().setSquare(this.square.getRow(), this.square.getCol(), this.square);
        }
    }

    /**
     * An abstract method.
     * Makes the child classes have all the legalmoves
     *
     * @return list of all the legal moves
     */
    public abstract List<Square> getLegalMoves();

    /**
     * An abstract method.
     * Makes the child classes have all the legalattacks.
     *
     * @return list of all the legal attacks
     */
    public abstract List<Square> getLegalAttacks();

    /**
     * Method to move the piece.
     *
     * @param toSquare the square to which the field should be moved
     */
    public void move(Square toSquare) {
        this.square.setPiece(null);
        this.square = toSquare;
        toSquare.setPiece(this);
    }

    /**
     * Method to attack another piece.
     *
     * @param targetSquare the square which should be attacked
     */
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

    /**
     * Method to get the results when a piece attacks the other.
     *
     * @param targetPiece the piece which is going to be attacked.
     * @return the status of the attack
     */
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

    /**
     * Method to remove a piece when it is defeated.
     */
    public void beCaptured() {
        this.square.setPiece(null);
        this.square = null;
    }

    /**
     * Getter method to get the square associated to a piece.
     *
     * @return Square which is assosciated to the piece
     */
    public Square getSquare() {
        return this.square;
    }

    /**
     * Getter method to get the owner of the piece.
     *
     * @return Player who owns the piece
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * Getter method to get the rank associated to a piece.
     *
     * @return integer number(rank)
     */
    public int getRank() {
        return this.rank;
    }
}
