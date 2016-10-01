package client.model;

import com.google.gson.annotations.SerializedName;

/**
 * Associates the index of a player with an enumerated value
 */
public enum PlayerIndex {
    None(-1), Player1(0), Player2(1), Player3(2), Player4(3);

    private int index;

    PlayerIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
