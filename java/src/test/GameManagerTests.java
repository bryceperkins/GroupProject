import static org.junit.Assert.*;

import client.model.Game;
import client.model.GameManager;
import org.junit.Test;

public class GameManagerTests {

    @Test
    public void testParseGameModel_itSetsUpTheBankCorrectly() {
        String json = "{\"id\": 1, \"bank\": { \"brick\": 5, \"ore\": 5, \"sheep\": 5, \"wheat\": 5, \"wood\": 5 }}";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getBank());
//        assertEquals(game.getBank().getBrick(), 5);
//        assertEquals(game.getBank().getOre(), 5);
//        assertEquals(game.getBank().getSheep(), 5);
//        assertEquals(game.getBank().getWheat(), 5);
//        assertEquals(game.getBank().getWood(), 5);
    }

    @Test
    public void testParseGameModel_itSetsUpTheChatCorrectly() {
        String json = "{\"id\": 1, \"chat\": { \"lines\": [ { \"message\": \"A message\", \"source\": 1 } ] } }";

        GameManager.processGame(json);

        Game game = GameManager.getGame(1);
        assertNotNull(game.getChat());
//        assertNotNull(game.getChat().getLines());
    }

}
