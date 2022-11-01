package stratego;

import java.util.*;

// Class Game
public class Game {
    private static final int HEIGHT = 10;
    private static final int WIDTH = 10;
    private static final int[] WATER_ROWS = {4, 5};
    private static final int[] WATER_COLS = {2, 3, 6, 7};

    private final Player p0;
    private final Player p1;

    public static Square[][] board = new Square[HEIGHT][WIDTH];

    public Game(Player p0, Player p1) {
        this.p0 = p0;
        this.p1 = p1;
        Game game = this;

        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                for (int water_rows : WATER_ROWS) {
                    for (int water_cols : WATER_COLS) {
                        if ((water_rows != row) && (water_cols != col)) {
                            board[row][col] = new Square(game, row, col, false);
                        }
                    }
                }
            }
        }

        for (int water_rows : WATER_ROWS) {
            for (int water_cols : WATER_COLS) {
                board[water_rows][water_cols] = new Square(game, 4, 2, true);
            }
        }

    }

    public Player getPlayer(int playerNumber) {
        if (this.p0.getPlayerNumber() == playerNumber) {
            return this.p0;
        } else if ((this.p0.getPlayerNumber() != playerNumber) &&
                (this.p1.getPlayerNumber() != playerNumber)) {
            throw new IllegalArgumentException("No such player exists!!");
        }

        return this.p1;
    }

    public Player getWinner() {

        if (Objects.equals(this.p0.getPlayerGameStatus(), CombatResult.LOSE) ||
                Objects.equals(this.p1.getPlayerGameStatus(), CombatResult.WIN)) {
            return this.p1;
        } else if (Objects.equals(this.p1.getPlayerGameStatus(), CombatResult.LOSE) ||
                Objects.equals(this.p0.getPlayerGameStatus(), CombatResult.WIN)) {
            return this.p0;
        }
        return null;
    }


    public Square getSquare(int row, int col) {

        if (row > HEIGHT || col > WIDTH) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        } else {
            return board[row][col];
        }
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static int getWidth() {
        return WIDTH;
    }
}
