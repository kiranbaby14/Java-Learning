package stratego;
package stratego.pieces;

// enum CombatResult
public final enum CombatResult {
    WIN,
    DRAW,
    LOSE
}

// Class Game
public class Game {
    public final int HEIGHT = 10;
    public final int WIDTH = 10;
    public final int[] WATER_ROWS = {4, 5};
    public final int[] WATER_COLS = {2, 3, 6, 7};

    public Game(Player p0, Player p1) {
        this.p0 = p0;
        this.p1 = p1;
    }

    public Player getPlayer(int playerNumber) {
        if (this.p0.playernumber.equals(playerNumber)) {
            return this.p0
        } else if (!this.p0.playernumber.equals(playerNumber) && !this.p1.playernumber.equals(playerNumber) {
            throw new IllegalArgumentException("No such player exists!!");
        }

        return this.p1
    }

    public Player getWinner() {
        if(this.p0.lost) {
            return this.p1
        } else if(this.p1.lost) {
            return this.p0
        }
    }

    public Square getSquare(int row, int col) {
        if(row > 10 || col >10) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        } else {

        }
    }
}

// Class Player
public class Player {

    public boolean lost = false;
    public Player(String name, int playerNumber) {
        this.name = name;
        this.playerNumber = playerNumber;
    }

    public String getName() {

    }

    public int getPlayerNumber() {

    }

    public void loseGame() {
        lost = true;
    }

    public boolean hasLost() {

    }
}

// class Square
public class Square {
    public Square(Game game, int row, int col, boolean isWater) {
        this.game = game;
        this.row = row;
        this.col = col;
        this.isWater = isWater;
    }

    public void placePiece(Piece piece) {

    }

    public void removePiece() {

    }

    public Game getGame() {

    }

    public Piece getPiece() {

    }

    public int getRow() {

    }

    public int getCol() {

    }

    public boolean canBeEntered() {

    }
}
