import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Ignore;

import shared.communication.*;
import shared.communication.servers.*;
import shared.commands.*;
import client.server.*;

public class GameListAITest {
    private User testUser;
    private ServerProxy server; 
    private Command command;

    @Before
    public void setUp() {
        this.server = new ServerProxy();
    }

    @After
    public void tearDown() {
        testUser = null;
        assertNull(testUser);
    }

    @Test
    public void test_GameListAI_expected_mock_json() {
        command = new GameListAI();
        String response = "['LARGEST_ARMY']";
        assertEquals(response, server.execute(command));
    }
}
