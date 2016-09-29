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

public class GamesJoinTest {
    private static ServerFacade server; 
    private Command command;
    private Game game;
    private String response;
    private String expected;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test_GamesJoinMock() {
        CatanColor c = CatanColor.RED;
        this.server = new ServerFacade();
        this.response = server.execute(new GamesJoin(1, c));
        this.expected = "{\"id\":1,\"color\":\"red\"}";
        assertEquals(expected, response);

    }

    @Test
    public void test_GamesJoinLive() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        CatanColor c = CatanColor.RED;

        gsonBuilder.registerTypeAdapter(Game.class, new GameDeserializer());
        Gson gson = gsonBuilder.create();

        this.server = new ServerFacade("localhost", "8081");
        this.server.execute(new UserLogin("Sam", "sam"));
        this.response = this.server.execute(new GamesJoin(3, c));

        assertEquals("Success", this.response);
    }
    
    @Test
    public void test_GamesJoinLive_multiple() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        CatanColor c = CatanColor.RED;

        gsonBuilder.registerTypeAdapter(Game.class, new GameDeserializer());
        Gson gson = gsonBuilder.create();

        this.server = new ServerFacade("localhost", "8081");
        this.server.execute(new UserLogin("Sam", "sam"));
        this.response = this.server.execute(new GamesJoin(3, c));

        assertEquals("Success", this.response);
    }
}
