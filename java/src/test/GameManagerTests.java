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
        String json = "{id: 1, map: {ports: [ { resource: \"wheat\", location: {x: 1, y: 1}, direction: \"NW\", ratio: 1} ]}}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getMap());
        assertNotNull(game.getMap().getPorts());
        assertEquals(game.getMap().getPorts().size(), 1);
        assertNotNull(game.getMap().getPorts().get(0).getLocation());
        assertTrue(game.getMap().getPorts().get(0).getResource() == ResourceType.wheat);
        assertEquals(game.getMap().getPorts().get(0).getRatio(), 1);
        assertEquals(game.getMap().getPorts().get(0).getDirection(), Direction.NW);
    }

    @Test
    public void testParseMap_theRoadsSetUpCorrectly() {
        String json = "{id: 1, map: {roads: [ { owner: 0, location: {x: 1, y: 1, direction: \"NW\"} } ]}}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getMap());
        assertNotNull(game.getMap().getRoads());
        assertEquals(game.getMap().getRoads().size(), 1);
        assertNotNull(game.getMap().getRoads().get(0).getLocation());
        assertTrue(game.getMap().getRoads().get(0).getLocation().getDirection() == Direction.NW);
    }

    @Test
    public void testParseMap_theSettlementsSetUpCorrectly() {
        String json = "{id: 1, map: {settlements: [ { owner: 0, location: {x: 1, y: 1, direction: \"NW\"} } ]}}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getMap());
        assertNotNull(game.getMap().getSettlements());
        assertEquals(game.getMap().getSettlements().size(), 1);
        assertNotNull(game.getMap().getSettlements().get(0).getLocation());
        assertTrue(game.getMap().getSettlements().get(0).getLocation().getDirection() == Direction.NW);
    }

    @Test
    public void testParseMap_theCitiesSetUpCorrectly() {
        String json = "{id: 1, map: {cities: [ { owner: 0, location: {x: 1, y: 1, direction: \"NW\"} } ]}}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getMap());
        assertNotNull(game.getMap().getCities());
        assertEquals(game.getMap().getCities().size(), 1);
        assertNotNull(game.getMap().getCities().get(0).getLocation());
        assertTrue(game.getMap().getCities().get(0).getLocation().getDirection() == Direction.NW);
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

}
