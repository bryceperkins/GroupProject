package client.model;

import java.util.*;
import com.google.gson.*;
import shared.commands.GameModel;
import shared.commands.GamesList;
import shared.deserializers.GamesCreateDeserializer;
import client.server.*;

public class Poller extends Thread {
    private ServerProxy server;
    /**
     * Polls the Server on a regular basis, retrieving a new model.
     */
    private int interval;
    private int version = 0;
    private int count = 0;
    private String response;
    private iCommand command = new GamesList();
    private GameManager manager = GameManager.getInstance();

    public Poller(ServerProxy server){
        setServer(server);
    }
    public void setVersion(int version) {
        this.version = version;
    }
    public void setServer(ServerProxy server) {
        this.server = server;
    }
    public void setCommand(iCommand command) {
        this.command = command;
    }
    public int getCount(){
        return this.count;
    }
    public void run() {
        try {
            poll();
        } catch (InterruptedException e) { }
    }
    public String poll() throws InterruptedException{
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Game.class, new GamesCreateDeserializer());
        Gson gson = gsonBuilder.create();
        String new_response = "";

        while (true) {
            count++;
            new_response = this.server.execute(command);
            if (this.manager.getServer() == null){ }
            else if (this.command.getEndPoint().equals("/game/model")){
                this.manager.processGame(new_response);
            }
            else {
                JsonArray gamesArray = new JsonParser().parse(new_response).getAsJsonArray();
                for (int i=0; i < gamesArray.size(); i++){
                    Game game = gson.fromJson(gamesArray.get(i), Game.class);
                    manager.addGame(game);
                }
            }
            if (!new_response.equals(this.response)){
                this.response = new_response;
                manager.update();
            }
            this.sleep(2000);
        }
    }
}
