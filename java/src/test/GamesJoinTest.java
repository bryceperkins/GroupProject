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
    private CatanColor c;

    @Before
    public void setUp() {
        this.c = CatanColor.RED;
        this.server = new ServerProxy();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test_GamesJoinMock() {
        this.response = server.execute(new GamesJoin(1, this.c));
        this.expected = "Success";
        assertEquals(expected, response);
    }
}
