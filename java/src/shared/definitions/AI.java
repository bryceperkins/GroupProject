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

public class AI extends Player {

    private transient MovesFacade facade;
    private transient User user;

    public AI(int gameId, CatanColor color, String name, PlayerIndex playerIndex) {
        super(color, name, 24601, playerIndex, 24601);

        user = new User(name, "password");
        user.setPlayerID(24601);
        user.setGameID(gameId);

        facade = new MovesFacade(user);
    }

    private void rollNumber() {
        Random rand = new Random();
        int die1 = rand.nextInt(6) + 1;
        int die2 = rand.nextInt(6) + 1;
        int rollResult = die1 + die2;
        if (rollResult == 7){
            rollResult = 8;
        }
        facade.rollNumber(getPlayerIndex().getIndex(), rollResult);
    }

    private void placeRandomRoad() {
        Map map = facade.getGame().getMap();
        for (Hex hex : map.getHexes()) {
            HexLocation hexlocation = hex.getLocation();
            for (EdgeDirection direction : EdgeDirection.values()) {
                EdgeLocation location = new EdgeLocation(hexlocation, direction);
                if (map.canBuildRoad(this, location, new State(facade.getGame().getTurnTracker().getStatus()))) {
                    facade.buildRoad(getPlayerIndex().getIndex(), true, location);
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

    private void placeRandomSettlement() {
        Map map = facade.getGame().getMap();
        for (Hex hex : map.getHexes()) {
            HexLocation hexlocation = hex.getLocation();
            for (VertexDirection direction : VertexDirection.values()) {
                ItemLocation location = new ItemLocation(hexlocation, direction);
                if (map.canBuildSettlement(this, location, new State(facade.getGame().getTurnTracker().getStatus()))) {
                    facade.buildSettlement(getPlayerIndex().getIndex(), true, new VertexLocation(hexlocation, direction));
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

    public void play() {
        Game game = facade.getGame();
        if (game.getTurnTracker().getCurrentTurn() != getPlayerIndex()) {
            return;
        }

        TurnTracker.GameStatus status = game.getTurnTracker().getStatus();

        if (game.getTradeOffer() != null) {
            if (game.getTradeOffer().getReceiver() == getPlayerIndex()) {
                facade.acceptTrade(getPlayerIndex().getIndex(), false);
            }
        }

        switch (status) {
            case Rolling:
                rollNumber();
                break;
            case FirstRound:
            case SecondRound:
                placeRandomRoad();
                placeRandomSettlement();
                finishTurn();
                break;
            case Discarding:
                discard();
                break;
            case Playing:
                finishTurn();
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
