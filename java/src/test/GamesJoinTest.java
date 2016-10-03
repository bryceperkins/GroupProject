import static org.junit.Assert.*;

import org.junit.Ignore;
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
    private static ServerProxy server; 
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

    @Ignore
    @Test
    public void test_GamesJoinMock() {
        CatanColor c = CatanColor.RED;
        this.server = new ServerProxy();
        this.response = server.execute(new GamesJoin(1, c));
        this.expected = "{\"id\":1,\"color\":\"red\"}";
        assertEquals(expected, response);

    }
}
