import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Ignore;

import shared.communication.*;
import shared.communication.servers.*;
import shared.commands.*;
import client.server.*;

public class GameAddAITest {
    private User testUser;
    private ServerFacade server; 
    private Command command;

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
    public void test_GameAddAI_expected_mock_json() {
        command = new GameAddAI("LARGEST_ARMY");
        String response = "Success";
        assertEquals(response, server.execute(command));
    }
}
