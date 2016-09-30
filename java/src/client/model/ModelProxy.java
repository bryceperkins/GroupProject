package client.model;

import client.model.map.ItemLocation;
import client.model.player.Player;
import shared.communication.User;

import java.util.List;
import java.util.stream.Collectors;

public class ModelProxy {

    private final static int PLAYERS_NEEDED = 4;

    /**
     * @param gameIndex the index of the game in question
     * @return whether or not the game is ready to begin (if it has 4 players)
     */
    public static boolean canBeginGame(int gameIndex) {
        Game game = GameManager.getGames().get(gameIndex);
        return game.getPlayers().size() == PLAYERS_NEEDED;
    }

    /**
     * @return whether the active player can build a settlement (not location-specific)
     */
    public static boolean playerCanBuildSettlement() {
        Player player = GameManager.getActivePlayer();
        return (player == null) ? false : player.canBuildSettlement();
    }

    /**
     * @return whether the active player can build a city (not location-specific)
     */
    public static boolean playerCanBuildCity() {
        Player player = GameManager.getActivePlayer();
        return (player == null) ? false : player.canBuildCity();
    }

    /**
     * @return whether the active player can build a road (not location-specific)
     */
    public static boolean playerCanBuildRoad() {
        Player player = GameManager.getActivePlayer();
        return (player == null) ? false : player.canBuildRoad();
    }

    /**
     * @return whether the active player can buy a dev card
     */
    public static boolean canBuyDevCard() {
        Player player = GameManager.getActivePlayer();
        return (player == null) ? false : player.canBuyDevCard();
    }

    /**
     * @return whether it is the active player's turn
     */
    public static boolean isPlayerTurn() {
        Game game = GameManager.getActiveGame();
        if (game == null) { return false; }
        return game.getTurnTracker().getCurrentTurn() == GameManager.getActivePlayerIndex();
    }

    /**
     * @param resources
     * @return whether the player has at least the resources in the provided resource list
     */
    public static boolean playerHasResources(ResourceList resources) {
        Player player = GameManager.getActivePlayer();
        return (player == null) ? false : player.hasResources(resources);
    }

    /**
     * @param location
     * @return whether a settlement can be build at a specficied location (not player-specific)
     */
    public static boolean canBuildSettlementAtLocation(ItemLocation location) {
        Game game = GameManager.getActiveGame();
        if (game == null) { return false; }
        Player player = GameManager.getActivePlayer();
        return game.getMap().canBuildSettlement(player, location);
    }

    /**
     * @param location
     * @return whether a settlement can be build at a specficied location (not player-specific)
     */
    public static boolean canBuildCityAtLocation(ItemLocation location) {
        Game game = GameManager.getActiveGame();
        if (game == null) { return false; }
        Player player = GameManager.getActivePlayer();
        return game.getMap().canBuildCity(player, location);
    }

    /**
     * @param location
     * @return whether a settlement can be build at a specficied location (not player-specific)
     */
    public static boolean canBuildRoadAtLocation(ItemLocation location) {
        Game game = GameManager.getActiveGame();
        if (game == null) { return false; }
        Player player = GameManager.getActivePlayer();
        return game.getMap().canBuildRoad(player, location);
    }

    /**
     * @param user
     * @param gameIndex
     * @return whether a user can join a game at a certain index
     */
    public static boolean userCanJoinGame(User user, int gameIndex) {
        Game game = GameManager.getGames().get(gameIndex);
        if (game == null) { return false; }

        List<Integer> playerIds = game.getPlayers().stream().map(player -> player.getPlayerId()).collect(Collectors.toList());
        if (playerIds.contains(user.getPlayerID())) {
            return false;
        }

        if (game.getPlayers().size() == 4) {
            return false;
        }

        return true;
    }

    public static boolean playerCanMakeMaritimeTrade(TradeOffer offer) {
        Player player = GameManager.getActivePlayer();
        return (player == null) ? false : player.canMakeTrade(offer);
    }



}