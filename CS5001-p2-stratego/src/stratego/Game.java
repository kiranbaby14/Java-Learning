package stratego;

import java.util.ArrayList;

// Class Game
public class Game {
    public final int HEIGHT = 10;
    public final int WIDTH = 10;
    public final int[] WATER_ROWS = {4, 5};
    public final int[] WATER_COLS = {2, 3, 6, 7};

    private final Player p0;
    private final Player p1;


    public Game(Player p0, Player p1) {
        this.p0 = p0;
        this.p1 = p1;
    }

    public Player getPlayer(int playerNumber) {
        if (this.p0.playerNumber == playerNumber) {
            return this.p0;
        } else if (!(this.p0.playerNumber == playerNumber) && !(this.p1.playerNumber == playerNumber)) {
            throw new IllegalArgumentException("No such player exists!!");
        }

        return this.p1;
    }

    public Player getWinner() {

        if (this.p0.lost) {
            return this.p1;
        } else if (this.p1.lost) {
            return this.p0;
        }
        return null;
    }

    public Square getSquare(int row, int col) {

        if (row > HEIGHT || col > WIDTH) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        } else {
            Game game = new Game(this.p0, this.p1);
            for (int water_row : WATER_ROWS) {
                for (int water_col : WATER_COLS) {

                    if (row == water_row && col == water_col) {
                        return new Square(game, HEIGHT, WIDTH, true);
                    } else {
                        return new Square(game, HEIGHT, WIDTH, false);
                    }
                }
            }
        }
        return null;
    }
}
