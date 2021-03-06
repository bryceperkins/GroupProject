package shared.model;

import java.util.*;

import client.data.PlayerInfo;
import client.server.ServerProxy;
import shared.definitions.AI;
import shared.model.player.Player;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import shared.communication.User;
import client.*;
import server.persistance.Persistor;
import server.persistance.UserDAO;
import server.persistance.GameDAO;

/**
 * Facade class which statically manages the client's games and updates
 * the model based on the results from the server
 */
public class GameManager extends Observable{

    private static final GameManager INSTANCE = new GameManager();
    private Persistor persistor = Persistor.getInstance();
    private UserDAO ud;
    private GameDAO gd;
    private ServerProxy server;
    private List<Game> games = new ArrayList<Game>();
    private PlayerInfo playerInfo;
    private Poller poller;
    private int activeGameIndex = -1;
    private GameController gameController = new GameController();
    private HashMap<String, User> users = new HashMap();
    private int checkpoint = 10;

    private GameManager () {}

    public void loadAll(){
        ud = persistor.getUserDAO();
        gd = persistor.getGameDAO();
        users = ud.getUsers();
        games = gd.getGames();
        for(Game game: games){
            game.setUp(10);
            game.getCommands();
        }
    }

    public HashMap<String, User> getUsers(){
        return users;
    }

    public int login(User user){
        if (users.get(user.getUserName()).equals(user)){
            return users.get(user.getUserName()).getPlayerID();
        }
        return -1;
    }

    public User register(User user){
        if (users.containsKey(user.getUserName())){
            return null;
        }
        user.setPlayerID(users.size());
        users.put(user.getUserName(), user);
        ud.addUser(user);
        return user;
    }

    private Game createGame(String json) {
        Gson gson = new Gson();
        JsonElement jsonElement = new JsonParser().parse(json);
        Game game = gson.fromJson(jsonElement, Game.class);
        game.setCheckpoint(checkpoint);
        game.postDeserializationSetup(game);
        return game;
    }

    public void setCheckpoint(int checkpoint){
        this.checkpoint = checkpoint;
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

    public List<Game> getGames() {
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

    public void saveGame(Game game){
        gd.addGame(game);
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
        Game game = getActiveGame();
        return (game == null) ? null : game.getPlayerByName(playerInfo.getName()).getPlayerIndex();
    }

    public PlayerInfo getCurrentPlayerInfo() {
        PlayerInfo p = this.playerInfo;
        Game g = getActiveGame();
        if (g != null){
            p = g.getPlayerByName(p.getName()).toPlayerInfo();
        }
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
        notifyObservers(getActiveGame());
    }
}
