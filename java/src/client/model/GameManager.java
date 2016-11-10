package client.model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Set;
import java.util.HashSet;
import client.data.PlayerInfo;
import client.model.*;
import client.server.ServerProxy;
import client.model.player.Player;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import shared.communication.User;

/**
 * Facade class which statically manages the client's games and updates
 * the model based on the results from the server
 */
public class GameManager extends Observable{

    private static final GameManager INSTANCE = new GameManager();
    private ServerProxy server;
    private ArrayList<Game> games = new ArrayList<>();
    private Set activeUsers = new HashSet();
    private PlayerInfo playerInfo;
    private Poller poller;
    private int activeGameIndex = -1;

    private GameManager () {}

    private Game createGame(String json) {
        Gson gson = new Gson();
        JsonElement jsonElement = new JsonParser().parse(json);
        Game game = gson.fromJson(jsonElement, Game.class);
        game.postDeserializationSetup(game);
        return game;
    }

    public void setServer(ServerProxy server){
        this.server = server;
    }

    public Poller getPoller(){
        return poller;
    }

    public void setPoller(Poller p){
        this.poller = p;
    }

    public ServerProxy getServer(){
        return server;
    }

    public boolean Login(User user){
        return false;
    }

    public boolean Register(User user){
        return false;
    }

    public Set gamesList(){
        Set games = new HashSet();
        return games;
    }

    /**
     * Updates or creates a game on the client from the provided JSON
     * @pre a valid json object is given as reflected in the documentation
     * @post the game is updated or added if it did not previously exist
     * @param json JSON containing the Game model to update to
     */
    public void processGame(String json) {
        Game game = createGame(json);
        int index = gameIndex(game.getId());
        if (index >= 0) {
            games.set(index, game);
            activeGameIndex = index;
        } else {
            games.add(game);
            activeGameIndex = games.size() - 1;
            System.out.println("added new game");
        }
    }

    /**
     * @return The model of the game active on the client, or null if no game is active
     */
    public Game getActiveGame() {
        return getGame(activeGameIndex);
    }
    
    public int getActiveGameIndex() {
        return activeGameIndex;
    }
    
    public void setActiveGame(int id) {
        activeGameIndex = id;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public Player getActivePlayer() {
        Game game = getActiveGame();
        return (game == null) ? null : game.getPlayer(getActivePlayerIndex());
    }

    public Game getGame(int id) {
        int index = gameIndex(id);
        return (index < 0) ? null : games.get(index);
    }

    public void addGame(Game game) {
        if(game.getId() >= games.size()){
            games.add(game.getId(), game);
        }
        else{
            games.set(game.getId(), game);
        }
    }

    private int gameIndex(int id) {
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).getId() == id) {
                return i;
            }
        }

        return -1;
    }

    /**
     * @return the current player's index or NULL when user is not yet signed in
     */
    public PlayerIndex getActivePlayerIndex() {
        return playerInfo.getPlayerIndex();
    }

    public PlayerInfo getCurrentPlayerInfo() {
        return this.playerInfo;
    }

    public void updatePlayerInfo() {
        this.playerInfo = this.server.getServer().getDetails().toPlayerInfo();
    }

    public static GameManager getInstance() {
        return INSTANCE;
    }

    public void update(){
        setChanged();
        notifyObservers();
    }
}
