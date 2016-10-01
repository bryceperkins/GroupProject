package shared.deserializers;

import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;

import com.google.gson.*;
import client.model.player.*;
import client.model.*;

public class DeGame implements JsonDeserializer<Game> {

  @Override
  public Game deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
      throws JsonParseException {
    final JsonObject jsonObject = json.getAsJsonObject();

    final JsonElement jsonTitle = jsonObject.get("title");
    final String title = jsonTitle.getAsString();

    final int id = Integer.parseInt(jsonObject.get("id").getAsString());

    Gson gson = new Gson();

    final JsonArray jsonPlayersArray = jsonObject.get("players").getAsJsonArray();
    final List<Player> players = new ArrayList<Player>();
    for (int i = 0; i < jsonPlayersArray.size(); i++) {
        Player player = gson.fromJson(jsonPlayersArray.get(i), Player.class);
        players.add(player);
    }

    final Game game = new Game();
    game.setName(title);
    game.setPlayers(players);
    return game;
  }
}
