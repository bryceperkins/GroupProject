package client.model;

import client.data.*;
import java.util.List;
import client.model.map.*;
import client.model.player.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Game model class containing all local information provided by
 * the server for a given game.
 */
public class Game implements PostProcessor {

    private String name;
    private int version;
    private int id;
    private PlayerIndex winner;
    private ResourceList bank;
    private Chat chat;
    private Log log;
    private Map map;
    private List<Player> players;
    private TurnTracker turnTracker;
    private TradeOffer tradeOffer;
    
    public Game(){ 
        //name = "Test";
        //version = 1;
        //winner = PlayerIndex.None;
        //bank = new ResourceList(19, 19, 19, 19, 19);
        chat = new Chat();
        log = new Log();
        map = new Map();
        players = new ArrayList<Player>();
        turnTracker = new TurnTracker();
        tradeOffer = new TradeOffer(); 
    }

    /**
     * @return whether the game is completed
     */
    public boolean gameOver() {
        if (winner == PlayerIndex.None){
            return false;
        }
        return true;
    }

    /**
     * @return whether the game is ready to begin
     */
    public boolean canBeginGame() {
        if (players.size() < 4){
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public int getVersion() {
        return version;
    }

    public PlayerIndex getWinner() {
        return winner;
    }

    public void setWinner(PlayerIndex index){
        this.winner = index;
    }
    
    public ResourceList getBank() {
        return bank;
    }

    public Chat getChat() {
        return chat;
    }

    public Log getLog() {
        return log;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(PlayerIndex id) { return players.get(id.getIndex()); }

    public TurnTracker getTurnTracker() {
        return turnTracker;
    }

    public TradeOffer getTradeOffer() {
        return tradeOffer;
    }

    @Override
    public void postDeserializationSetup(Game game) {
        map.postDeserializationSetup(game);
    }

    public GameInfo toGameInfo(){
        GameInfo game = new GameInfo();
        game.setId(this.id);
        game.setTitle(this.name);
        for(Player player: getPlayers()){
            game.addPlayer(player.toPlayerInfo());
        }
        return game;
    }
}
