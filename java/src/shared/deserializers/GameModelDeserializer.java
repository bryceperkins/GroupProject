package shared.deserializers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import client.model.map.*;
import com.google.gson.*;
import client.model.player.*;
import client.model.*;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

public class GameModelDeserializer implements JsonDeserializer<Game> {

    private Gson gson = new Gson();

    @Override
    public Game deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        // Break the JSON apart piece by piece.  Cast the whole thing as a generic jsonObject
        final JsonObject jsonObject = json.getAsJsonObject();
        final Game game = new Game();

        // Handle the nesting

        ResourceBank bank = gson.fromJson(jsonObject.get("bank"), ResourceBank.class);
        TurnTracker turnTracker = gson.fromJson(jsonObject.get("turnTracker"), TurnTracker.class);
        Chat chat = parseChat(jsonObject.get("chat"));
        Log log = parseLog(jsonObject.get("log"));
        List<Player> players = parsePlayers(jsonObject.get("players"));

        // Create the final Game object
        game.setBank(bank);
        game.setTurnTracker(turnTracker);
        game.setChat(chat);
        game.setPlayers(players);
        return game;
    }

    private Map parseMap(JsonElement mapElement) {
        final JsonObject mapObject = mapElement.getAsJsonObject();
        Map map = new Map();

        List<Hex> hexes = parseHexes(mapObject.get("hexes"));
        List<Port> ports = parsePorts(mapObject.get("ports"));

        map.setHexes(hexes);
        map.setPorts(ports);

        return map;
    }

    private List<Port> parsePorts(JsonElement portsElement) {
        final JsonArray jsonPortsArray = portsElement.getAsJsonArray();
        final List<Port> ports = new ArrayList<>();

        for (int i = 0; i < jsonPortsArray.size(); i++) {
            ports.add(parsePort(jsonPortsArray.get(i)));
        }

        return ports;
    }

    private Port parsePort(JsonElement portElement) {
        JsonObject portObject = portElement.getAsJsonObject();
        Port port = new Port();

        final ResourceType resourceType = ResourceType.valueOf(portObject.get("resource").getAsString());
        final HexLocation location = gson.fromJson(portObject.get("location"), HexLocation.class);
        final HexDirection direction = HexDirection.valueOf(portObject.get("direction").getAsString());
        final int ratio = portObject.get("ratio").getAsInt();

        port.setResource(resourceType);
        port.setLocation(location);
        port.setDirection(direction);
        port.setRatio(ratio);

        return port;
    }

    private List<Hex> parseHexes(JsonElement hexesElement) {
        final JsonArray jsonHexArray = hexesElement.getAsJsonArray();
        final List<Hex> hexes = new ArrayList<>();

        for (int i = 0; i < jsonHexArray.size(); i++) {
            hexes.add(parseHex(jsonHexArray.get(i)));
        }

        return hexes;
    }

    private Hex parseHex(JsonElement hexElement) {
        JsonObject hexObject = hexElement.getAsJsonObject();
        Hex hex = new Hex();

        final HexLocation location = gson.fromJson(hexObject.get("location"), HexLocation.class);
        final ResourceType resourceType = ResourceType.valueOf(hexObject.get("resource").getAsString());
        final int number = hexObject.get("number").getAsInt();

        hex.setLocation(location);
        hex.setResource(resourceType);
        hex.setValue(number);

        return hex;
    }

    private Chat parseChat(JsonElement chatElement) {
        final JsonArray jsonLinesArray = chatElement.getAsJsonObject().get("lines").getAsJsonArray();
        final Chat chat = new Chat();
        final List<MessageLine> lines = new ArrayList<>();

        for (int i = 0; i < jsonLinesArray.size(); i++) {
            MessageLine line = gson.fromJson(jsonLinesArray.get(i), MessageLine.class);
            lines.add(line);
        }

        chat.setLines(lines);
        return chat;
    }

    private Log parseLog(JsonElement logElement) {
        final JsonArray jsonLinesArray = logElement.getAsJsonObject().get("lines").getAsJsonArray();
        final Log log = new Log();
        final List<MessageLine> lines = new ArrayList<>();

        for (int i = 0; i < jsonLinesArray.size(); i++) {
            MessageLine line = gson.fromJson(jsonLinesArray.get(i), MessageLine.class);
            lines.add(line);
        }

        log.setLines(lines);
        return log;
    }

    private List<Player> parsePlayers(JsonElement playersElement) {
        final JsonArray jsonPlayersArray = playersElement.getAsJsonArray();
        final List<Player> players = new ArrayList<>();

        for (int i = 0; i < jsonPlayersArray.size(); i++) {
            Player player = gson.fromJson(jsonPlayersArray.get(i), Player.class);
            players.add(player);
        }

        return players;
    }
}
