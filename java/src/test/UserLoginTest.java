import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

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
        testUser = new User("test", "test");
        this.server = new ServerFacade();
    }

    @After
    public void tearDown() {
        testUser = null;
        assertNull(testUser);
    }

    @Test
    public void test_UserLogin_expected_mock_json() {
        command = new UserLogin(testUser);
        String response = "{\"username\":\"test\",\"password\":\"test\"}";
        assertEquals(response, server.execute(command));
    }
    
    @Test
    public void test_UserLogin_live() {
        this.server = new ServerFacade("localhost", "8081");
        command = new UserLogin(new User("Sam", "sam"));
        assertEquals("Success", server.execute(command));
    }
}
