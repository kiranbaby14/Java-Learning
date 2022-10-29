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
        if(this.p0.playernumber.equals(playerNumber)) {
            return this.p0
        }

        return this.p1
    }

    public Player getWinner() {

    }

    public Square getSquare(int row, int col) {

    }
}

// Class Player
public class Player {
    public Player(String name, int playerNumber) {
        this.name = name;
        this.playerNumber = playerNumber;
    }

    public String getName() {

    }

    public int getPlayerNumber() {

    }

    public void loseGame(){

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
