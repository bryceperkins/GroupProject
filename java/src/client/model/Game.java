package client.model;

import java.util.HashMap;

import shared.definitions.*;
import shared.locations.*;
import client.model.map.*;
import client.model.player.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Game model class containing all local information provided by
 * the server for a given game.
 */
public class Game {

    private String name;
    private int version;
    private int id;
    private PlayerIndex winner;
    private ResourceBank bank;
    private Chat chat;
    private Log log;
    private Map map;
    private List<Player> players;
    private TurnTracker turnTracker;
    private TradeOffer tradeOffer;
    
    public Game(){
        name = "Test";
        version = 1;
        winner = PlayerIndex.None;
        bank = new ResourceBank();
        chat = new Chat();
        log = new Log();
        map = new Map();
        players = new ArrayList<Player>();
        turnTracker = new TurnTracker();
        tradeOffer = new TradeOffer();
        System.out.println("In Game Constructor");
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
    
    public ResourceBank getBank() {
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

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public TurnTracker getTurnTracker() {
        return turnTracker;
    }

    public TradeOffer getTradeOffer() {
        return tradeOffer;
    }
    
    public void setVersion(int version) {
        this.version = version;
    }

    public void setBank(ResourceBank bank) {
        this.bank = bank;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setTurnTracker(TurnTracker turnTracker) {
        this.turnTracker = turnTracker;
    }

    public void setTradeOffer(TradeOffer tradeOffer) {
        this.tradeOffer = tradeOffer;
    }

}
