package stratego;

import stratego.pieces.Piece;

/**
 * Square
 * Package to initialise the squares of the board in which the game is played.
 *
 * @author: Student ID: 220015821
 */
public class Square {
    private final Game game;
    private final int col;
    private final int row;
    private final boolean isWater;
    private Piece piece;

    /**
     * Constructor for the square class.
     *
     * @param game    object of the GAme class
     * @param row     specific row where the square should be.
     * @param col     specific column where the square should be.
     * @param isWater boolean to check whether a particular square is occupied by water or not
     */
    public Square(Game game, int row, int col, boolean isWater) {
        this.game = game;
        this.row = row;
        this.col = col;
        this.isWater = isWater;
        this.piece = null; // a connection from Square to Piece
    }

    /**
     * Method to set a piece onto the square.
     *
     * @param piece object of the Piece class
     */
    public void placePiece(Piece piece) {
        if (this.piece == null) { // checks if the square is null
            this.piece = piece;
            if (this.game != null) {
                Game.board[this.row][this.col] = this;
            }
        } else { // throws exception if square is already occupied
            throw new IllegalArgumentException("Square already occupied!!");
        }
    }

    /**
     * Method to remove a piece from the square.
     */
    public void removePiece() {
        this.piece = null;
    }

    /**
     * Getter method to get the Game.
     *
     * @return the current game
     */
    public Game getGame() {
        return this.game;
    }

    /**
     * Getter method to get the piece associated with the square.
     *
     * @return the piece that's on the square.
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * Getter method to get the row of the square.
     *
     * @return the row of the square
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Getter method to get the column of the square.
     *
     * @return the column of the square
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Method to check if a already occupied or not.
     *
     * @return boolean
     */
    public boolean canBeEntered() {
        // checks if the square is water filled or if it is already occupied by a piece
        if ((this.isWater) || (this.piece != null)) {
            return false;
        }
        return true;
    }

    /**
     * Method to check if the square is filled with water or not.
     *
     * @return boolean
     */
    public boolean isSquareWater() {
        return this.isWater;
    }

    /**
     * Method to set the connection between a square and its piece.
     */
    public void setPiece(Piece pieceParam) {
        this.piece = pieceParam;
    }
}
