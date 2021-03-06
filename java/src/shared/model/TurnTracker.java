package shared.model;

import com.google.gson.annotations.SerializedName;

import java.util.Observable;
import java.io.Serializable;

/**
 * Keeps track of the current turn and game state for a given game
 */
public class TurnTracker implements Serializable{

    private PlayerIndex currentTurn;
    private GameStatus status;
    @SerializedName("longestRoad")
    private PlayerIndex longestRoadOwner;
    @SerializedName("largestArmy")
    private PlayerIndex largestArmyOwner;
    private int round = 1; // 1 based

    public TurnTracker() {
        currentTurn = PlayerIndex.Player1;
        status = GameStatus.FirstRound;
        longestRoadOwner = PlayerIndex.None;
        largestArmyOwner = PlayerIndex.None;
    }

    public PlayerIndex getCurrentTurn() {
        return currentTurn;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setLongestRoadOwner(PlayerIndex i) {
        longestRoadOwner = i;
    }

    public void setLargestArmyOwner(PlayerIndex i) {
        largestArmyOwner = i;
    }

    public PlayerIndex getLongestRoadOwner() {
        return longestRoadOwner;
    }

    public PlayerIndex getLargestArmyOwner() {
        return largestArmyOwner;
    }

    public void setGameStatus(GameStatus status) {
        this.status = status;
    }

    public int getRound() {
        return round;
    }

    public void setNextTurn() {
        int nextTurnInt = currentTurn.getIndex();
        if (round == 2){
            nextTurnInt--;
            if (nextTurnInt < 0){
                nextTurnInt = 0;
                nextRound();
                setGameStatus(GameStatus.Rolling);
            }
        }
        else{
            nextTurnInt++;
            if (nextTurnInt > 3){
                nextTurnInt = 3;
                if (round > 2){
                    nextTurnInt = 0;
                }
                nextRound();
            }
            if (round > 2){
                setGameStatus(GameStatus.Rolling);
            }
        }
        this.currentTurn = PlayerIndex.valueOf(nextTurnInt);
    }

    public void nextRound() {
        setRound(getRound() + 1);
    }

    private void setRound(int round) {
        this.round = round;

        if (round == 1) {
            status = GameStatus.FirstRound;
        } else if (round == 2) {
            status = GameStatus.SecondRound;
        }
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

}
