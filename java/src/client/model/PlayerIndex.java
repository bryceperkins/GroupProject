package client.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Associates the index of a player with an enumerated value
 */
public enum PlayerIndex {
    None(-1), Player1(0), Player2(1), Player3(2), Player4(3);

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
}
