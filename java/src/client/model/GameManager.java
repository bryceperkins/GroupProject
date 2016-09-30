package client.model;

import java.util.ArrayList;
import client.model.*;

/**
 * Facade class which statically manages the client's games and updates
 * the model based on the results from the server
 */
public class GameManager {

    private static Game activeGame;

    private static ArrayList<Game> games;
    private static int activeGameIndex = -1;

    private static void createGame(String json) {

    }

    private static void updateGame(String json) {}

    /**
     * Updates or creates a game on the client from the provided JSON
     * @pre a valid json object is given as reflected in the documentation
     * @post the game is updated or added if it did not previously exist
     * @param json JSON containing the Game model to update to
     */
    public static void processGame(String json) {}

    /**
     * @param game the game to join
     * @pre the game exists 
     * @return whether or not the user can join the specified game
     */
    public static boolean userCanJoinGame(Game game) {
        return true;
    }

    /**
     * @return The model of the game active on the client, or null if no game is active
     */
    public static Game getActiveGame() {
        return activeGame;
    }

    public static ArrayList<Game> getGames() {
        return games;
    }

}
