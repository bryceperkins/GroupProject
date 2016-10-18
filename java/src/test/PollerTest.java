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
import shared.definitions.CatanColor;

public class PollerTest {
    private Poller poller;
    private ServerProxy server; 
    private Game game;
    private String response;
    private String username = "Sam";

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test_Poller_mock() {
        this.server = new ServerProxy();
        Poller p = new Poller(this.server); 
        p.setCommand(new GameModel());
        p.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        p.interrupt();
        assertEquals(3, p.getCount());
    }
/*
    @Ignore("Live Test")  @Test
    public void test_Poller_Live() {
        this.server = new ServerProxy("localhost", "8081");
        this.server.execute(new UserLogin(username, "sam"));
        this.response = this.server.execute(new GamesList());
        
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(this.response).getAsJsonArray();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Game.class, new GamesCreateDeserializer());
        Gson gson = gsonBuilder.create();

        boolean found = false;
        for (int i=0; i < array.size(); i++){
            game = gson.fromJson(array.get(i), Game.class);
            if (game.getPlayers().size() < 4){
                found = true;
                this.server.execute(new GamesJoin(game.getId(), CatanColor.RED)); 
            }
        }

        if (found != true) {
            return;
        }

        this.response = this.server.execute(new GameModel());
        Poller p = new Poller(this.server); 
        p.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        p.interrupt();
    }
*/
}
