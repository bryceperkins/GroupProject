import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Ignore;

import shared.communication.*;
import shared.communication.servers.*;
import shared.commands.*;
import client.server.*;

public class UserLoginTest {
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
    public void test_UserLogin_expected_mock_json() {
        command = new UserLogin("test", "test");
        String response = "Success";
        assertEquals(response, server.execute(command));
    }
    
<<<<<<< c63de33e0f183ef30c5b5786d3a7975844b7e163
    @Ignore("Live Test")  @Test
    public void test_UserLogin_live() {
        this.server = new ServerFacade("localhost", "8081");
        command = new UserLogin("Sam", "sam");
        assertEquals("Success", server.execute(command));
    }
=======
>>>>>>> did a lot of stuff
}
