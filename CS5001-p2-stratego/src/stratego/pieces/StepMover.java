package stratego.pieces;

import stratego.Game;
import stratego.Player;
import stratego.Square;

import java.util.ArrayList;
import java.util.List;

public class StepMover extends Piece {

    public StepMover(Player owner, Square square, int rank) {
        super(owner, square, rank);
    }

    @Override
    public List<Square> getLegalMoves() {
        List<Square> legalMovesList = new ArrayList<Square>();
        Square currentSquare = this.getSquare();
        int currentPieceRow = currentSquare.getRow();
        int currentPieceCol = currentSquare.getCol();

        try {
            if (Game.board[currentPieceRow + 1][currentPieceCol].canBeEntered())
                legalMovesList.add(Game.board[currentPieceRow + 1][currentPieceCol]);
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Index out of bounds");
        }

        try {
            if (Game.board[currentPieceRow - 1][currentPieceCol].canBeEntered())
                legalMovesList.add(Game.board[currentPieceRow - 1][currentPieceCol]);
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Index out of bounds");
        }

        try {
            if (Game.board[currentPieceRow][currentPieceCol + 1].canBeEntered())
                legalMovesList.add(Game.board[currentPieceRow][currentPieceCol + 1]);
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Index out of bounds");
        }

        try {
            if (Game.board[currentPieceRow][currentPieceCol - 1].canBeEntered())
                legalMovesList.add(Game.board[currentPieceRow][currentPieceCol - 1]);
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Index out of bounds");
        }

        return legalMovesList;
    }

    @Override
    public List<Square> getLegalAttacks() {
        List<Square> legalAttacksList = new ArrayList<Square>();
        Square currentSquare = this.getSquare();
        int currentPieceRow = currentSquare.getRow();
        int currentPieceCol = currentSquare.getCol();

        try {
            if (Game.board[currentPieceRow + 1][currentPieceCol].getPiece() != null &&
                    !Game.board[currentPieceRow + 1][currentPieceCol].isSquareWater())
                legalAttacksList.add(Game.board[currentPieceRow + 1][currentPieceCol]);
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Index out of bounds");
        }

        try {
            if (Game.board[currentPieceRow - 1][currentPieceCol].getPiece() != null &&
                    !Game.board[currentPieceRow - 1][currentPieceCol].isSquareWater())
                legalAttacksList.add(Game.board[currentPieceRow - 1][currentPieceCol]);
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Index out of bounds");
        }

        try {
            if (Game.board[currentPieceRow][currentPieceCol + 1].getPiece() != null &&
                    !Game.board[currentPieceRow][currentPieceCol + 1].isSquareWater())
                legalAttacksList.add(this.getSquare().getGame().getSquare(currentPieceRow, currentPieceCol + 1));
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Index out of bounds");
        }

        try {
            if (Game.board[currentPieceRow][currentPieceCol - 1].getPiece() != null &&
                    !Game.board[currentPieceRow][currentPieceCol - 1].isSquareWater())
                legalAttacksList.add(this.getSquare().getGame().getSquare(currentPieceRow, currentPieceCol - 1));
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Index out of bounds");
        }

        System.out.println(legalAttacksList);

        return legalAttacksList;
    }
}
