package shared.deserializers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.*;

import client.model.player.*;
import client.model.*;
import client.model.map.*;
import shared.locations.*;
import shared.definitions.ResourceType;

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
        Map map = new Map();
        final JsonElement jsonMap = jsonObject.get("map");
        final JsonArray hexArray = jsonMap.getAsJsonObject().get("hexes").getAsJsonArray();
        final List<Hex> hexes = new ArrayList<>();
        for (int i = 0; i < hexArray.size(); i++) {
            HexLocation hex = gson.fromJson(hexArray.get(i), HexLocation.class);
            JsonElement number = jsonObject.get("number");
            JsonElement resource = jsonObject.get("resource");
            Hex h = new Hex(hex, false, number.getAsInt(), ResourceType.valueOf(resource.getAsString()));
            hexes.add(h);
        }

        final JsonArray jsonPlayersArray = jsonObject.get("players").getAsJsonArray();
        final List<Player> players = new ArrayList<>();
        for (int i = 0; i < jsonPlayersArray.size(); i++) {
            Player player = gson.fromJson(jsonPlayersArray.get(i), Player.class);
            players.add(player);
        }

        game.setPlayers(players);
        map.setHexes(hexes);
        game.setMap(map);
        return game;
    }
}
