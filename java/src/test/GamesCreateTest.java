import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Ignore;

import com.google.gson.*;

import shared.communication.*;
import shared.deserializers.*;
import shared.communication.servers.*;
import shared.commands.*;
import client.server.*;
import client.model.*;

public class GamesCreateTest {
    private ServerFacade server; 
    private Command command;
    private Game game;

    @Before
    public void setUp() {
        this.server = new ServerFacade();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test_GamesCreateMock() {
        String response = server.execute(new GamesCreate("test", true, true, true));
        String expected = "{\"title\":\"test\",\"id\":34,\"players\" :[{},{},{},{}]}";
        assertEquals(expected, response);

    }
<<<<<<< c63de33e0f183ef30c5b5786d3a7975844b7e163

    @Ignore("Live Test")  @Test
    public void skip_test_GamesCreateLive() {
        this.server = new ServerFacade("localhost", "8081");
        Command login = new UserLogin("bob", "bob");
        String response = server.execute(new GamesCreate("test", true, true, true));

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Game.class, new GamesCreateDeserializer());
        Gson gson = gsonBuilder.create();

        JsonParser parser = new JsonParser();
        game = gson.fromJson(response, Game.class);
        assertEquals("test", game.getName());
    }
=======
>>>>>>> did a lot of stuff
}
