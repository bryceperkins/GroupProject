package client.model;

/**
 * Keeps track of the current turn and game state for a given game
 */
public class TurnTracker {

    private PlayerIndex currentTurn;
    private GameStatus status;
    private PlayerIndex longestRoadOwner;
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
    private enum GameStatus {
        Rolling, Robbing, Playing, Discarding, FirstRound, SecondRound;
    }

}
