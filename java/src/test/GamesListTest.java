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

public class GamesListTest {
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
	public void true_test(){
		assertTrue(true);
	}
    @Test
    public void test_GamesListMock() {
        this.server = new ServerFacade();
        command = new GamesList();
        String response = server.execute(command);
        String expected = "[{\"title\":\"Default Game\",\"id\":0,\"players\":[{\"color\":\"red\",\"name\":\"Sam\",\"id\":0},{\"color\":\"blue\",\"name\":\"Brooke\",\"id\":1},{\"color\":\"red\",\"name\":\"Pete\",\"id\":10},{\"color\":\"green\",\"name\":\"Mark\",\"id\":11}]}]";
        assertEquals(expected, response);

    }
<<<<<<< c63de33e0f183ef30c5b5786d3a7975844b7e163
/*
    @Ignore("Live Test")  @Test
    public void test_GamesListLive() {
        this.server = new ServerFacade("localhost", "8081");
        Command login = new UserLogin("bob", "bob");
        command = new GamesList();
        String response = server.execute(command);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Game.class, new GamesCreateDeserializer());
        Gson gson = gsonBuilder.create();

        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(response).getAsJsonArray();
        assertTrue(array.size() > 0);
    }
*/
=======
>>>>>>> did a lot of stuff
}
