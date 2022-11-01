package stratego.pieces;

import stratego.Player;
import stratego.Square;
import stratego.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Scout
 * Package to initialise the characteristics for the piece Scout.
 * It inherits from Piece class
 *
 * @author: Student ID: 220015821
 */
public class Scout extends Piece {

    /**
     * Constructor for the class Scout.
     *
     * @param owner the player who owns the piece
     * @param square the square where the piece is placed
     */
    public Scout(Player owner, Square square) {
        super(owner, square, 2);
    }

    /**
     * Getter method to get the legal moves of the pieces belonging to Scout class.
     *
     * @return  a list of all the legal moves possible for a scout piece
     */
    @Override
    public List<Square> getLegalMoves() {
        List<Square> legalMovesList = new ArrayList<Square>();
        Square currentSquare = this.getSquare();
        int currentScoutRow = currentSquare.getRow();
        int currentScoutCol = currentSquare.getCol();

        for (int row = currentScoutRow + 1; row < Game.getHeight(); row++) {
            // checks all the possible moves downwards
            if (Game.board[row][currentScoutCol].canBeEntered()) {
                legalMovesList.add(Game.board[row][currentScoutCol]);
            } else {
                break;
            }
        }

        for (int row = currentScoutRow - 1; row >= 0; row--) {
            // checks all the possible moves upwards
            if (Game.board[row][currentScoutCol].canBeEntered()) {
                legalMovesList.add(Game.board[row][currentScoutCol]);
            } else {
                break;
            }
        }

        for (int col = currentScoutCol + 1; col < Game.getWidth(); col++) {
            // checks for all the moves to the right
            if (Game.board[currentScoutRow][col].canBeEntered()) {
                legalMovesList.add(Game.board[currentScoutRow][col]);
            } else {
                break;
            }
        }

        for (int col = currentScoutCol - 1; col >= 0; col--) {
            // checks all the possible moves to the left
            if (Game.board[currentScoutRow][col].canBeEntered()) {
                legalMovesList.add(Game.board[currentScoutRow][col]);
            } else {
                break;
            }
        }

        return legalMovesList;
    }

    /**
     * Getter method to get all the legal attacks of the pieces belonging to Scout class.
     *
     * @return  a list of all the legal attacks possible for a scout piece
     */
    @Override
    public List<Square> getLegalAttacks() {
        List<Square> legalAttacksList = new ArrayList<Square>();
        Square currentSquare = this.getSquare();
        int currentScoutRow = currentSquare.getRow();
        int currentScoutCol = currentSquare.getCol();

        try { // checks for the possible attack downwards
            if (Game.board[currentScoutRow + 1][currentScoutCol].getPiece() != null
                    && !Game.board[currentScoutRow + 1][currentScoutCol].isSquareWater()) {
                legalAttacksList.add(Game.board[currentScoutRow + 1][currentScoutCol]);
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Index out of bounds");
        }

        try { // checks for the possible attack upwards
            if (Game.board[currentScoutRow - 1][currentScoutCol].getPiece() != null
                    && !Game.board[currentScoutRow - 1][currentScoutCol].isSquareWater()) {
                legalAttacksList.add(Game.board[currentScoutRow - 1][currentScoutCol]);
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Index out of bounds");
        }

        try { // checks for the possible attack to the right
            if (Game.board[currentScoutRow][currentScoutCol + 1].getPiece() != null
                    && !Game.board[currentScoutRow][currentScoutCol + 1].isSquareWater()) {
                legalAttacksList.add(this.getSquare().getGame().getSquare(currentScoutRow, currentScoutCol + 1));
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Index out of bounds");
        }

        try { // checks for the possible attack to the right
            if (Game.board[currentScoutRow][currentScoutCol - 1].getPiece() != null
                    && !Game.board[currentScoutRow][currentScoutCol - 1].isSquareWater()) {
                legalAttacksList.add(this.getSquare().getGame().getSquare(currentScoutRow, currentScoutCol - 1));
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Index out of bounds");
        }

        System.out.println(legalAttacksList);
        return legalAttacksList;
    }
}
