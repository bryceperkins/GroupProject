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
import shared.definitions.CatanColor;

public class GamesJoinTest {
    private static ServerFacade server; 
    private Command command;
    private Game game;
    private String response;
    private String expected;
    private CatanColor c;

    @Before
    public void setUp() {
        this.c = CatanColor.RED;
        this.server = new ServerFacade("localhost", "8081");
        this.server.execute(new UserLogin("Sam", "sam"));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test_GamesJoinMock() {
        this.server = new ServerFacade();
        this.response = server.execute(new GamesJoin(1, this.c));
        this.expected = "Success";
        assertEquals(expected, response);

    }

    @Ignore("Live Test") @Test
    public void test_GamesJoinLive() {
        this.response = this.server.execute(new GamesJoin(3, this.c));
        assertEquals("Success", this.response);
    }
}
