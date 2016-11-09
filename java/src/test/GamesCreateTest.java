import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import shared.communication.servers.*;
import shared.commands.*;
import client.server.*;
import shared.model.*;

public class GamesCreateTest {
    private ServerProxy server; 
    private Command command;
    private Game game;

    @Before
    public void setUp() {
        this.server = new ServerProxy();
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
