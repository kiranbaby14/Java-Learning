package stratego;

import java.util.ArrayList;

// Class Game
public class Game {
    private final int HEIGHT = 10;
    private final int WIDTH = 10;
    private final int[] WATER_ROWS = {4, 5};
    private final int[] WATER_COLS = {2, 3, 6, 7};

    // Board of the game
    public ArrayList<ArrayList<Integer>> game = new ArrayList<ArrayList<Integer>>();


    public Game(Player p0, Player p1) {
        this.p0 = p0;
        this.p1 = p1;
    }

    public Player getPlayer(int playerNumber) {
        if (this.p0.playernumber.equals(playerNumber)) {
            return this.p0;
        } else if (!this.p0.playernumber.equals(playerNumber) && !this.p1.playernumber.equals(playerNumber)) {
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
    }

    public Square getSquare(int row, int col) {
        if (row > HEIGHT || col > WIDTH) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        } else {

            for (int i = 0; i < HEIGHT; i++) {
                for (int j=0; j<WIDTH; j++) {
                    if(row.equals(i) && col.equals(j)) {
                        Square squareObj = new Square(game, HEIGHT, WIDTH, true);
                    } else {
                        Square squareObj = new Square(game, HEIGHT, WIDTH, false);
                    }
                }
            }
        }
    }
}