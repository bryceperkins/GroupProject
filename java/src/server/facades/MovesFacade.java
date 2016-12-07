package server.facades;

import shared.communication.User;
import shared.locations.VertexDirection;
import shared.model.*;
import shared.definitions.*;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.map.*;
import shared.model.player.Player;
import shared.model.map.Map;
import server.persistance.Persistor;
import shared.commands.*;

import java.util.*;
import java.util.stream.Collectors;
import java.io.Serializable;


public class MovesFacade extends BaseFacade implements Serializable{

    private final int ROBBING_ROLL = 7;
    private final int SETTLEMENT_RESOURCES = 1;
    private final int CITY_RESOURCES = 2;

    GameManager manager = GameManager.getInstance();
    Persistor persist = Persistor.getInstance();

    public MovesFacade(){ }

    public MovesFacade(User user){
        super(user);
    }

    private void updateAI() {
        for (Player player : getGame().getPlayers()) {
            if (player instanceof AI && !((AI) player).hasPlayed()) {
                ((AI) player).setPlayed();
                ((AI) player).play();
            }
        }
    }

    public String sendChat(int index, String content){
        getGame().addCommand(new SendChat(PlayerIndex.valueOf(index), content));

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
        getGame().addCommand(new AcceptTrade(PlayerIndex.valueOf(index), willAccept));

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
        getGame().addCommand(new DiscardCards(PlayerIndex.valueOf(index), discardedCards));

        User user = getUser();
        Game game = getGame();

        Player player = game.getPlayer(PlayerIndex.valueOf(index));
        player.getResources().removeResources(discardedCards);
        player.discarded();
        game.getBank().addResources(discardedCards);

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
        getGame().addCommand(new RollNumber(PlayerIndex.valueOf(playerInd), number));

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

    private void logRoll(int index, int number) {
        String name = getGame().getPlayer(PlayerIndex.valueOf(index)).getName();
        String logMessage = name + " rolled ";
        if (number == 8 || number == 11) {
            logMessage += " an " + number;
        } else {
            logMessage += " a " + number;
        }

        getGame().getLog().addLine(new MessageLine(name, logMessage));
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
        getGame().addCommand(new BuildRoad(PlayerIndex.valueOf(index), roadLocation, free));
        Game game = getGame();
        User user = getUser();
        Player player = game.getPlayer(PlayerIndex.valueOf(index));
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
                    game.getBank().increaseBrick();
                    game.getBank().increaseWood();
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

            logBuild(player.getName(), "road");
            return getModel();
        }

        return "Failed";
    }

    private void logBuild(String player, String building){
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
        getGame().addCommand(new BuildSettlement(PlayerIndex.valueOf(index), vertexLocation, free));
        Game game = getGame();
        Player player = game.getPlayer(PlayerIndex.valueOf(index));
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
                    game.getBank().increaseBrick();
                    game.getBank().increaseWood();
                    game.getBank().increaseSheep();
                    game.getBank().increaseWheat();
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

            if(state.isSecondRound()){
                giveSecondRoundResources(index, vertexLocation);
            }

            logBuild(player.getName(), "settlement");
            return getModel();
        }
        return "Failed";
    }

    private void giveSecondRoundResources(int index, VertexLocation settlement){
        VertexLocation location = settlement.getNormalizedLocation();
        VertexDirection direction = location.getDir();
        HexLocation hexLoc = location.getHexLoc();
        int x = hexLoc.getX();
        int y = hexLoc.getY();

        HexLocation hexLoc2 = new HexLocation(x, y-1);
        HexLocation hexLoc3 = null;
        switch(direction){
            case East:
                break;
            case NorthEast:
                hexLoc3 = new HexLocation(x+1, y-1);
                break;
            case NorthWest:
                hexLoc3 = new HexLocation(x-1, y);
                break;
            case SouthEast:
                break;
            case SouthWest:
                break;
            case West:
                break;
            default:
                break;
        }

        getHexToGivePlayerResource(index, hexLoc);
        getHexToGivePlayerResource(index, hexLoc2);
        getHexToGivePlayerResource(index, hexLoc3);
    }

    private void getHexToGivePlayerResource(int index, HexLocation loc){
        Game game = getGame();
        List<Hex> hexes = game.getMap().getHexes();
        for(Hex hex: hexes){
            HexLocation hexLoc = hex.getLocation();
            if(hexLoc.equals(loc)){
                addResourceToPlayer(index, hex.getResource());
            }
        }
    }

    private void addResourceToPlayer(int index, ResourceType resource){
        Game game = getGame();
        User user = getUser();
        Player player = game.getPlayer(PlayerIndex.valueOf(index));
        ResourceList playerResources = player.getResources();
        ResourceList bank = game.getBank();

        switch(resource){
            case BRICK:
                playerResources.setBrick(playerResources.getBrick() + 1);
                bank.setBrick(bank.getBrick() -1);
                break;
            case ORE:
                playerResources.setOre(playerResources.getOre() + 1);
                bank.setOre(bank.getOre() -1);
                break;
            case SHEEP:
                playerResources.setSheep(playerResources.getSheep() + 1);
                bank.setSheep(bank.getSheep() -1);
                break;
            case WHEAT:
                playerResources.setWheat(playerResources.getWheat() + 1);
                bank.setWheat(bank.getWheat() -1);
                break;
            case WOOD:
                playerResources.setWood(playerResources.getWood() + 1);
                bank.setWood(bank.getWood() -1);
                break;
            default:
                break;
        }
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
        getGame().addCommand(new BuildCity(PlayerIndex.valueOf(index), vertexLocation));

        Game game = getGame();
        User user = getUser();
        Player player = game.getPlayer(PlayerIndex.valueOf(index));
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
                game.getBank().increaseOre();
                game.getBank().increaseOre();
                game.getBank().increaseOre();
                game.getBank().increaseWheat();
                game.getBank().increaseWheat();
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

            logBuild(player.getName(), "city");
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
        getGame().addCommand(new OfferTrade(PlayerIndex.valueOf(index), offer, PlayerIndex.valueOf(receiver)));
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
        getGame().addCommand(new MaritimeTrade(PlayerIndex.valueOf(index), ratio, inputResource, outputResource));
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
        getGame().addCommand(new RobPlayer(PlayerIndex.valueOf(index), PlayerIndex.valueOf(victim), location));
        PlayerIndex victimIndex = PlayerIndex.valueOf(victim);
        Game game = getGame();
        User user = getUser();
        Map map = game.getMap();
        Random rand = new Random();
        Player player = game.getPlayer(PlayerIndex.valueOf(index));
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
        getGame().addCommand(new FinishTurn(PlayerIndex.valueOf(index)));
        Player player = getGame().getPlayer(PlayerIndex.valueOf(index));
        String message = player.getName() + "'s turn just ended.";
        TurnTracker tracker = getGame().getTurnTracker();
        getGame().getLog().addLine(new MessageLine(player.getName(), message));
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

        getGame().setTracker(tracker);
        updateAI();
        for (Player p : getGame().getPlayers()) {
            if (p instanceof AI) {
                ((AI) p).clearPlayed();
            }
        }
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
        getGame().addCommand(new BuyDevCard(PlayerIndex.valueOf(index)));
        User user = getUser();
        Game game = getGame();
        Player player = game.getPlayer(PlayerIndex.valueOf(index));

        ResourceList playerResources = player.getResources();

        if(player.canBuyDevCard()){
            DevCardType card = game.dealRandomCard();

            player.addDevCard(card);

            player.getResources().decreaseOre();
            player.getResources().decreaseSheep();
            player.getResources().decreaseWheat();
            game.getBank().increaseOre();
            game.getBank().increaseSheep();
            game.getBank().increaseWheat();
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
        getGame().addCommand(new Soldier(PlayerIndex.valueOf(index), PlayerIndex.valueOf(victim), location));
        User user = getUser();
        Game game = getGame();
        Player player = game.getPlayer(PlayerIndex.valueOf(index));

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
        getGame().addCommand(new YearOfPlenty(PlayerIndex.valueOf(index), resource1, resource2));
        User user = getUser();
        Game game = getGame();
        Player player = game.getPlayer(PlayerIndex.valueOf(index));

		ResourceList resources = player.getResources();
		ResourceList bank = game.getBank();
		
		switch(resource1){
			case BRICK: 
				resources.increaseBrick();
				bank.decreaseBrick();
				break;
			case WOOD: 
				resources.increaseWood();
				bank.decreaseWood();
				break;
			case SHEEP: 
				resources.increaseSheep();
				bank.decreaseSheep();
				break;
			case WHEAT: 
				resources.increaseWheat();
				bank.decreaseWheat();
				break;
			case ORE: 
				resources.increaseOre();
				bank.decreaseOre();
				break;
		}
		
		switch(resource2){
			case BRICK: 
				resources.increaseBrick();
				bank.decreaseBrick();
				break;
			case WOOD: 
				resources.increaseWood();
				bank.decreaseWood();
				break;
			case SHEEP: 
				resources.increaseSheep();
				bank.decreaseSheep();
				break;
			case WHEAT: 
				resources.increaseWheat();
				bank.decreaseWheat();
				break;
			case ORE: 
				resources.increaseOre();
				bank.decreaseOre();
				break;
		}

        player.removeDevCard(DevCardType.YEAR_OF_PLENTY);
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
        getGame().addCommand(new RoadBuilding(PlayerIndex.valueOf(index), spot1, spot2));
        User user = getUser();
        Game game = getGame();

        Player player = game.getPlayer(PlayerIndex.valueOf(index));
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
        getGame().addCommand(new Monopoly(PlayerIndex.valueOf(index), resource));
        User user = getUser();
        Game game = getGame();
        List<Player> players = game.getPlayers();
        Player player = game.getPlayer(PlayerIndex.valueOf(index));

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
        getGame().addCommand(new Monument(PlayerIndex.valueOf(index)));
        int newVictoryPoints;

        Game game = getGame();
        User user = getUser();
        Player player = game.getPlayer(PlayerIndex.valueOf(index));

        player.removeDevCard(DevCardType.MONUMENT);

        newVictoryPoints =player.getVictoryPoints() + 1;
        player.setVictoryPoints(newVictoryPoints);
        return getModel();
    }

}
