import static org.junit.Assert.*;

import client.model.*;
import client.model.map.*;
import shared.definitions.ResourceType;
import shared.definitions.CatanColor;
import client.model.player.DevCardList;
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
        assertEquals(game.getBank().getBrick(), 5);
        assertEquals(game.getBank().getOre(), 5);
        assertEquals(game.getBank().getSheep(), 5);
        assertEquals(game.getBank().getWheat(), 5);
        assertEquals(game.getBank().getWood(), 5);
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
        String json = "{id: 1, map: {hexes: [ {location: {x: 1, y: 1}, resource: \"wheat\", number: 1} ], robber: {x: 1, y:1}}}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getMap());
        assertNotNull(game.getMap().getHexes());
        assertEquals(game.getMap().getHexes().size(), 1);
        Hex hex = game.getMap().getHexes().get(0);
        assertNotNull(hex.getLocation());
        assertEquals(hex.getLocation().getX(), 1);
        assertEquals(hex.getLocation().getY(), 1);
        assertTrue(hex.getResource() == ResourceType.WHEAT);
        assertEquals(hex.getNumber(), 1);
        assertTrue(hex.hasRobber());
    }

    @Test
    public void testParseMap_thePortsSetUpCorrectly() {
        String json = "{id: 1, map: {ports: [ { resource: \"wheat\", location: {x: 1, y: 1}, direction: \"NE\", ratio: 1} ]}}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getMap());
        assertNotNull(game.getMap().getPorts());
        assertEquals(game.getMap().getPorts().size(), 1);
        Port port = game.getMap().getPorts().get(0);
        assertTrue(port.getResource() == ResourceType.WHEAT);
        assertEquals(port.getRatio(), 1);
        assertEquals(port.getDirection(), HexDirection.NE);
        assertNotNull(port.getLocation());
        assertEquals(port.getLocation().getX(), 1);
        assertEquals(port.getLocation().getY(), 1);
    }

    @Test
    public void testParseMap_theRoadsSetUpCorrectly() {
        String json = "{id: 1, map: {roads: [ { owner: 0, location: {x: 1, y: 1, direction: \"NE\"} } ]}}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getMap());
        assertNotNull(game.getMap().getRoads());
        assertEquals(game.getMap().getRoads().size(), 1);
        Road road = game.getMap().getRoads().get(0);
        assertNotNull(road.getLocation());
        assertEquals(road.getLocation().getX(), 1);
        assertEquals(road.getLocation().getY(), 1);
        assertEquals(road.getLocation().getDirection(), VertexDirection.NorthEast);
    }

    @Test
    public void testParseMap_theSettlementsSetUpCorrectly() {
        String json = "{id: 1, map: {settlements: [ { owner: 0, location: {x: 1, y: 1, direction: \"NE\"} } ]}}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getMap());
        assertNotNull(game.getMap().getSettlements());
        assertEquals(game.getMap().getSettlements().size(), 1);
        Settlement settlement = game.getMap().getSettlements().get(0);
        assertEquals(settlement.getOwner(), PlayerIndex.Player1);
        assertNotNull(settlement.getLocation());
        assertEquals(settlement.getLocation().getX(), 1);
        assertEquals(settlement.getLocation().getY(), 1);
        assertEquals(settlement.getLocation().getDirection(), VertexDirection.NorthEast);
    }

    @Test
    public void testParseMap_theCitiesSetUpCorrectly() {
        String json = "{id: 1, map: {cities: [ { owner: 0, location: {x: 1, y: 1, direction: \"NE\"} } ]}}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getMap());
        assertNotNull(game.getMap().getCities());
        assertEquals(game.getMap().getCities().size(), 1);
        City city = game.getMap().getCities().get(0);
        assertEquals(city.getOwner(), PlayerIndex.Player1);
        assertNotNull(city.getLocation());
        assertEquals(city.getLocation().getX(), 1);
        assertEquals(city.getLocation().getY(), 1);
        assertEquals(city.getLocation().getDirection(), VertexDirection.NorthEast);
    }

    @Test
    public void testParseMap_theRobbersAndRadiusAreSet() {
        String json = "{id: 1, map: {radius: 5, robber: {x: 1, y: 1}}}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getMap());
        assertEquals(game.getMap().getRadius(), 5);
        assertNotNull(game.getMap().getRobber());
        assertEquals(game.getMap().getRobber().getX(), 1);
        assertEquals(game.getMap().getRobber().getY(), 1);
    }

    @Test
    public void testParsePlayer_itSetsUpThePlayer() {
        String json = "{id: 1, players: [ { cities: 1, color: \"red\", discarded: true, monuments: 1, name: \"John\", newDevCards: {monopoly: 1, monument: 1, roadBuilding: 1, soldiers: 1, yearOfPlenty: 1}, oldDevCards: {monopoly: 1, monument: 1, roadBuilding: 1, soldiers: 1, yearOfPlenty: 1}, playerIndex: 0, playedDevCard: true, playerID: 1, resources: {brick: 1, wood: 1, ore: 1, sheep: 1, wheat: 1}, roads: 1, settlements: 1, soldiers: 1, victoryPoints: 1 } ]}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getPlayers().get(0));
        Player player = game.getPlayers().get(0);
        assertTrue(player.getName().equals("John"));
        assertTrue(player.didDiscard());
        assertTrue(player.getCitiesRemaining() == 1);
        assertTrue(player.getColor() == CatanColor.RED);
        assertTrue(player.getMonumentsPlayed() == 1);
        assertNotNull(player.getNewDevCards());
        assertNotNull(player.getOldDevCards());
        assertEquals(player.getPlayerIndex(), PlayerIndex.Player1);
        assertTrue(player.getPlayerId() == 1);
        assertNotNull(player.getResources());
        assertTrue(player.isPlayedDevCard());
        assertTrue(player.getSoldiersPlayed() == 1);
        assertTrue(player.getVictoryPoints() == 1);
        assertTrue(player.getSettlementsRemaining() == 1);

        // Check nesting
    }

    @Test
    public void testParsePlayer_itSetsUpNewDevCards() {
        String json = "{id: 1, players: [ { newDevCards: {monopoly: 1, monument: 1, roadBuilding: 1, soldier: 1, yearOfPlenty: 1 }} ]}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getPlayers().get(0));
        DevCardList newDevCards = game.getPlayer(PlayerIndex.Player1).getNewDevCards();
        assertEquals(newDevCards.getMonopoly(), 1);
        assertEquals(newDevCards.getMonument(), 1);
        assertEquals(newDevCards.getRoadBuilding(), 1);
        assertEquals(newDevCards.getSoldier(), 1);
        assertEquals(newDevCards.getYearOfPlenty(), 1);
    }

    @Test
    public void testParsePlayer_itSetsUpOldDevCards() {
        String json = "{id: 1, players: [ { oldDevCards: {monopoly: 1, monument: 1, roadBuilding: 1, soldier: 1, yearOfPlenty: 1 }} ]}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getPlayers().get(0));
        DevCardList oldDevCards = game.getPlayer(PlayerIndex.Player1).getOldDevCards();
        assertEquals(oldDevCards.getMonopoly(), 1);
        assertEquals(oldDevCards.getMonument(), 1);
        assertEquals(oldDevCards.getRoadBuilding(), 1);
        assertEquals(oldDevCards.getSoldier(), 1);
        assertEquals(oldDevCards.getYearOfPlenty(), 1);
    }

    @Test
    public void testParsePlayer_itSetsUpResources() {
        String json = "{id: 1, players: [ { resources: { brick: 1, sheep: 1, wood: 1, ore: 1, wheat: 1 }} ]}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getPlayers().get(0));
        ResourceList resources = game.getPlayer(PlayerIndex.Player1).getResources();
        assertEquals(resources.getBrick(), 1);
        assertEquals(resources.getOre(), 1);
        assertEquals(resources.getSheep(), 1);
        assertEquals(resources.getWheat(), 1);
        assertEquals(resources.getWood(), 1);
    }

    @Test
    public void testParseGameModel_itSetsUpTheTradeOffer() {
        String json = "{id: 1, tradeOffer: { sender: 1, receiver: 2, offer: { brick: 1, sheep: 1, wood: 1, ore: 1, wheat: 1 }}}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getTradeOffer());
        TradeOffer tradeOffer = game.getTradeOffer();
        assertEquals(tradeOffer.getSender(), PlayerIndex.Player2);
        assertEquals(tradeOffer.getReceiver(), PlayerIndex.Player3);
        assertNotNull(tradeOffer.getOffer());
        ResourceList resources = tradeOffer.getOffer();
        assertEquals(resources.getBrick(), 1);
        assertEquals(resources.getOre(), 1);
        assertEquals(resources.getSheep(), 1);
        assertEquals(resources.getWheat(), 1);
        assertEquals(resources.getWood(), 1);
    }

    @Test
    public void testParseGameModel_itSetsUpTheTurnTracker() {
        String json = "{id: 1, turnTracker: { currentTurn: 0, status: 'FirstRound', longestRoad: 1, longestArmy: 1 }}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getTurnTracker());
        TurnTracker turnTracker = game.getTurnTracker();
        assertEquals(turnTracker.getStatus(), TurnTracker.GameStatus.FirstRound);
        assertEquals(turnTracker.getLargestArmyOwner(), PlayerIndex.Player2);
        assertEquals(turnTracker.getLongestRoadOwner(), PlayerIndex.Player2);
        assertEquals(turnTracker.getCurrentTurn(), PlayerIndex.Player1);
    }

    @Ignore
    @Test
    public void testParseFullGame () {
        String json = "{'deck': {'monument': 5, 'soldier': 14, 'roadBuilding': 2, 'monopoly': 2, 'yearOfPlenty': 2}, 'bank': {'brick': 24, 'ore': 24, 'wood': 24, 'wheat': 24, 'sheep': 24}, 'players': [{'oldDevCards': {'monument': 0, 'soldier': 0, 'roadBuilding': 0, 'monopoly': 0, 'yearOfPlenty': 0}, 'color': 'red', 'resources': {'brick': 0, 'ore': 0, 'wood': 0, 'wheat': 0, 'sheep': 0}, 'cities': 4, 'victoryPoints': 0, 'discarded': False, 'playedDevCard': False, 'playerID': 0, 'monuments': 0, 'newDevCards': {'monument': 0, 'soldier': 0, 'roadBuilding': 0, 'monopoly': 0, 'yearOfPlenty': 0}, 'roads': 15, 'settlements': 5, 'playerIndex': 0, 'name': 'Sam', 'soldiers': 0}, {'oldDevCards': {'monument': 0, 'soldier': 0, 'roadBuilding': 0, 'monopoly': 0, 'yearOfPlenty': 0}, 'color': 'red', 'resources': {'brick': 0, 'ore': 0, 'wood': 0, 'wheat': 0, 'sheep': 0}, 'cities': 4, 'victoryPoints': 0, 'discarded': False, 'playedDevCard': False, 'playerID': 12, 'monuments': 0, 'newDevCards': {'monument': 0, 'soldier': 0, 'roadBuilding': 0, 'monopoly': 0, 'yearOfPlenty': 0}, 'roads': 15, 'settlements': 5, 'playerIndex': 1, 'name': 'test', 'soldiers': 0}, None, None], 'winner': -1, 'map': {'robber': {'y': 0, 'x': -2}, 'radius': 3, 'ports': [{'resource': 'wheat', 'direction': 'NW', 'location': {'y': -1, 'x': 3}, 'ratio': 2}, {'direction': 'S', 'location': {'y': -3, 'x': 1}, 'ratio': 3}, {'resource': 'ore', 'direction': 'SW', 'location': {'y': -3, 'x': 3}, 'ratio': 2}, {'resource': 'brick', 'direction': 'NE', 'location': {'y': 3, 'x': -2}, 'ratio': 2}, {'resource': 'sheep', 'direction': 'N', 'location': {'y': 3, 'x': 0}, 'ratio': 2}, {'direction': 'S', 'location': {'y': -2, 'x': -1}, 'ratio': 3}, {'direction': 'NW', 'location': {'y': 1, 'x': 2}, 'ratio': 3}, {'direction': 'SE', 'location': {'y': 0, 'x': -3}, 'ratio': 3}, {'resource': 'wood', 'direction': 'NE', 'location': {'y': 2, 'x': -3}, 'ratio': 2}], 'roads': [], 'settlements': [], 'cities': [], 'hexes': [{'number': 9, 'resource': 'sheep', 'location': {'y': -2, 'x': 0}}, {'number': 4, 'resource': 'wood', 'location': {'y': -2, 'x': 1}}, {'number': 5, 'resource': 'brick', 'location': {'y': -2, 'x': 2}}, {'number': 5, 'resource': 'ore', 'location': {'y': -1, 'x': -1}}, {'number': 3, 'resource': 'wood', 'location': {'y': -1, 'x': 0}}, {'number': 6, 'resource': 'wood', 'location': {'y': -1, 'x': 1}}, {'number': 10, 'resource': 'sheep', 'location': {'y': -1, 'x': 2}}, {'location': {'y': 0, 'x': -2}}, {'number': 11, 'resource': 'wheat', 'location': {'y': 0, 'x': -1}}, {'number': 11, 'resource': 'wood', 'location': {'y': 0, 'x': 0}}, {'number': 10, 'resource': 'sheep', 'location': {'y': 0, 'x': 1}}, {'number': 12, 'resource': 'sheep', 'location': {'y': 0, 'x': 2}}, {'number': 6, 'resource': 'wheat', 'location': {'y': 1, 'x': -2}}, {'number': 8, 'resource': 'brick', 'location': {'y': 1, 'x': -1}}, {'number': 9, 'resource': 'ore', 'location': {'y': 1, 'x': 0}}, {'number': 2, 'resource': 'wheat', 'location': {'y': 1, 'x': 1}}, {'number': 4, 'resource': 'brick', 'location': {'y': 2, 'x': -2}}, {'number': 3, 'resource': 'ore', 'location': {'y': 2, 'x': -1}}, {'number': 8, 'resource': 'wheat', 'location': {'y': 2, 'x': 0}}]}, 'log': {'lines': []}, 'version': 0, 'turnTracker': {'status': 'FirstRound', 'currentTurn': 0, 'largestArmy': -1, 'longestRoad': -1}, 'chat': {'lines': []}}";
        GameManager.processGame(json);
    }

}
