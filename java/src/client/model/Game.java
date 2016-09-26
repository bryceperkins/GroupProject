package client.model;

import java.util.HashMap;

import shared.definitions.*;
import shared.locations.*;
import client.model.map.*;
import client.model.player.*;

/**
 * Game model class containing all local information provided by
 * the server for a given game.
 */
public class Game {

    private String name;
    private int version;
    private PlayerIndex winner;
    private ResourceBank bank;
    private Chat chat;
    private Log log;
    private Map map;
    private HashMap<String, Player> players;
    private TurnTracker turnTracker;
    private TradeOffer tradeOffer;

    /**
     * @return whether the game is completed
     */
    public boolean gameOver() {
        return true;
    }

    /**
     * @return whether the game is ready to begin
     */
    public boolean canBeginGame() {
        return true;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public PlayerIndex getWinner() {
        return winner;
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

    public void setPlayers(HashMap<String, Player> players) {
        this.players = players;
    }

    public HashMap<String, Player> getPlayers() {
        return players;
    }

    public TurnTracker getTurnTracker() {
        return turnTracker;
    }

    public TradeOffer getTradeOffer() {
        return tradeOffer;
    }


}
