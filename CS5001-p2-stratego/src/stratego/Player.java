package stratego;

import java.util.ArrayList;

// Class Player
public class Player {

    public final String name;
    public final int playerNumber;
    public boolean lost = false;

    public Player(String name, int playerNumber) {
        this.name = name;
        this.playerNumber = playerNumber;
    }

    public String getName() {

        return null;
    }

    public int getPlayerNumber() {
        return 0;
    }

    public void loseGame() {
        lost = true;
    }

    public boolean hasLost() {

        return false;
    }
}