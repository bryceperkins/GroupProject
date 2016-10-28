package client.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Associates the index of a player with an enumerated value
 */
public enum PlayerIndex {
    @SerializedName("-1")
    None(-1),
    @SerializedName("0")
    Player1(0),
    @SerializedName("1")
    Player2(1),
    @SerializedName("2")
    Player3(2),
    @SerializedName("3")
    Player4(3);

    private int index;

    private static Map<Integer, PlayerIndex> map = new HashMap<>();

    static {
        for (PlayerIndex player : PlayerIndex.values()) {
            map.put(player.index, player);
        }
    }

    PlayerIndex(int index) {
        this.index = index;
    }

    public static PlayerIndex valueOf(int index) {
        return map.get(index);
    }

    public int getIndex() {
        return index;
    }
	public static PlayerIndex createPlayerAtIndex(int index){
		if (index == 0) return PlayerIndex.Player1;
		if (index == 1) return PlayerIndex.Player2;
		if (index == 2) return PlayerIndex.Player3;
		if (index == 3) return PlayerIndex.Player4;
		return PlayerIndex.None;
	}
}
