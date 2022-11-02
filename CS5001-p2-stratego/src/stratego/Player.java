package stratego;

import java.util.Objects;

/**
 * Player
 * Package to initialise the players.
 *
 * @author: Student ID: 220015821
 */
public class Player {

    private final String name;
    private final int playerNumber;
    private CombatResult playerGameStatus;
    private int countPieces = 0;

    /**
     * Constructor for the class Player.
     *
     * @param name         player's name
     * @param playerNumber player's particular number
     */
    public Player(String name, int playerNumber) {
        this.name = name;
        this.playerNumber = playerNumber;
    }

    /**
     * Getter method to get the name of the player.
     *
     * @return player's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter method to get the number of the player.
     *
     * @return player's number
     */
    public int getPlayerNumber() {
        return this.playerNumber;
    }

    /**
     * Method to set the status of a player to LOSE.
     */
    public void loseGame() {
        this.playerGameStatus = CombatResult.LOSE;
    }

    /**
     * Method to check whether a player has lost the game or not.
     *
     * @return boolean value of whether player has lost or not
     */
    public boolean hasLost() {
        return Objects.equals(this.playerGameStatus, CombatResult.LOSE);
    }

    /**
     * Getter method to get the status of a player.
     *
     * @return the status of the player
     */
    public CombatResult getPlayerGameStatus() {
        return playerGameStatus;
    }

    /**
     * Getter method to get the total count of a pieces per player.
     *
     * @return the count of the number of pieces
     */
    public int getCountPieces() {
        return countPieces;
    }

    /**
     * Setter method to set the total count of a pieces per player.
     */
    public void setCountPieces() {
        countPieces += 1;
    }
}
