package server;

import client.model.PlayerIndex;
import client.model.ResourceList;
import client.model.map.Edge;
import shared.definitions.*;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;


public class movesFacade implements serverFacade {

    public void sendChat(String content){}

    public void acceptTrade(boolean willAccept){}

    public void discardCards(){}

    public void rollNumber(){}

    public void buildRoad(boolean free, EdgeLocation roadLocation){}

    public void buildSettlement(boolean free, VertexLocation vertexLocation){}

    public void buildCity(VertexLocation vertexLocation){}

    public void offerTrade(ResourceList offer, PlayerIndex reveiver){}

    public void maritimeTrade(int ratio, ResourceType inputResource, ResourceType outputResource){}

    public void robPlayer(HexLocation location, PlayerIndex victim){}

    public void finishTurn(){}

    public void buyDevCard(){}

    public void Soldier(HexLocation location, PlayerIndex victim){}

    public void Year_of_Plenty(ResourceType resource1, ResourceType resource2){}

    public void Road_Building(EdgeLocation spot1, EdgeLocation spot2){}

    public void Monopoly(ResourceType resource){}

    public void Monument(){}

}
