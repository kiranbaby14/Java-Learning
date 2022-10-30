package stratego;

import java.util.ArrayList;

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