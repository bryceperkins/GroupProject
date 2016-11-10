import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import shared.communication.servers.*;
import shared.commands.*;
import client.server.*;
import shared.model.*;

public class GamesListTest {
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
	public void true_test(){
		assertTrue(true);
	}
    @Test
    public void test_GamesListMock() {
        this.server = new ServerProxy();
        command = new GamesList();
        String response = server.execute(command);
        String expected = "[{\"title\":\"Default Game\",\"id\":0,\"players\":[{\"color\":\"red\",\"name\":\"Sam\",\"id\":0},{\"color\":\"blue\",\"name\":\"Brooke\",\"id\":1},{\"color\":\"red\",\"name\":\"Pete\",\"id\":10},{\"color\":\"green\",\"name\":\"Mark\",\"id\":11}]}]";
        assertEquals(expected, response);

    }
}
