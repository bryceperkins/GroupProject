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
}
