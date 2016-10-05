import static org.junit.Assert.*;

import client.model.Game;
import client.model.GameManager;
import client.model.PlayerIndex;
import client.model.ResourceType;
import client.model.map.Direction;
import client.model.player.Color;
import client.model.player.Player;
import org.junit.Ignore;
import org.junit.Test;
import shared.locations.VertexDirection;

public class GameManagerTests {

    @Test
    public void testParseGameModel_itSetsUpTheBankCorrectly() {
        String json = "{\"id\": 1, \"bank\": { \"brick\": 5, \"ore\": 5, \"sheep\": 5, \"wheat\": 5, \"wood\": 5 }}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getBank());
    }

    @Test
    public void testParseGameModel_itSetsUpTheChatCorrectly() {
        String json = "{\"id\": 1, \"chat\": { \"lines\": [ { \"message\": \"A message\", \"source\": 1 } ] } }";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getChat());
        assertNotNull(game.getChat().getLines());
    }

    @Test
    public void testParseLog_theLogAndLinesAreNotNull() {
        String json = "{id: 1, log: { lines: [ { message: \"A message\", source: 1 } ] } }";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getLog());
        assertNotNull(game.getLog().getLines());
    }

    @Test
    public void testParseMap_theMapItselfIsNotNull() {
        String json = "{id: 1, map: {}}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getMap());
    }

    @Test
    public void testParseMap_theHexesSetUpCorrectly() {
        String json = "{id: 1, map: {hexes: [ {location: {x: 1, y: 1}, resource: \"wheat\", number: 1} ]}}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getMap());
        assertNotNull(game.getMap().getHexes());
        assertEquals(game.getMap().getHexes().size(), 1);
        assertNotNull(game.getMap().getHexes().get(0).getLocation());
        assertTrue(game.getMap().getHexes().get(0).getResource() == ResourceType.wheat);
        assertEquals(game.getMap().getHexes().get(0).getNumber(), 1);
    }

    @Test
    public void testParseMap_thePortsSetUpCorrectly() {
        String json = "{id: 1, map: {ports: [ { resource: \"wheat\", location: {x: 1, y: 1}, direction: \"NorthEast\", ratio: 1} ]}}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getMap());
        assertNotNull(game.getMap().getPorts());
        assertEquals(game.getMap().getPorts().size(), 1);
        assertTrue(game.getMap().getPorts().get(0).getResource() == ResourceType.wheat);
        assertEquals(game.getMap().getPorts().get(0).getRatio(), 1);
    }

    @Test
    public void testParseMap_theRoadsSetUpCorrectly() {
        String json = "{id: 1, map: {roads: [ { owner: 0, location: {x: 1, y: 1, direction: \"NorthEast\"} } ]}}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getMap());
        assertNotNull(game.getMap().getRoads());
        assertEquals(game.getMap().getRoads().size(), 1);
        assertNotNull(game.getMap().getRoads().get(0).getLocation());
    }

    @Test
    public void testParseMap_theSettlementsSetUpCorrectly() {
        String json = "{id: 1, map: {settlements: [ { owner: 0, location: {x: 1, y: 1, direction: \"NorthEast\"} } ]}}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getMap());
        assertNotNull(game.getMap().getSettlements());
        assertEquals(game.getMap().getSettlements().size(), 1);
    }

    @Test
    public void testParseMap_theCitiesSetUpCorrectly() {
        String json = "{id: 1, map: {cities: [ { owner: 0, location: {x: 1, y: 1, direction: \"NorthEast\"} } ]}}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getMap());
        assertNotNull(game.getMap().getCities());
        assertEquals(game.getMap().getCities().size(), 1);
    }

    @Test
    public void testParseMap_theRobbersAndRadiusAreSet() {
        String json = "{id: 1, map: {radius: 5, robber: {x: 1, y: 1}}}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getMap());
        assertEquals(game.getMap().getRadius(), 5);
        assertNotNull(game.getMap().getRobber());
    }

    @Test
    public void testParsePlayer_itSetsUpThePlayer() {
        String json = "{id: 1, players: [ { cities: 1, color: \"red\", discarded: true, monuments: 1, name: \"John\", newDevCards: {}, oldDevCards: {}, playerIndex: 0, playedDevCard: true, playerID: 1, resources: {}, roads: 1, settlements: 1, soldiers: 1, victoryPoints: 1 } ]}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getPlayers().get(0));
        Player player = game.getPlayers().get(0);
        assertTrue(player.getName().equals("John"));
        assertTrue(player.getCitiesRemaining() == 1);
        assertTrue(player.getColor() == Color.red);
        assertTrue(player.getMonuments() == 1);
        assertNotNull(player.getNewDevCards());
        assertNotNull(player.getOldDevCards());
        assertTrue(player.getPlayerId() == 1);
        assertNotNull(player.getResources());
        assertTrue(player.getSoldiers() == 1);
        assertTrue(player.getVictoryPoints() == 1);
        assertTrue(player.getSettlements() == 1);
    }

    @Ignore
    @Test
    public void testParseFullGame () {
        String json = "{'deck': {'monument': 5, 'soldier': 14, 'roadBuilding': 2, 'monopoly': 2, 'yearOfPlenty': 2}, 'bank': {'brick': 24, 'ore': 24, 'wood': 24, 'wheat': 24, 'sheep': 24}, 'players': [{'oldDevCards': {'monument': 0, 'soldier': 0, 'roadBuilding': 0, 'monopoly': 0, 'yearOfPlenty': 0}, 'color': 'red', 'resources': {'brick': 0, 'ore': 0, 'wood': 0, 'wheat': 0, 'sheep': 0}, 'cities': 4, 'victoryPoints': 0, 'discarded': False, 'playedDevCard': False, 'playerID': 0, 'monuments': 0, 'newDevCards': {'monument': 0, 'soldier': 0, 'roadBuilding': 0, 'monopoly': 0, 'yearOfPlenty': 0}, 'roads': 15, 'settlements': 5, 'playerIndex': 0, 'name': 'Sam', 'soldiers': 0}, {'oldDevCards': {'monument': 0, 'soldier': 0, 'roadBuilding': 0, 'monopoly': 0, 'yearOfPlenty': 0}, 'color': 'red', 'resources': {'brick': 0, 'ore': 0, 'wood': 0, 'wheat': 0, 'sheep': 0}, 'cities': 4, 'victoryPoints': 0, 'discarded': False, 'playedDevCard': False, 'playerID': 12, 'monuments': 0, 'newDevCards': {'monument': 0, 'soldier': 0, 'roadBuilding': 0, 'monopoly': 0, 'yearOfPlenty': 0}, 'roads': 15, 'settlements': 5, 'playerIndex': 1, 'name': 'test', 'soldiers': 0}, None, None], 'winner': -1, 'map': {'robber': {'y': 0, 'x': -2}, 'radius': 3, 'ports': [{'resource': 'wheat', 'direction': 'NW', 'location': {'y': -1, 'x': 3}, 'ratio': 2}, {'direction': 'S', 'location': {'y': -3, 'x': 1}, 'ratio': 3}, {'resource': 'ore', 'direction': 'SW', 'location': {'y': -3, 'x': 3}, 'ratio': 2}, {'resource': 'brick', 'direction': 'NE', 'location': {'y': 3, 'x': -2}, 'ratio': 2}, {'resource': 'sheep', 'direction': 'N', 'location': {'y': 3, 'x': 0}, 'ratio': 2}, {'direction': 'S', 'location': {'y': -2, 'x': -1}, 'ratio': 3}, {'direction': 'NW', 'location': {'y': 1, 'x': 2}, 'ratio': 3}, {'direction': 'SE', 'location': {'y': 0, 'x': -3}, 'ratio': 3}, {'resource': 'wood', 'direction': 'NE', 'location': {'y': 2, 'x': -3}, 'ratio': 2}], 'roads': [], 'settlements': [], 'cities': [], 'hexes': [{'number': 9, 'resource': 'sheep', 'location': {'y': -2, 'x': 0}}, {'number': 4, 'resource': 'wood', 'location': {'y': -2, 'x': 1}}, {'number': 5, 'resource': 'brick', 'location': {'y': -2, 'x': 2}}, {'number': 5, 'resource': 'ore', 'location': {'y': -1, 'x': -1}}, {'number': 3, 'resource': 'wood', 'location': {'y': -1, 'x': 0}}, {'number': 6, 'resource': 'wood', 'location': {'y': -1, 'x': 1}}, {'number': 10, 'resource': 'sheep', 'location': {'y': -1, 'x': 2}}, {'location': {'y': 0, 'x': -2}}, {'number': 11, 'resource': 'wheat', 'location': {'y': 0, 'x': -1}}, {'number': 11, 'resource': 'wood', 'location': {'y': 0, 'x': 0}}, {'number': 10, 'resource': 'sheep', 'location': {'y': 0, 'x': 1}}, {'number': 12, 'resource': 'sheep', 'location': {'y': 0, 'x': 2}}, {'number': 6, 'resource': 'wheat', 'location': {'y': 1, 'x': -2}}, {'number': 8, 'resource': 'brick', 'location': {'y': 1, 'x': -1}}, {'number': 9, 'resource': 'ore', 'location': {'y': 1, 'x': 0}}, {'number': 2, 'resource': 'wheat', 'location': {'y': 1, 'x': 1}}, {'number': 4, 'resource': 'brick', 'location': {'y': 2, 'x': -2}}, {'number': 3, 'resource': 'ore', 'location': {'y': 2, 'x': -1}}, {'number': 8, 'resource': 'wheat', 'location': {'y': 2, 'x': 0}}]}, 'log': {'lines': []}, 'version': 0, 'turnTracker': {'status': 'FirstRound', 'currentTurn': 0, 'largestArmy': -1, 'longestRoad': -1}, 'chat': {'lines': []}}";
        GameManager.processGame(json);
    }

}
