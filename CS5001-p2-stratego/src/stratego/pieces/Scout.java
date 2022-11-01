package stratego.pieces;

import stratego.Player;
import stratego.Square;
import stratego.Game;

import java.util.ArrayList;
import java.util.List;

public class Scout extends Piece {

    public Scout(Player owner, Square square) {
        super(owner, square, 2);
    }

    @Override
    public List<Square> getLegalMoves() {
        List<Square> legalMovesList = new ArrayList<Square>();
        Square currentSquare = this.getSquare();
        int currentScoutRow = currentSquare.getRow();
        int currentScoutCol = currentSquare.getCol();

        for (int col = currentScoutCol + 1; col < Game.getWidth(); col++) {
            if (Game.board[currentScoutRow][col].canBeEntered()) {
                legalMovesList.add(Game.board[currentScoutRow][col]);
            } else {
                break;
            }
        }

        for (int col = currentScoutCol - 1; col >= 0; col--) {
            if (Game.board[currentScoutRow][col].canBeEntered()) {
                legalMovesList.add(Game.board[currentScoutRow][col]);
            } else {
                break;
            }
        }

        for (int row = currentScoutRow + 1; row < Game.getHeight(); row++) {
            if (Game.board[row][currentScoutCol].canBeEntered()) {
                legalMovesList.add(Game.board[row][currentScoutCol]);
            } else {
                break;
            }
        }

        for (int row = currentScoutRow - 1; row >= 0; row--) {
            if (Game.board[row][currentScoutCol].canBeEntered()) {
                legalMovesList.add(Game.board[row][currentScoutCol]);
            } else {
                break;
            }
        }

        return legalMovesList;
    }

    @Override
    public List<Square> getLegalAttacks() {
        List<Square> legalAttacksList = new ArrayList<Square>();
        Square currentSquare = this.getSquare();
        int currentScoutRow = currentSquare.getRow();
        int currentScoutCol = currentSquare.getCol();

        try {
            if (Game.board[currentScoutRow + 1][currentScoutCol].getPiece() != null
                    && !Game.board[currentScoutRow + 1][currentScoutCol].isSquareWater()) {
                legalAttacksList.add(Game.board[currentScoutRow + 1][currentScoutCol]);
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Index out of bounds");
        }

        try {
            if (Game.board[currentScoutRow - 1][currentScoutCol].getPiece() != null
                    && !Game.board[currentScoutRow - 1][currentScoutCol].isSquareWater()) {
                legalAttacksList.add(Game.board[currentScoutRow - 1][currentScoutCol]);
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Index out of bounds");
        }

        try {
            if (Game.board[currentScoutRow][currentScoutCol + 1].getPiece() != null
                    && !Game.board[currentScoutRow][currentScoutCol + 1].isSquareWater()) {
                legalAttacksList.add(this.getSquare().getGame().getSquare(currentScoutRow, currentScoutCol + 1));
            }
        } catch (Exception ArrayIndexOutOfBoundsException) {
            System.out.println("Index out of bounds");
        }

        try {
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
