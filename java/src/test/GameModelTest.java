import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import com.google.gson.*;

import java.util.*;

import shared.deserializers.*;
import shared.communication.servers.*;
import shared.commands.*;
import client.server.*;
import shared.model.*;
import shared.model.player.*;
import shared.definitions.CatanColor;

public class GameModelTest {
    private ServerProxy server; 
    private Game game;
    private String expected;

    @Before
    public void setUp() {
        this.server = new ServerProxy();
        this.expected = "{'deck': {'monument': 5, 'soldier': 14, 'roadBuilding': 2, 'monopoly': 2, 'yearOfPlenty': 2}, 'bank': {'brick': 24, 'ore': 24, 'wood': 24, 'wheat': 24, 'sheep': 24}, 'players': [{'oldDevCards': {'monument': 0, 'soldier': 0, 'roadBuilding': 0, 'monopoly': 0, 'yearOfPlenty': 0}, 'color': 'red', 'resources': {'brick': 0, 'ore': 0, 'wood': 0, 'wheat': 0, 'sheep': 0}, 'cities': 4, 'victoryPoints': 0, 'discarded': False, 'playedDevCard': False, 'playerID': 0, 'monuments': 0, 'newDevCards': {'monument': 0, 'soldier': 0, 'roadBuilding': 0, 'monopoly': 0, 'yearOfPlenty': 0}, 'roads': 15, 'settlements': 5, 'playerIndex': 0, 'name': 'Sam', 'soldiers': 0}, {'oldDevCards': {'monument': 0, 'soldier': 0, 'roadBuilding': 0, 'monopoly': 0, 'yearOfPlenty': 0}, 'color': 'red', 'resources': {'brick': 0, 'ore': 0, 'wood': 0, 'wheat': 0, 'sheep': 0}, 'cities': 4, 'victoryPoints': 0, 'discarded': False, 'playedDevCard': False, 'playerID': 12, 'monuments': 0, 'newDevCards': {'monument': 0, 'soldier': 0, 'roadBuilding': 0, 'monopoly': 0, 'yearOfPlenty': 0}, 'roads': 15, 'settlements': 5, 'playerIndex': 1, 'name': 'test', 'soldiers': 0}, None, None], 'winner': -1, 'map': {'robber': {'y': 0, 'x': -2}, 'radius': 3, 'ports': [{'resource': 'wheat', 'direction': 'NW', 'location': {'y': -1, 'x': 3}, 'ratio': 2}, {'direction': 'S', 'location': {'y': -3, 'x': 1}, 'ratio': 3}, {'resource': 'ore', 'direction': 'SW', 'location': {'y': -3, 'x': 3}, 'ratio': 2}, {'resource': 'brick', 'direction': 'NE', 'location': {'y': 3, 'x': -2}, 'ratio': 2}, {'resource': 'sheep', 'direction': 'N', 'location': {'y': 3, 'x': 0}, 'ratio': 2}, {'direction': 'S', 'location': {'y': -2, 'x': -1}, 'ratio': 3}, {'direction': 'NW', 'location': {'y': 1, 'x': 2}, 'ratio': 3}, {'direction': 'SE', 'location': {'y': 0, 'x': -3}, 'ratio': 3}, {'resource': 'wood', 'direction': 'NE', 'location': {'y': 2, 'x': -3}, 'ratio': 2}], 'roads': [], 'settlements': [], 'cities': [], 'hexes': [{'number': 9, 'resource': 'sheep', 'location': {'y': -2, 'x': 0}}, {'number': 4, 'resource': 'wood', 'location': {'y': -2, 'x': 1}}, {'number': 5, 'resource': 'brick', 'location': {'y': -2, 'x': 2}}, {'number': 5, 'resource': 'ore', 'location': {'y': -1, 'x': -1}}, {'number': 3, 'resource': 'wood', 'location': {'y': -1, 'x': 0}}, {'number': 6, 'resource': 'wood', 'location': {'y': -1, 'x': 1}}, {'number': 10, 'resource': 'sheep', 'location': {'y': -1, 'x': 2}}, {'location': {'y': 0, 'x': -2}}, {'number': 11, 'resource': 'wheat', 'location': {'y': 0, 'x': -1}}, {'number': 11, 'resource': 'wood', 'location': {'y': 0, 'x': 0}}, {'number': 10, 'resource': 'sheep', 'location': {'y': 0, 'x': 1}}, {'number': 12, 'resource': 'sheep', 'location': {'y': 0, 'x': 2}}, {'number': 6, 'resource': 'wheat', 'location': {'y': 1, 'x': -2}}, {'number': 8, 'resource': 'brick', 'location': {'y': 1, 'x': -1}}, {'number': 9, 'resource': 'ore', 'location': {'y': 1, 'x': 0}}, {'number': 2, 'resource': 'wheat', 'location': {'y': 1, 'x': 1}}, {'number': 4, 'resource': 'brick', 'location': {'y': 2, 'x': -2}}, {'number': 3, 'resource': 'ore', 'location': {'y': 2, 'x': -1}}, {'number': 8, 'resource': 'wheat', 'location': {'y': 2, 'x': 0}}]}, 'log': {'lines': []}, 'version': 0, 'turnTracker': {'status': 'FirstRound', 'currentTurn': 0, 'largestArmy': -1, 'longestRoad': -1}, 'chat': {'lines': []}}";
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test_GameModelMock() {
        String response = server.execute(new GameModel());
        assertEquals(expected, response);

    }
    @Ignore
    @Test
    public void test_GameModelLive() {
        this.server = new ServerProxy("localhost", "8081");
        this.server.execute(new UserLogin("Sam", "sam"));
        String response = this.server.execute(new GamesList());
        if (response == null) {
            System.out.println("Server is not running");
            return;
        }
        
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(response).getAsJsonArray();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Game.class, new GamesCreateDeserializer());
        Gson gson = gsonBuilder.create();

        boolean found = false;
        for (int i=0; i < array.size(); i++){
            game = gson.fromJson(array.get(i), Game.class);
            System.out.println("Game: " + game.getName());
            List<Player> players = game.getPlayers();
            for (Player player: players){
                if (player.getName().equals("Sam")) {
                    found = true;
                    this.server.execute(new GamesJoin(game.getId(), CatanColor.PUCE)); 
                    break;
                }
            }
            if (found == true) { 
                break; 
            }
        }

        if (found != true) {
            System.out.println("No empty games found");
            return;
        }

        
        response = this.server.execute(new GameModel());
        game = gson.fromJson(response, Game.class);
        assertEquals(4, game.getPlayers().size());
    }
}
