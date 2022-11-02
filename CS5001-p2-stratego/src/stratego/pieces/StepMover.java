package stratego.pieces;

import stratego.Player;
import stratego.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * StepMover
 * It inherits from the class Piece.
 * Package to get and set the properties of pieces that can only move one step at a time.
 *
 * @author: Student ID: 220015821
 */
public class StepMover extends Piece {

    /**
     * Constructor of the class StepMover.
     *
     * @param owner  object of the Player class
     * @param square object of the Square class
     * @param rank   particular rank of the pieces
     */
    public StepMover(Player owner, Square square, int rank) {
        super(owner, square, rank);
    }

    /**
     * Getter method to get the legal moves of the pieces belonging to StepMover class.
     *
     * @return a list of all the legal moves possible for a piece
     */
    @Override
    public List<Square> getLegalMoves() {
        List<Square> legalMovesList = new ArrayList<Square>();
        Square currentSquare = this.getSquare();
        int currentPieceRow = currentSquare.getRow();
        int currentPieceCol = currentSquare.getCol();

        try { // checks one step downwards
            Square bottomSquare = currentSquare.getGame().getSquare(currentPieceRow + 1, currentPieceCol);
            if (bottomSquare.canBeEntered()) {
                legalMovesList.add(bottomSquare);
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Index out of bounds");
        }

        try { // checks one step upwards
            Square topSquare = currentSquare.getGame().getSquare(currentPieceRow - 1, currentPieceCol);
            if (topSquare.canBeEntered()) {
                legalMovesList.add(topSquare);
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Index out of bounds");
        }

        try { // checks one step to the right
            Square rightSquare = currentSquare.getGame().getSquare(currentPieceRow, currentPieceCol + 1);
            if (rightSquare.canBeEntered()) {
                legalMovesList.add(rightSquare);
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Index out of bounds");
        }

        try { // checks one step to the left
            Square leftSquare = currentSquare.getGame().getSquare(currentPieceRow, currentPieceCol - 1);
            if (leftSquare.canBeEntered()) {
                legalMovesList.add(leftSquare);
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Index out of bounds");
        }

        return legalMovesList;
    }

    /**
     * Getter method to get all the legal attacks of the pieces belonging to StepMover class.
     *
     * @return a list of all the legal attacks possible for a piece
     */
    @Override
    public List<Square> getLegalAttacks() {
        List<Square> legalAttacksList = new ArrayList<Square>();
        Square currentSquare = this.getSquare();
        int currentPieceRow = currentSquare.getRow();
        int currentPieceCol = currentSquare.getCol();

        try { // checks one step downwards
            Square bottomSquare = currentSquare.getGame().getSquare(currentPieceRow + 1, currentPieceCol);
            if (bottomSquare.getPiece() != null
                    && !bottomSquare.isSquareWaterOccupied()) {
                legalAttacksList.add(bottomSquare);
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Index out of bounds");
        }

        try { // checks one step upwards
            Square topSquare = currentSquare.getGame().getSquare(currentPieceRow - 1, currentPieceCol);
            if (topSquare.getPiece() != null
                    && !topSquare.isSquareWaterOccupied()) {
                legalAttacksList.add(topSquare);
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Index out of bounds");
        }

        try { // checks one step to the right
            Square rightSquare = currentSquare.getGame().getSquare(currentPieceRow, currentPieceCol + 1);
            if (rightSquare.getPiece() != null
                    && !rightSquare.isSquareWaterOccupied()) {
                legalAttacksList.add(rightSquare);
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Index out of bounds");
        }

        try { // checks one step to the left
            Square leftSquare = currentSquare.getGame().getSquare(currentPieceRow, currentPieceCol - 1);
            if (leftSquare.getPiece() != null
                    && !leftSquare.isSquareWaterOccupied()) {
                legalAttacksList.add(leftSquare);
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Index out of bounds");
        }

        System.out.println(legalAttacksList);

        return legalAttacksList;
    }
}
