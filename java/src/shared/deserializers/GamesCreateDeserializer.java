package shared.deserializers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;

import shared.model.player.*;
import shared.model.*;

public class GamesCreateDeserializer implements JsonDeserializer<Game> {
    @Override
    public Game deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        // Break the JSON apart piece by piece.  Cast the whole thing as a generic jsonObject
        final JsonObject jsonObject = json.getAsJsonObject();
        final Game game = new Game();

        // Pull off the known elements - We know a title exists and is a string
        final JsonElement jsonTitle = jsonObject.get("title");
        if (jsonTitle != null) {
            game.setName(jsonTitle.getAsString());
        }

        // Pull off the known elements - We know ID  exists and is an int
        final JsonElement jsonID = jsonObject.get("id");
        if (jsonID != null) {
            game.setId(jsonID.getAsInt());
        }

        // Handle the nested players
        Gson gson = new Gson();

        // Multiple players exist, read it into a generic array 
        final JsonArray jsonPlayersArray = jsonObject.get("players").getAsJsonArray();
        final List<Player> players = new ArrayList<>();
        for (JsonElement j: jsonPlayersArray) {
            Player player = gson.fromJson(j, Player.class);
            if(player.getName() == null){
                continue;
            }
            players.add(player);
        }
        game.setPlayers(players);
        return game;
    }
}
