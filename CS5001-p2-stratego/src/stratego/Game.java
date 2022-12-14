package stratego;

import java.util.Objects;

/**
 * Game
 * Package to initialise a game with two players.
 *
 * @author: Student ID: 220015821
 */
public class Game {
    private static final int HEIGHT = 10;
    private static final int WIDTH = 10;
    private static final int[] WATER_ROWS = {4, 5};
    private static final int[] WATER_COLS = {2, 3, 6, 7};

    private final Player p0;
    private final Player p1;

    /**
     * 2D matrix to represent the board.
     */
    private static Square[][] board = new Square[HEIGHT][WIDTH]; // 2D matrix initialisation of the board

    /**
     * Constructor for the class Game.
     * Initialises a 2D matrix of type Square when constructor is called.
     *
     * @param p0 player1
     * @param p1 player2
     */
    public Game(Player p0, Player p1) {
        this.p0 = p0;
        this.p1 = p1;
        Game game = this;

        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                for (int water_rows : WATER_ROWS) {
                    for (int water_cols : WATER_COLS) {
                        if ((water_rows != row) && (water_cols != col)) { //Intialise all the squares of the board
                            board[row][col] = new Square(game, row, col, false);
                        } else { // Initialise all the water squares
                            board[water_rows][water_cols] = new Square(game, row, col, true);
                        }
                    }
                }
            }
        }
    }

    /**
     * Getter method to get the player according to the player number.
     *
     * @param playerNumber player number of the particular object
     * @return player object
     */
    public Player getPlayer(int playerNumber) {
        if ((this.p0.getPlayerNumber() != playerNumber)
                && (this.p1.getPlayerNumber() != playerNumber)) {
            throw new IllegalArgumentException("No such player exists!!");
        } else if (this.p0.getPlayerNumber() == playerNumber) {
            return this.p0;
        }

        return this.p1;
    }

    /**
     * Getter method to get the winner among players.
     *
     * @return player who've won the game
     */
    public Player getWinner() {

        if (Objects.equals(this.p0.getPlayerGameStatus(), CombatResult.LOSE)
                || Objects.equals(this.p1.getPlayerGameStatus(), CombatResult.WIN)) {
            return this.p1;
        } else if (Objects.equals(this.p1.getPlayerGameStatus(), CombatResult.LOSE)
                || Objects.equals(this.p0.getPlayerGameStatus(), CombatResult.WIN)) {
            return this.p0;
        }
        return null;
    }

    /**
     * Getter method to get the square for the board.
     *
     * @param row the row number of the square
     * @param col the column number of the square
     * @return Square pieces of the board
     */
    public Square getSquare(int row, int col) {

        if (row > HEIGHT || col > WIDTH) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        } else {
            return board[row][col];
        }
    }

    /**
     * Setter method to set the square for the board.
     *
     * @param row            the row number of the square
     * @param col            the column number of the square
     * @param setSquareParam the parameter value to be set
     */
    public void setSquare(int row, int col, Square setSquareParam) {

        if (row > HEIGHT || col > WIDTH) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        } else {
            board[row][col] = setSquareParam;
        }
    }

    /**
     * Getter method to get the height of the board.
     *
     * @return height of the board
     */
    public static int getHeight() {
        return HEIGHT;
    }

    /**
     * Getter method to get the width of the board.
     *
     * @return width of the board
     */
    public static int getWidth() {
        return WIDTH;
    }
}
