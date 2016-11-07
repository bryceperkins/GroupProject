package server.facades;

import client.model.PlayerIndex;
import client.model.ResourceList;
import shared.definitions.*;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import server.handlers.iServerFacade;


public class MovesFacade implements iServerFacade {

    public void sendChat(String content){}

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
    public void acceptTrade(boolean willAccept){}

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
    public void discardCards(ResourceList discardedCards){}

    /**
     *  Process the number rolled
     *
     *  @pre It is your turn
     *  @pre Client model's status is Rolling
     *
     *  @post client model's status is now Discarding, Robbing, or Playing
     */
    public void rollNumber(){}

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
    public void buildRoad(boolean free, EdgeLocation roadLocation){}

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
    public void buildSettlement(boolean free, VertexLocation vertexLocation){}

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
    public void buildCity(VertexLocation vertexLocation){}

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
    public void offerTrade(ResourceList offer, PlayerIndex receiver){}

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
    public void maritimeTrade(int ratio, ResourceType inputResource, ResourceType outputResource){}

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
    public void robPlayer(HexLocation location, PlayerIndex victim){}

    /**
     *  Finish turn
     *
     *  @post new dex cards are transferred to old dev cards
     *  @post it is the next player's turn
     */
    public void finishTurn(){}

    /**
     *  buy a new dev card
     *
     *  @pre you have required resources
     *  @pre there are dev cards left in the deck
     *
     *  @post You have a new card - if it is a monument card, add it to old dev cards. Else add to new dev cards
     */
    public void buyDevCard(){}

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
    public void Soldier(HexLocation location, PlayerIndex victim){}

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
    public void Year_of_Plenty(ResourceType resource1, ResourceType resource2){}

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
    public void Road_Building(EdgeLocation spot1, EdgeLocation spot2){}

    /**
     *  Monopoly
     *
     *  Play the monopoly DevCard
     *
     *  @param resource - The resource you are collecting from the other players
     *
     *  @post You gain the amount of specified resource the other players lost
     */
    public void Monopoly(ResourceType resource){}

    /**
     *  Play the monument DevCard
     *
     *  @pre Playing monument cards will give you enough victory points to win the game
     *
     *  @post You gain a victory point
     */
    public void Monument(){}

}
