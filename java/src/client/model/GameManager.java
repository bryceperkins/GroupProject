package client.model;

import java.util.ArrayList;

import shared.communication.*;

/**
 * Facade class which statically manages the client's games and updates
 * the model based on the results from the server
 */
public class GameManager {

    private static ArrayList<Game> games;
    private static int activeGameIndex = -1;

    private static void createGame(String json) {}

    private static void updateGame(String json) {}

    /**
     * Updates or creates a game on the client from the provided JSON
     * @pre a valid json object is given as reflected in the documentation
     * @post the game is updated or added if it did not previously exist
     * @param json JSON containing the Game model to update to
     */
    public static void processGame(String json) {}

    /**
     * @param gameIndex the index of the game to join
     * @param user the user of inquiry
     * @pre a game exists at gameIndex
     * @return whether or not the user can join the specified game
     */
    public static boolean userCanJoinGame(Game game) {}

    /**
     * @return The model of the game active on the client, or null if no game is active
     */
    public static Game getActiveGame() {}

    public static ArrayList<Game> getGames() {
        return games;
    }

}
