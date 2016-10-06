package client.model;

import com.google.gson.annotations.SerializedName;

/**
 * Keeps track of the current turn and game state for a given game
 */
public class TurnTracker {

    private PlayerIndex currentTurn;
    private GameStatus status;
    @SerializedName("longestRoad")
    private PlayerIndex longestRoadOwner;
    @SerializedName("longestArmy")
    private PlayerIndex largestArmyOwner;

    public PlayerIndex getCurrentTurn() {
        return currentTurn;
    }

    public GameStatus getStatus() {
        return status;
    }

    public PlayerIndex getLongestRoadOwner() {
        return longestRoadOwner;
    }

    public PlayerIndex getLargestArmyOwner() {
        return largestArmyOwner;
    }

    /**
     * The different possible states of the game
     */
    public enum GameStatus {
        Rolling, Robbing, Playing, Discarding, FirstRound, SecondRound;
    }

}
