package server.facades;

import java.util.*;
import java.util.stream.Collectors;

import shared.definitions.AI;
import shared.communication.User;
import shared.definitions.AIType;
import com.google.gson.*;
import shared.definitions.CatanColor;
import shared.model.GameManager;
import shared.model.PlayerIndex;
import shared.model.player.Player;

public class GameFacade extends BaseFacade {

    GameManager manager = GameManager.getInstance();

    public GameFacade(User user){
        super(user);
    }

    /**
     * @return Returns the current state of the game in JSON format.
     * @pre 1. The caller has previously logged in to the server and joined a game (i.e., they have valid catan.user and catan.game HTTP cookies).
     * @pre 2. If specified, the version number is included as the "version" query parameter in the request URL, and its value is a valid integer.
     * @post 1. The server returns an HTTP 200 success response.
     * @post 2. The response body contains JSON data
    a. The full client model JSON is returned if the caller does not provide a version
    number, or the provide version number does not match the version on the server
    b. "true" (true in double quotes) is returned if the caller provided a version number,
    and the version number matched the version number on the server
     */
    public String model(){
        return getModel();
    }

    /**
     * @return Returns a list of supported AI player types
     * @post 1. The server returns an HTTP 200 success response.
     * @post 2. The body contains a JSON string array enumerating the different types of AI players.
     */
    public String listAI(){
        return new Gson().toJson(new ArrayList<AIType>(Arrays.asList(AIType.values())));
    }

    private List<String> names = new ArrayList<String>() {{
        add("Larry");
        add("Steve");
        add("John");
    }};

    /**
     * Adds an AI player to the current game.
     * @pre 1. The caller has previously logged in to the server and joined a game (i.e., they have valid catan.user and catan.game HTTP cookies).
     * @pre 2. There is space in the game for another player (i.e., the game is not "full").
     * @pre 3. The specified "AIType" is valid (i.e., one of the values returned by the /game/listAI method).
     * @post 1. The server returns an HTTP 200 success response with "Success" in the body.
     * @post 2. A new AI player of the specified type has been added to the current game. The server selected a name and color for the player.
     */
    public String addAI(AIType type){
        PlayerIndex index = PlayerIndex.valueOf(getGame().getPlayers().size());

        CatanColor playerColor = CatanColor.BLUE;
        List<CatanColor> takenColors = getGame().getPlayers().stream().map(player -> player.getColor()).collect(Collectors.toList());
        for (CatanColor color : CatanColor.values()) {
            if (!takenColors.contains(color)) {
                playerColor = color;
                break;
            }
        }

        manager.addAI(new AI(getGame(), index)); //TODO figure out what to do with this
        getGame().getPlayers().add(new Player(playerColor, names.get(index.getIndex()), 1, index, -1));

        return getModel();
    }
}
