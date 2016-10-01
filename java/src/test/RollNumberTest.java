import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Ignore;

import shared.communication.*;
import shared.communication.servers.*;
import shared.commands.*;
import client.server.*;

public class RollNumberTest {
    private ServerFacade server; 
    private Command command;

    @Before
    public void setUp() {
        this.server = new ServerFacade();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test_RollNumber_mock() {
        command = new RollNumber(1, 1);
        String expected = "Success";
        assertEquals(expected, server.execute(command));
    }
}
