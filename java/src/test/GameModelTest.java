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
import shared.definitions.CatanColor;

public class GameModelTest {
    private ServerFacade server; 
    private Game game;
    private String response;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test_GameModelMock() {
        this.server = new ServerFacade();
        String response = server.execute(new GameModel());
        String expected = "{}";
        assertEquals(expected, response);

    }

    @Test
    public void test_GameModelLive() {
        this.server = new ServerFacade("localhost", "8081");
        this.server.execute(new UserLogin("Sam", "sam"));
        this.server.execute(new GamesJoin(3, CatanColor.RED));
        String response = server.execute(new GameModel());

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Game.class, new GameModelDeserializer());
        Gson gson = gsonBuilder.create();

        game = gson.fromJson(response, Game.class);
        assertEquals(4, game.getPlayers().size());
    }
}
