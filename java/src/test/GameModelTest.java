import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import com.google.gson.*;

import shared.commands.GameModel;
import shared.communication.*;
import shared.deserializers.*;
import shared.communication.servers.*;
import shared.commands.*;
import client.server.*;
import client.model.*;
import client.model.player.*;
import shared.definitions.CatanColor;

public class GameModelTest {
    private ServerProxy server; 
    private Game game;
    private String response;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Ignore
    @Test
    public void test_GameModelMock() {
        this.server = new ServerFacade();
        String response = server.execute(new GameModel());
        String expected = "{}";
        assertEquals(expected, response);

    }
}
