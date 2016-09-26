import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import com.google.gson.*;

import shared.communication.*;
import shared.deserializers.*;
import shared.communication.servers.*;
import shared.commands.*;
import client.server.*;
import client.model.*;

public class GamesListTest {
    private User testUser;
    private ServerFacade server; 
    private Command command;
    private Game game;

    @Before
    public void setUp() {
        this.server = new ServerFacade();
    }

    @After
    public void tearDown() {
        testUser = null;
        assertNull(testUser);
    }

    @Test
    public void test_GamesList_live() {
        this.server = new ServerFacade("localhost", "8081");
        Command login = new UserLogin("bob", "bob");
        command = new GamesList();
        String response = server.execute(command);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Game.class, new DeGame());
        Gson gson = gsonBuilder.create();

        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(response).getAsJsonArray();
        game = gson.fromJson(array.get(0), Game.class);
        assertEquals(4, game.getPlayers().size());
    }
}
