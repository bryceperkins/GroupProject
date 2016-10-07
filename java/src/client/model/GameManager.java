package client.model;

import java.util.ArrayList;
import client.model.*;
import client.model.player.Player;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import shared.communication.User;

/**
 * Facade class which statically manages the client's games and updates
 * the model based on the results from the server
 */
public class GameManager {

    private static Game activeGame;

    private static ArrayList<Game> games = new ArrayList<>();
    private static int activeGameIndex = -1;
    private static PlayerIndex activePlayer;

    private static Game createGame(String json) {
        Gson gson = new Gson();
        JsonElement jsonElement = new JsonParser().parse(json);
        Game game = gson.fromJson(jsonElement, Game.class);
        game.postDeserializationSetup(game);

        return game;
    }

    /**
     * Updates or creates a game on the client from the provided JSON
     * @pre a valid json object is given as reflected in the documentation
     * @post the game is updated or added if it did not previously exist
     * @param json JSON containing the Game model to update to
     */
    public static void processGame(String json) {
        Game game = createGame(json);
        int index = gameIndex(game.getId());
        if (index >= 0) {
            games.set(index, game);
            activeGameIndex = index;
        } else {
            games.add(game);
            activeGameIndex = games.size() - 1;
        }
    }

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

    public static Player getActivePlayer() {
        Game game = getActiveGame();
        return (game == null) ? null : game.getPlayer(activePlayer);
    }

    public static Game getGame(int id) {
        int index = gameIndex(id);
        return (index < 0) ? null : games.get(index);
    }

    private static int gameIndex(int id) {
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).getId() == id) {
                return i;
            }
        }

        return -1;
    }

    public static PlayerIndex getActivePlayerIndex() {
        return activePlayer;
    }

}
