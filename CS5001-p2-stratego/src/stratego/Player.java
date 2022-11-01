package stratego;

import java.util.Objects;

// Class Player
public class Player {

    private final String name;
    private final int playerNumber;
    private CombatResult playerGameStatus;

    public Player(String name, int playerNumber) {
        this.name = name;
        this.playerNumber = playerNumber;
    }

    public String getName() {
        return this.name;
    }

    public int getPlayerNumber() {
        return this.playerNumber;
    }

    public void loseGame() {
        this.playerGameStatus = CombatResult.LOSE;
    }

    public boolean hasLost() {
        return Objects.equals(this.playerGameStatus, CombatResult.LOSE);
    }

    public CombatResult getPlayerGameStatus() {
        return playerGameStatus;
    }
}
