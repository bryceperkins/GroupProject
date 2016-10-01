package shared.deserializers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.*;
import client.model.player.*;
import client.model.*;

public class GamesCreateDeserializer implements JsonDeserializer<Game> {
    /**
     *  deserialize
     *
     *  Pull apart objects that contain nested objects that are not easily deserialized. Specifically used for the create game object which pulls apart an empty game which is returned when a new game is created.
     *
     *  @param json - The element containing nested objects that need to be parsed.
     */



    @Override
    public Game deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        // Break the JSON apart piece by piece.  Cast the whole thing as a generic jsonObject
        final JsonObject jsonObject = json.getAsJsonObject();
        final Game game = new Game();

        // Pull off the known elements - We know a title exists and is a string
        final JsonElement jsonTitle = jsonObject.get("title");
        game.setName(jsonTitle.getAsString());

        // Pull off the known elements - We know ID  exists and is an int
        game.setId(jsonObject.get("id").getAsInt());

        // Handle the nested players
        Gson gson = new Gson();

        // Multiple players exist, read it into a generic array 
        final JsonArray jsonPlayersArray = jsonObject.get("players").getAsJsonArray();
        
        // Create the HashMap that holds players as specified in the Game Model.
        final List<Player> players = new ArrayList<>();

        // Add each player to the HashMap
        for (int i = 0; i < jsonPlayersArray.size(); i++) {
            // Gson cast each item in the list to a Player
            Player player = gson.fromJson(jsonPlayersArray.get(i), Player.class);
            players.add(player);
        }

        // Create the final Game object
        game.setPlayers(players);
        return game;
    }
}
