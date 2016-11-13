package server.facades;

import java.util.ArrayList;
import java.util.Arrays;
import shared.communication.User;
import shared.definitions.AIType;
import shared.model.Game;
import com.google.gson.*;

public class GameFacade extends BaseFacade {

    public GameFacade(User user){
        super(user);
    }


    /**
     * @return Returns the current state of the game in JSON format.
     * @pre 1. The caller has previously logged in to the server and joined a game (i.e., they have valid catan.user and catan.game HTTP cookies).
     * @ore 2. If specified, the version number is included as the “version” query parameter in the request URL, and its value is a valid integer.
     * @post 1. The server returns an HTTP 200 success response.
     * @post 2. The response body contains JSON data
    a. The full client model JSON is returned if the caller does not provide a version
    number, or the provide version number does not match the version on the server
    b. “true” (true in double quotes) is returned if the caller provided a version number,
    and the version number matched the version number on the server
     */
    public String model(){
        Game game = new Game();
        game.setId(0);
        game.setName("model-test");
        return new Gson().toJson(game);
    }

    /**
     * @return Returns a list of supported AI player types
     * @post 1. The server returns an HTTP 200 success response.
     * @post 2. The body contains a JSON string array enumerating the different types of AI players.
     */
    public String listAI(){
        return new Gson().toJson(new ArrayList<AIType>(Arrays.asList(AIType.values())));
    }

    /**
     * Adds an AI player to the current game.
     * @pre 1. The caller has previously logged in to the server and joined a game (i.e., they have valid catan.user and catan.game HTTP cookies).
     * @pre 2. There is space in the game for another player (i.e., the game is not “full”).
     * @pre 3. The specified “AIType” is valid (i.e., one of the values returned by the /game/listAI method).
     * @post 1. The server returns an HTTP 200 success response with “Success” in the body.
     * @post 2. A new AI player of the specified type has been added to the current game. The server selected a name and color for the player.
     */
    public String addAI(AIType type){
        Game game = new Game();
        game.setId(0);
        game.setName("addai-test");
        return new Gson().toJson(game);
    }
}
