package server.facades;

import shared.communication.User;
import shared.model.*;
import shared.definitions.*;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.map.*;
import shared.model.player.Player;
import shared.model.map.Map;

import java.util.*;


public class MovesFacade extends BaseFacade{

    private final int ROBBING_ROLL = 7;
    private final int SETTLEMENT_RESOURCES = 1;
    private final int CITY_RESOURCES = 2;

    GameManager manager = GameManager.getInstance();

    public MovesFacade(User user){
        super(user);
    }

    private void updateAI() {
        for (Player player : getGame().getPlayers()) {
            if (player instanceof AI) {
                ((AI) player).play();
            }
        }
    }

    public String sendChat(int index, String content){
        if (index < 0 || index > 3) return "Failed";
        if (content == null) return "Failed";
        Game game = getGame();
        Player player = game.getPlayer(PlayerIndex.valueOf(index));
        Chat chat = game.getChat();
        chat.createMessage(new MessageLine(player.getName(), content));
        getGame().setChat(chat);
        return getModel();
    }

    /**
     *  Accept the proposed trade.
     *
     *  @param willAccept - accept the trade, True/False
     *
     *  @pre trade has been offered
     *  @pre have resources
     *
     *  @post resources exchanged
     *  @post trade offere removed
     */
    public String acceptTrade(int index, boolean willAccept){
        Game game = getGame();
        String name = game.getPlayer(PlayerIndex.valueOf(index)).getName();

        if (!willAccept) {
            getGame().setTradeOffer(null);
            getGame().getLog().addLine(new MessageLine(name, name + " has rejected the trade."));
            return getModel();
        } else {
            TradeOffer trade_offer = game.getTradeOffer();
            ResourceList sender_list = game.getPlayer(trade_offer.getSender()).getResources();
            ResourceList reciever_list = game.getPlayer(trade_offer.getReceiver()).getResources();
            ResourceList offer = trade_offer.getOffer();
            sender_list.addResources(offer.reversedList());
            reciever_list.addResources(offer);
            getGame().getPlayer(trade_offer.getSender()).setResources(sender_list);
            getGame().getPlayer(trade_offer.getReceiver()).setResources(reciever_list);
            getGame().getLog().addLine(new MessageLine(name, name + " has accepted the trade."));
            getGame().setTradeOffer(null);
            return getModel();
        }
    }

    private int discardCount = 0;
    /**
     *  When a 7 is rolled and a player has more than 7 cards, they must discard until they have 7 card in their hand
     *
     *  @param discardedCards - ResourceList of cards to be removed
     *
     *  @pre Status of client model is Discarding
     *  @pre You have over 7 cards
     *  @pre You have the cards you're choosing to discard
     *
     *  @post You gave up the specified resources
     *  @post If you're the last one to discard, the client model status changes to Robbing
     */
    public String discardCards(int index, ResourceList discardedCards){
        User user = getUser();
        Game game = getGame();

        Player player = game.getPlayerByName(user.getUserName());
        player.getResources().removeResources(discardedCards);
        player.discarded();

        boolean done = true;

        for (Player p: getGame().getPlayers()){
            System.out.println("Player: " + p.getName() + " " + p.getResources().total() + " " + p.didDiscard());
            if (p.getResources().total() > 7 && !p.didDiscard()) {
                done = false;
            }
        }

        if (done){
            game.getTurnTracker().setGameStatus(TurnTracker.GameStatus.Robbing);
        }

        return getModel();
    }

    /**
     *  Process the number rolled
     *
     *  @pre It is your turn
     *  @pre Client model's status is Rolling
     *
     *  @post client model's status is now Discarding, Robbing, or Playing
     */
    public String rollNumber(int playerInd, int number){
        Game game = getGame();
        Map map = game.getMap();

        logRoll(playerInd, number);

        if (number == ROBBING_ROLL) {
            boolean discarding = false;
            for (Player player : game.getPlayers()) {
                if (player.getResources().total() > 7) {
                    game.getTurnTracker().setGameStatus(TurnTracker.GameStatus.Discarding);
                    discarding = true;
                }
            }

            if (!discarding) {
                game.getTurnTracker().setGameStatus(TurnTracker.GameStatus.Robbing);
            }

            return getModel();
        }

        // Creates map of resources to a set of normalized locations that match the roll (not tied to pieces)
        HashMap<ResourceType, Set<VertexLocation>> locations = new HashMap<>();
        for (Hex h : map.getHexes()) {
            if (h.getNumber() == number) {
                if (!locations.containsKey(h.getResource())) {
                    locations.put(h.getResource(), new HashSet<>());
                }
                locations.get(h.getResource()).addAll(h.getNormalizedVertexLocations());
            }
        }

        for (ResourceType resource : locations.keySet()) {
            updatePlayerAndBankResource(resource, locations.get(resource));
        }

        game.getTurnTracker().setGameStatus(TurnTracker.GameStatus.Playing);
        updateAI();
        return getModel();
    }

    /**
     * Assumes resourcebank has requisite resources
     * Updates player and bank resources based on normalized vertex locations
     * @param resource
     * @param resourceVertexLocations
     */
    private void updatePlayerAndBankResource(ResourceType resource, Set<VertexLocation> resourceVertexLocations) {
        Map map = getGame().getMap();

        // Keep map of expected updates after verifying resources exist
        HashMap<Player, Integer> plannedUpdates = new HashMap<>();
        int totalNeeded = 0;

        for (Piece piece : map.getCitiesAndSettlements()) {
            if (resourceVertexLocations.contains(piece.getLocation().getNormalizedLocation())) {
                Player owner = getGame().getPlayer(piece.getOwner());
                int increaseBy = (piece instanceof Settlement) ? SETTLEMENT_RESOURCES : CITY_RESOURCES;

                if (!plannedUpdates.keySet().contains(owner)) {
                    plannedUpdates.put(owner, 0);
                }

                // increment expected increase
                plannedUpdates.put(owner, plannedUpdates.get(owner) + increaseBy);
                totalNeeded += increaseBy;
            }
        }

        // Check if bank has enough of resource for all players - if so, add to player, remove from bank
        for (Player player : plannedUpdates.keySet()) {
            if (getGame().getBank().hasResource(resource, totalNeeded)) {
                player.getResources().increaseBy(resource, plannedUpdates.get(player));
                getGame().getBank().decreaseBy(resource, plannedUpdates.get(player));
            }
        }
    }

    private void logRoll(int playerInd, int number) {
        String logMessage = "Player " + (playerInd + 1) + " rolled ";
        if (number == 8 || number == 11) {
            logMessage += " an " + number;
        } else {
            logMessage += " a " + number;
        }

        String playerName = getGame().getPlayer(PlayerIndex.valueOf(playerInd)).getName();

        getGame().getLog().addLine(new MessageLine(playerName, logMessage));
    }

    /**
     *  Build a Road.
     *
     *  @param free - If this road is built for free
     *  @param roadLocation - EdgeLocation
     *
     *  @pre Location is open
     *  @pre Location is not on water
     *  @pre You have the resources
     *  @pre not next to another settlement
     *  @pre connected to a road if not during setup
     *  @pre connected to the settlement if during setup
     *
     *  @post resources lost
     *  @post Settlement is on the map
     *  @post longest road gained if necessary
     */
    public String buildRoad(int index, boolean free, EdgeLocation roadLocation){
        Game game = getGame();
        User user = getUser();
        Player player = game.getPlayerByName(user.getUserName());
        int remainingRoads = player.getRoadsRemaining();

        Map map = game.getMap();
        State state = game.getState();
        if(map.canBuildRoad(player,roadLocation,state) && remainingRoads > 0){
            if(!free){
                //charge cost of road
                ResourceList roadCost = new ResourceList(1,0,0,0,1);
                ResourceList playerResources = player.getResources();

                if(playerResources.hasResources(roadCost)){
                    playerResources.decreaseBrick();
                    playerResources.decreaseWood();
                }else {
                    System.out.println("not enough resources to purchase road");
                    return "Failed";
                }
            }
            player.setRoadsRemaining(remainingRoads-1);
            Road road = new Road(player.getPlayerIndex(),roadLocation);
            List<Road> existingRoads = map.getRoads();
            existingRoads.add(road);
            map.setRoads(existingRoads);



            logBuild(user.getUserName(), "road");
            return getModel();
        }

        return "Failed";
    }

    public void logBuild(String player, String building){
        String logMessage = player + " built a " + building;
        getGame().getLog().addLine(new MessageLine(player, logMessage));
    }

    /**
     *  Build a Settlement.
     *
     *  @param free - If this settlement is free
     *  @param vertexLocation - VertexLocation
     *
     *  @pre Location is open
     *  @pre Location is not on water
     *  @pre You have the resources
     *  @pre not next to another settlement
     *  @pre connected to a road if not during setup
     *
     *  @post resources lost
     *  @post Settlement is on the map
     */
    public String buildSettlement(int index, boolean free, VertexLocation vertexLocation){
        Game game = getGame();
        User user = getUser();
        Player player = game.getPlayerByName(user.getUserName());
        int remainingSettlements = player.getSettlementsRemaining();

        ItemLocation location = new ItemLocation(vertexLocation.getHexLoc(),vertexLocation.getDirection());
        Map map = game.getMap();
        State state = game.getState();
        if(map.canBuildSettlement(player,location,state) && remainingSettlements >0){
            if(!free){
                //charge cost of settlement
                ResourceList settlementCost = new ResourceList(1,0,1,1,1);
                ResourceList playerResources = player.getResources();

                if(playerResources.hasResources(settlementCost)){
                    playerResources.decreaseBrick();
                    playerResources.decreaseWood();
                    playerResources.decreaseSheep();
                    playerResources.decreaseWheat();
                }else {
                    return "Failed";
                }
            }
            player.setSettlementsRemaining(remainingSettlements - 1);
            
            int victoryPoints = player.getVictoryPoints();
            player.setVictoryPoints(victoryPoints + 1);
            Settlement settlement = new Settlement(player.getPlayerIndex(), vertexLocation);
            List<Settlement> existingSettlements = map.getSettlements();
            existingSettlements.add(settlement);
            map.setSettlements(existingSettlements);

            logBuild(user.getUserName(), "settlement");
            return getModel();
        }
        return "Failed";
    }

    /**
     *  Build a city.
     *
     *  @param vertexLocation - Location of the city
     *
     *  @pre You own a settlement on the vertex
     *  @pre have resources
     *
     *  @post resources lost
     *  @post settlement returned
     *  @post city is on the map
     */
    public String buildCity(int index, VertexLocation vertexLocation){

        Game game = getGame();
        User user = getUser();
        Player player = game.getPlayerByName(user.getUserName());
        int citiesRemaining = player.getCitiesRemaining();

        ItemLocation location = new ItemLocation(vertexLocation.getHexLoc(),vertexLocation.getDirection());
        Map map = game.getMap();
        State state = game.getState();
        if(map.canBuildCity(player,location) && citiesRemaining > 0){
            //charge cost of City
            ResourceList cityCost = new ResourceList(0,3,0,2,0);
            ResourceList playerResources = player.getResources();

            if(playerResources.hasResources(cityCost)){
                playerResources.decreaseOre();
                playerResources.decreaseOre();
                playerResources.decreaseOre();
                playerResources.decreaseWheat();
                playerResources.decreaseWheat();
            }else {
                System.out.println("not enough resources to purchase road");
                return "Failed";
            }
            int victoryPoints = player.getVictoryPoints();
            player.setVictoryPoints(victoryPoints + 1);
            player.setCitiesRemaining(citiesRemaining - 1);
            City city = new City(player.getPlayerIndex(), vertexLocation);
            List<City> existingCities = map.getCities();
            existingCities.add(city);
            map.setCities(existingCities);

            //remove settlement replaced by city
            List<Settlement> existingSettlements = map.getSettlements();
            for (Settlement settlement : existingSettlements){
                if (settlement.getLocation().equals(location)){
                    existingSettlements.remove(settlement);
                    break;
                }
            }

            logBuild(user.getUserName(), "city");
            return getModel();
        }

        return "Failed";
    }

    /**
     *  Offer a trade to another player
     *
     *  @param offer - cards in the trade
     *  @param receiver - the recipient of the trade
     *
     *  @pre You have enough of the specified resources
     *
     *  @post trade is offered to the other player
     */
    public String offerTrade(int index, ResourceList offer, int receiver){
        Game game = getGame();
        getGame().setTradeOffer(new TradeOffer(index, receiver, offer));
        String name = game.getPlayer(PlayerIndex.valueOf(index)).getName();
        String reciever_name = game.getPlayer(PlayerIndex.valueOf(receiver)).getName();
        getGame().getLog().addLine(new MessageLine(name, name + " has requested trade with " + reciever_name));
        updateAI();
        return getModel();
    }

    /**
     *  Perform a trade with the Bank
     *
     *  @param ratio - cards required for trade
     *  @param inputResource - resource you are sending
     *  @param outputResource - resource you are expecting
     *
     *  @pre You have enough of the specified inputResource
     *  @pre for ratios less than for you have the correct port
     *
     *  @post trade has been performed
     */
    public String maritimeTrade(int index, int ratio, ResourceType inputResource, ResourceType outputResource){
        ResourceList trade = new ResourceList();
        Game game = getGame();
        switch (inputResource){
            case BRICK: 
                trade.setBrick(ratio*-1);
                break;
            case ORE: 
                trade.setOre(ratio*-1);
                break;
            case SHEEP: 
                trade.setSheep(ratio*-1);
                break;
            case WHEAT:
                trade.setWheat(ratio*-1);
                break;
            case WOOD: 
                trade.setWood(ratio*-1);
                break;
        }
        switch (outputResource){
            case BRICK: 
                trade.setBrick(1);
                break;
            case ORE: 
                trade.setOre(1);
                break;
            case SHEEP: 
                trade.setSheep(1);
                break;
            case WHEAT: 
                trade.setWheat(1);
                break;
            case WOOD: 
                trade.setWood(1);
                break;
        }
        getGame().getPlayer(PlayerIndex.valueOf(index)).getResources().addResources(trade);
        getGame().getBank().addResources(trade.reversedList());
        String name = game.getPlayer(PlayerIndex.valueOf(index)).getName();
        getGame().getLog().addLine(new MessageLine(name, name + " has traded " + ratio + " " +
                inputResource.getValue() + " for 1 " + outputResource.getValue()));
        return getModel();
    }

    /**
     *  Relocate the robber and rob another player
     *
     *  @param location - new location for the Robber
     *  @param victim - player to be robbed or -1 for no one
     *
     *  @pre Robber is not staying in the same locationY
     *  @pre Player being robbed has resource cards
     *
     *  @post Robber has moved
     *  @post Player being robbed (if any) has given you their resource cards
     *  @post Largest army awarded to player with most Solder cards
     */
    public String robPlayer(int index, HexLocation location, int victim){
        PlayerIndex victimIndex = PlayerIndex.valueOf(victim);
        Game game = getGame();
        User user = getUser();
        Map map = game.getMap();
        Random rand = new Random();
        Player player = game.getPlayerByName(user.getUserName());
        Robber robber = map.getRobber();

        if(victim == -1){
            if(map.canPlaceRobber(location)){

                robber.setX(location.getX());
                robber.setY(location.getY());
                String logMessage = player.getName() + " has robbed no one";
                getGame().getLog().addLine(new MessageLine(player.getName(), logMessage));
                game.getTurnTracker().setGameStatus(TurnTracker.GameStatus.Playing);
                updateAI();
                return getModel();
            }
        }

        Player victimPlayer = game.getPlayer(victimIndex);
        ResourceList playerResources = player.getResources();
        ResourceList victimPlayerResources = victimPlayer.getResources();

        int randomNumber;
        List<ResourceType> resources = new ArrayList<>();

        if(victimPlayerResources.total()> 0){
            if(map.canPlaceRobber(location)){
                int wood = victimPlayerResources.getWood();
                int ore = victimPlayerResources.getOre();
                int wheat = victimPlayerResources.getWheat();
                int brick = victimPlayerResources.getBrick();
                int sheep = victimPlayerResources.getSheep();

                for(int i = 0; i < wood; i++){resources.add(ResourceType.WOOD);}
                for(int i = 0; i < ore; i++){resources.add(ResourceType.ORE);}
                for(int i = 0; i < wheat; i++){resources.add(ResourceType.WHEAT);}
                for(int i = 0; i < brick; i++){resources.add(ResourceType.BRICK);}
                for(int i = 0; i < sheep; i++){resources.add(ResourceType.SHEEP);}

                //transfer one random card form victim to player
                randomNumber = rand.nextInt(victimPlayerResources.total());

                switch (resources.get(randomNumber)){
                    case WOOD:
                        victimPlayerResources.decreaseWood();
                        playerResources.increaseWood();
                        break;
                    case ORE:
                        victimPlayerResources.decreaseOre();
                        playerResources.increaseOre();
                        break;
                    case WHEAT:
                        victimPlayerResources.decreaseWheat();
                        playerResources.increaseWheat();
                        break;
                    case BRICK:
                        victimPlayerResources.decreaseBrick();
                        playerResources.increaseBrick();
                        break;
                    case SHEEP:
                        victimPlayerResources.decreaseSheep();
                        playerResources.increaseSheep();
                        break;
                    default:
                        System.out.println("some error with robbing resource");
                        return "Failed";
                }
                robber.setX(location.getX());
                robber.setY(location.getY());

                String logMessage = player.getName() + " has robbed " + victimPlayer.getName();
                getGame().getLog().addLine(new MessageLine(player.getName(), logMessage));

                getGame().getTurnTracker().setGameStatus(TurnTracker.GameStatus.Playing);

                updateAI();
                return getModel();
            }else{
                System.out.println("Cannot place a robber on this location");
                return "Failed";
            }
        }else{
            System.out.println("Victim does not have resources");
            return "Failed";
        }
    }

    /**
     *  Finish turn
     *
     *  @post new dex cards are transferred to old dev cards
     *  @post it is the next player's turn
     */
    public String finishTurn(int index){
        Player player = getGame().getPlayer(PlayerIndex.valueOf(index));
        String message = player.getName() + "'s turn just ended.";
        getGame().getLog().addLine(new MessageLine(player.getName(), message));
        TurnTracker tracker = getGame().getTurnTracker();

        tracker.setNextTurn();
        player.transferNewDevCards();

        PlayerIndex mostRoads = tracker.getLongestRoadOwner();
        PlayerIndex largestArmy = tracker.getLargestArmyOwner();

        Player lr = getGame().getPlayer(mostRoads);
        Player la = getGame().getPlayer(largestArmy);

        if (lr != null){
            getGame().getPlayer(mostRoads).setVictoryPoints(lr.getVictoryPoints() - 2);
        }
        if (la != null){
            getGame().getPlayer(largestArmy).setVictoryPoints(la.getVictoryPoints() - 2);
        }

        int roadCount = 10;
        int armyCount = 3;

        for (Player p: getGame().getPlayers()){
            p.clearDiscard();
            if (p.getRoadsRemaining() < roadCount){
                roadCount = p.getRoadsRemaining();
                mostRoads = p.getPlayerIndex();
            }
            if (p.getSoldiersPlayed() > armyCount){
                armyCount = p.getSoldiersPlayed();
                largestArmy = p.getPlayerIndex();
            }
        }
        
        lr = getGame().getPlayer(mostRoads);
        la = getGame().getPlayer(largestArmy);
        if (lr != null){
            getGame().getPlayer(mostRoads).setVictoryPoints(lr.getVictoryPoints() + 2);
        }
        if (la != null){
            getGame().getPlayer(largestArmy).setVictoryPoints(la.getVictoryPoints() + 2);
        }

        tracker.setLongestRoadOwner(mostRoads);
        tracker.setLargestArmyOwner(largestArmy);

        updateAI();
        return getModel();
    }

    /**
     *  buy a new dev card
     *
     *  @pre you have required resources
     *  @pre there are dev cards left in the deck
     *
     *  @post You have a new card - if it is a monument card, add it to old dev cards. Else add to new dev cards
     */
    public String buyDevCard(int index){
        User user = getUser();
        Game game = getGame();
        Player player = game.getPlayerByName(user.getUserName());

        ResourceList playerResources = player.getResources();

        if(player.canBuyDevCard()){
            DevCardType card = game.dealRandomCard();

            player.addDevCard(card);

            player.getResources().decreaseOre();
            player.getResources().decreaseSheep();
            player.getResources().decreaseWheat();
            //dole out devcard

        }else {
            return "Failed";
        }

        return getModel();

    }

    /**
     *  Relocate the robber and rob another player. Add one to army
     *
     *  @param location - new location for the Robber
     *  @param victim - player to be robbed or -1 for no one
     *
     *  @pre Robber is not staying in the same locationY
     *  @pre Player being robbed has resource cards
     *
     *  @post Robber has moved
     *  @post Player being robbed (if any) has given you their resource cards
     *  @post Largest army awarded to player with most Solder cards
     *  @post Player is not allowed to play any other dev cards this turn
     */
    public String Soldier(int index, HexLocation location, int victim){
        User user = getUser();
        Game game = getGame();
        Player player = game.getPlayerByName(user.getUserName());

        player.removeDevCard(DevCardType.SOLDIER);
        robPlayer(index, location, victim);


        return getModel();
    }

    /**
     *  Gain two resources from the bank
     *
     *  @param resource1 - First resource
     *  @param resource2 - Second resourceL
     *
     *  @pre The resources are in the bank
     *
     *  @post You gained the two specified resources
     */
    public String Year_of_Plenty(int index, ResourceType resource1, ResourceType resource2){
        User user = getUser();
        Game game = getGame();
        Player player = game.getPlayerByName(user.getUserName());

        int resource1Amount = player.getResources().getResourceByType(resource1);
        int resource2Amount = player.getResources().getResourceByType(resource2);

        resource1Amount += 2;
        resource2Amount += 2;

        player.getResources().setResourceByType(resource2, resource2Amount);
        player.removeDevCard(DevCardType.YEAR_OF_PLENTY);

        ResourceList bank = game.getBank();

        return getModel();
    }

    /**
     *  RoadBuilding
     *
     *  Build two roads for free
     *
     *  @param spot1 - Location for first road
     *  @param spot2 - Location for second road
     *
     *  @pre Location is open
     *  @pre Location is not on water
     *  @pre You have the resources
     *  @pre not next to another settlement
     *  @pre connected to a road
     *  @pre connected to the settlement
     *  @pre you have 2 unused roads
     *
     *  @post you have 2 fewer roads
     *  @post 2 new roads appear
     *  @post longest road gained if necessary
     */
    public String Road_Building(int index, EdgeLocation spot1, EdgeLocation spot2){
        User user = getUser();
        Game game = getGame();

        Player player = game.getPlayerByName(user.getUserName());
        player.removeDevCard(DevCardType.ROAD_BUILD);

        //public String buildRoad(int index, boolean free, EdgeLocation roadLocation){
        buildRoad(index, true, spot1);
        buildRoad(index, true, spot2);

        return getModel();
    }

    /**
     *  Monopoly
     *
     *  Play the monopoly DevCard
     *
     *  @param resource - The resource you are collecting from the other players
     *
     *  @post You gain the amount of specified resource the other players lost
     */
    public String Monopoly(int index, ResourceType resource){
        User user = getUser();
        Game game = getGame();
        List<Player> players = game.getPlayers();
        Player player = game.getPlayerByName(user.getUserName());

        player.removeDevCard(DevCardType.MONOPOLY);

        int stolenResources = 0;

        for(Player p: players)
        {
            int playerResources = p.getResources().getResourceByType(resource);

        }

        return getModel();

    }

    /**
     *  Play the monument DevCard
     *
     *  @pre Playing monument cards will give you enough victory points to win the game
     *
     *  @post You gain a victory point
     */

    public String Monument(int index){
        int newVictoryPoints;

        Game game = getGame();
        User user = getUser();
        Player player = game.getPlayerByName(user.getUserName());

        player.removeDevCard(DevCardType.MONUMENT);

        newVictoryPoints =player.getVictoryPoints() + 1;
        player.setVictoryPoints(newVictoryPoints);
        return getModel();
    }

}
