package server.facades;

import shared.communication.User;
import shared.model.Game;
import shared.model.GameManager;
import shared.model.PlayerIndex;
import shared.model.ResourceList;
import shared.definitions.*;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import server.handlers.iServerFacade;
import shared.model.State;
import shared.model.map.Map;
import shared.model.map.Road;
import shared.model.player.Player;

import java.util.List;


public class MovesFacade extends BaseFacade{

    public MovesFacade(User user){
        super(user);
    }



    public String sendChat(int index, String content){
		return "Failed";
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
		return "";
    }

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
		return "";
	}

    /**
     *  Process the number rolled
     *
     *  @pre It is your turn
     *  @pre Client model's status is Rolling
     *
     *  @post client model's status is now Discarding, Robbing, or Playing
     */
    public String rollNumber(int index, int number){
		return "";
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
        User user = getUser();
        Game game = getGame();

        Player player = game.getPlayerByName(user.getUserName());

        if(!free){
            //charge cost of road
            ResourceList roadCost = new ResourceList(1,0,0,0,1);
            ResourceList playerResources = player.getResources();

            if(playerResources.hasResources(roadCost)){
                playerResources.decreaseBrick();
                playerResources.decreaseWood();
            }else {
                System.out.println("not enough resources to purchase road");
                return "";
            }
        }

        Map map = game.getMap();
        State state = game.getState();
        if(map.canBuildRoad(player,roadLocation,state)){
            Road road = new Road(player.getPlayerIndex(),roadLocation);
            List<Road> existingRoads = map.getRoads();
            existingRoads.add(road);
            map.setRoads(existingRoads);
            return getModel();
        }

        return "";
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
		return "";
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
		return "";
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
		return "";
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
		return "";
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
		return "";
	}

    /**
     *  Finish turn
     *
     *  @post new dex cards are transferred to old dev cards
     *  @post it is the next player's turn
     */
    public String finishTurn(int index){
		return "";
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
	
	return "";

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
		return "";
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
		return "";
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
		return "";
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

	return "";

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
	return "";
	}

}
