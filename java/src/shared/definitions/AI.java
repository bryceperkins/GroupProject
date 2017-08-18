package shared.definitions;

import server.facades.MovesFacade;
import shared.communication.User;
import shared.locations.*;
import shared.model.*;
import shared.model.map.Hex;
import shared.model.map.ItemLocation;
import shared.model.map.Map;
import shared.model.player.Player;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.io.Serializable;

public class AI extends Player implements Serializable{

    private transient MovesFacade facade;
    private transient User user;
    private transient boolean hasPlayed = false;

    public AI(int gameId, CatanColor color, String name, PlayerIndex playerIndex) {
        super(color, name, 24601, playerIndex, 24601);
        setUp(gameId, name);

    }

    private void rollNumber() {
        Random rand = new Random();
        int die1 = rand.nextInt(6) + 1;
        int die2 = rand.nextInt(6) + 1;
        int rollResult = die1 + die2;
        facade.rollNumber(getPlayerIndex().getIndex(), rollResult);
    }

    public boolean hasPlayed(){
        return hasPlayed;
    }
    
    public void setPlayed(){
        hasPlayed = true;
    }

    public void clearPlayed(){
        hasPlayed = false;
    }
    
    public void setUp(int id, String name){
        user = new User(name, "password");
        user.setPlayerID(24601);
        user.setGameID(id);
        facade = new MovesFacade(user);

    }

    private void placeRandomRoad(boolean free) {
        Map map = facade.getGame().getMap();
        if(!free && !canBuildRoad())
            return;
        for (Hex hex : map.getHexes()) {
            HexLocation hexlocation = hex.getLocation();
            for (EdgeDirection direction : EdgeDirection.values()) {
                EdgeLocation location = new EdgeLocation(hexlocation, direction);
                if (map.canBuildRoad(this, location, new State(facade.getGame().getTurnTracker().getStatus()))) {
                    facade.buildRoad(getPlayerIndex().getIndex(), free, location);
                    return;
                }
            }
        }
    }

    private void placeRobberRandomly() {
        Map map = facade.getGame().getMap();
        for (Hex hex : map.getHexes()) {
            if (map.canPlaceRobber(hex.getLocation())) {
                facade.robPlayer(getPlayerIndex().getIndex(), hex.getLocation(), 0);
                return;
            }
        }
    }

    private void placeRandomSettlement(boolean free) {
        Map map = facade.getGame().getMap();
        if(!free && !canBuildSettlement())
            return;
        for (Hex hex : map.getHexes()) {
            HexLocation hexlocation = hex.getLocation();
            for (VertexDirection direction : VertexDirection.values()) {
                ItemLocation location = new ItemLocation(hexlocation, direction);
                if (map.canBuildSettlement(this, location, new State(facade.getGame().getTurnTracker().getStatus()))) {
                    facade.buildSettlement(getPlayerIndex().getIndex(), free, new VertexLocation(hexlocation, direction));
                    return;
                }
            }
        }
    }
    
    private void placeRandomCity() {
        Map map = facade.getGame().getMap();
        for (Hex hex : map.getHexes()) {
            HexLocation hexlocation = hex.getLocation();
            for (VertexDirection direction : VertexDirection.values()) {
                ItemLocation location = new ItemLocation(hexlocation, direction);
                if (map.canBuildCity(this, location)) {
                    facade.buildCity(getPlayerIndex().getIndex(), new VertexLocation(hexlocation, direction));
                    return;
                }
            }
        }
    }

    private void discard() {
        facade.discardCards(getPlayerIndex().getIndex(), getResources());
    }

    private void finishTurn() {
        facade.finishTurn(getPlayerIndex().getIndex());
    }

    private void doStuff(){
        if (canBuildRoad())
            placeRandomRoad(false);
        if (canBuildSettlement())
            placeRandomSettlement(false);
        if (canBuildCity())
            placeRandomCity();
        if (canBuyDevCard())
            facade.buyDevCard(getPlayerIndex().getIndex());

        if (getResources().total() > 7){
            for (ResourceType r: ResourceType.values()){
                if (getResources().count(r) > 4){
                    if (getResources().count(ResourceType.WOOD) == 0)
                        facade.maritimeTrade(getPlayerIndex().getIndex(), 4, r, ResourceType.WOOD);
                    else if (getResources().count(ResourceType.BRICK) == 0)
                        facade.maritimeTrade(getPlayerIndex().getIndex(), 4, r, ResourceType.BRICK);
                    else if (getResources().count(ResourceType.SHEEP) == 0)
                        facade.maritimeTrade(getPlayerIndex().getIndex(), 4, r, ResourceType.SHEEP);
                    else if (getResources().count(ResourceType.WHEAT) == 0)
                        facade.maritimeTrade(getPlayerIndex().getIndex(), 4, r, ResourceType.WHEAT);
                    else if (getResources().count(ResourceType.ORE) == 0)
                        facade.maritimeTrade(getPlayerIndex().getIndex(), 4, r, ResourceType.ORE);
                }
            }
        }
        if (canBuildRoad())
            placeRandomRoad(false);
        if (canBuildSettlement())
            placeRandomSettlement(false);
        if (canBuildCity())
            placeRandomCity();
        if (canBuyDevCard())
            facade.buyDevCard(getPlayerIndex().getIndex());
        discard();
    }

    public void play() {
        Game game = facade.getGame();

        if (game.getTradeOffer() != null) {
            if (game.getTradeOffer().getReceiver() == getPlayerIndex()) {
                facade.acceptTrade(getPlayerIndex().getIndex(), false);
            }
        }

        if (game.getTurnTracker().getCurrentTurn() != getPlayerIndex()) {
            return;
        }

        TurnTracker.GameStatus status = game.getTurnTracker().getStatus();

        boolean free = true;

        switch (status) {
            case Rolling:
                rollNumber();
                break;
            case Playing:
                doStuff();
                finishTurn();
                break;
            case FirstRound:
            case SecondRound:
                placeRandomRoad(free);
                placeRandomSettlement(free);
                finishTurn();
                break;
            case Discarding:
                discard();
                break;
            case Robbing:
                placeRobberRandomly();
                break;
        }
    }

    @Override
    public int hashCode() {
        return 7 * getPlayerIndex().getIndex() + user.getGameID();
    }
}
