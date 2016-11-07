package client.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

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
        Rolling("Roll the Dice", false),
        Robbing("Place the Robber", false),
        Playing("Finish Turn", true),
        Discarding("Discard Cards", false),
        FirstRound("Finish Turn", true),
        SecondRound("Finish Turn", true);

        private String prompt;
        private boolean buttonEnabled;

        GameStatus(String prompt, boolean enabled) {
            this.prompt = prompt;
            this.buttonEnabled = enabled;
        }

        public String getPrompt() {
            return prompt;
        }

        public boolean isButtonEnabled() {
            return buttonEnabled;
        }
    }

    public void nextTurn(){

    }

    public void setLongestRoadOwner(PlayerIndex player){

    }

    public void setLargestArmyOwner(PlayerIndex player){

    }

}
