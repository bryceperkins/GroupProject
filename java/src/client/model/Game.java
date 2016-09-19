package client.model;

import java.util.HashMap;

import shared.definitions.*;
import shared.locations.*;
import map.*;
import player.*;

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
    public boolean gameOver() {}

    /**
     * @return whether the game is ready to begin
     */
    public boolean canBeginGame() {}

    public String getName() {
        return name;
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
