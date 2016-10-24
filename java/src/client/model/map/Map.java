package client.model.map;

import java.util.*;

import client.model.player.*;
import client.model.*;
import shared.locations.*;
import client.model.map.*;

public class Map implements PostProcessor {

    private List<Hex> hexes;
    private List<Port> ports;
	private List<Road> roads;
    private List<Settlement> settlements;
    private List<City> cities;
    private int radius;
    private Robber robber;
    
    

    public Map() {
    	
		super();
		this.hexes = new ArrayList<Hex>();
		this.ports = new ArrayList<Port>();
		this.settlements = new ArrayList<Settlement>();
		this.cities = new ArrayList<City>();
		this.radius = 3;
		this.robber = new Robber(3, 3);

	}

	public boolean canBuildRoad(Player player, EdgeLocation edgeLocation){
        int playerID = player.getPlayerID();
        edgeLocation = edgeLocation.getNormalizedLocation();

        for(Road road : roads){
            if(road.getLocation().equals(edgeLocation))
                return false;
        }
        edgeLocation = edgeLocation.getNormalizedLocation();
        int locationX = edgeLocation.getHexLoc().getX();
        int locationY = edgeLocation.getHexLoc().getY();
        EdgeDirection locationDirection = edgeLocation.getDir();

        //check four edge locations
        EdgeLocation edge1 = null;
        EdgeLocation edge2 = null;
        EdgeLocation edge3 = null;
        EdgeLocation edge4 = null;
        HexLocation hex12;
        HexLocation hex34;

        switch(locationDirection){
            case North:
                hex12 = new HexLocation(locationX,locationY);
                edge1 = new EdgeLocation(hex12,EdgeDirection.NorthEast);
                edge2 = new EdgeLocation(hex12,EdgeDirection.NorthWest);

                hex34 = new HexLocation(locationX,locationY-1);
                edge3 = new EdgeLocation(hex34,EdgeDirection.SouthEast);
                edge4 = new EdgeLocation(hex34,EdgeDirection.SouthWest);
                break;
            case NorthEast:
                hex12 = new HexLocation(locationX,locationY);
                edge1 = new EdgeLocation(hex12,EdgeDirection.North);
                edge2 = new EdgeLocation(hex12,EdgeDirection.SouthEast);

                hex34 = new HexLocation(locationX+1,locationY-1);
                edge3 = new EdgeLocation(hex34,EdgeDirection.NorthWest);
                edge4 = new EdgeLocation(hex34,EdgeDirection.South);
                break;
            case NorthWest:
                hex12 = new HexLocation(locationX,locationY);
                edge1 = new EdgeLocation(hex12,EdgeDirection.North);
                edge2 = new EdgeLocation(hex12,EdgeDirection.SouthWest);

                hex34 = new HexLocation(locationX-1,locationY);
                edge3 = new EdgeLocation(hex34,EdgeDirection.NorthEast);
                edge4 = new EdgeLocation(hex34,EdgeDirection.South);
                break;
            case South:
                hex12 = new HexLocation(locationX,locationY);
                edge1 = new EdgeLocation(hex12,EdgeDirection.SouthWest);
                edge2 = new EdgeLocation(hex12,EdgeDirection.SouthEast);

                hex34 = new HexLocation(locationX,locationY+1);
                edge3 = new EdgeLocation(hex34,EdgeDirection.NorthWest);
                edge4 = new EdgeLocation(hex34,EdgeDirection.NorthEast);
                break;
            case SouthEast:
                hex12 = new HexLocation(locationX,locationY);
                edge1 = new EdgeLocation(hex12,EdgeDirection.NorthEast);
                edge2 = new EdgeLocation(hex12,EdgeDirection.South);

                hex34 = new HexLocation(locationX,locationY+1);
                edge3 = new EdgeLocation(hex34,EdgeDirection.North);
                edge4 = new EdgeLocation(hex34,EdgeDirection.SouthWest);
                break;
            case SouthWest:
                hex12 = new HexLocation(locationX,locationY);
                edge1 = new EdgeLocation(hex12,EdgeDirection.NorthWest);
                edge2 = new EdgeLocation(hex12,EdgeDirection.South);

                hex34 = new HexLocation(locationX,locationY+1);
                edge3 = new EdgeLocation(hex34,EdgeDirection.North);
                edge4 = new EdgeLocation(hex34,EdgeDirection.SouthEast);
                break;
        }

        edge1 = edge1.getNormalizedLocation();
        edge2 = edge2.getNormalizedLocation();
        edge3 = edge3.getNormalizedLocation();
        edge4 = edge4.getNormalizedLocation();

        if(checkForPlayerRoad(playerID,edge1) || checkForPlayerRoad(playerID,edge2) ||
            checkForPlayerRoad(playerID,edge3) || checkForPlayerRoad(playerID,edge4))
            return true;

    	return false;
    }

    public boolean checkForPlayerRoad(int playerID, EdgeLocation edge){
        for(Road road: roads){
            if(road.getLocation().equals(edge) && road.getOwner().getIndex() == playerID)
                return true;
        }
        return false;
    }

    public boolean canBuildSettlement(Player player, ItemLocation itemLocation){
    	/*
    	 * does vertex have settlement or city on it
    	 * does the vertex right next to it have a city/settlement on it
    	 */
    	
    	if(checkVertex(itemLocation.getLocation(), itemLocation.getDirection()) == false)
    		return false;
    	
    	switch (itemLocation.getDirection())
    	{
    	case West: 
    		return checkVertex(itemLocation.getLocation(), VertexDirection.NorthWest) &&
    				checkVertex(itemLocation.getLocation(), VertexDirection.SouthWest) &&
    				checkVertex(new HexLocation(itemLocation.getLocation().getX() -1, itemLocation.getLocation().getY() + 1), VertexDirection.East);
    	case NorthWest:
    		return checkVertex(itemLocation.getLocation(), VertexDirection.NorthEast) &&
    				checkVertex(itemLocation.getLocation(), VertexDirection.West) &&
    				checkVertex(new HexLocation(itemLocation.getLocation().getX() -1, itemLocation.getLocation().getY() + 1), VertexDirection.NorthEast);
    	
    	case NorthEast:
    		return checkVertex(itemLocation.getLocation(), VertexDirection.NorthWest) &&
    				checkVertex(itemLocation.getLocation(), VertexDirection.SouthEast) &&
    				checkVertex(new HexLocation(itemLocation.getLocation().getX() +1, itemLocation.getLocation().getY() + 1), VertexDirection.NorthWest);
    	
    	case East:
    		return checkVertex(itemLocation.getLocation(), VertexDirection.SouthEast) &&
    				checkVertex(itemLocation.getLocation(), VertexDirection.NorthEast) &&
    				checkVertex(new HexLocation(itemLocation.getLocation().getX() +1, itemLocation.getLocation().getY() + 1), VertexDirection.SouthEast);
    	
    	case SouthEast:
    		return checkVertex(itemLocation.getLocation(), VertexDirection.East) &&
    				checkVertex(itemLocation.getLocation(), VertexDirection.SouthWest) &&
    				checkVertex(new HexLocation(itemLocation.getLocation().getX() +1, itemLocation.getLocation().getY() - 1), VertexDirection.SouthWest);
    	
    	case SouthWest:
    		return checkVertex(itemLocation.getLocation(), VertexDirection.West) &&
    				checkVertex(itemLocation.getLocation(), VertexDirection.SouthEast) &&
    				checkVertex(new HexLocation(itemLocation.getLocation().getX() -1, itemLocation.getLocation().getY() - 1), VertexDirection.West);
    	
    	}
    	
    	return false;
    	}
    
    private boolean checkVertex(HexLocation loc, VertexDirection dir)
    {
    	for(int i = 0; i < settlements.size(); i++)
    	{
    		if(settlements.get(i).getLocation().equals(loc) && 
    				settlements.get(i).getLocation().getDirection() == dir)
    		{
    			return false;
    		}
    	}
    	for(int i = 0; i < cities.size(); i++)
    	{
    		if(cities.get(i).getLocation().equals(loc) && 
    				cities.get(i).getLocation().getDirection() == dir)
    		{
    			return false;
    		}
    	}
    	
    	return true;
    }

	public List<Road> getRoads() {
		return roads;
	}

	public void setRoads(List<Road> roads) {
		this.roads = roads;
	}

	public boolean canBuildCity(Player player, ItemLocation itemLocation){

    	for(int i = 0; i < settlements.size(); i++)
    	{
    		if(settlements.get(i).getLocation().equals(itemLocation.getLocation()) && 
    				settlements.get(i).getLocation().getDirection() == itemLocation.getDirection())
    		{
    			if(settlements.get(i).getOwner() == player.getPlayerIndex())
    				return true;
    			else
    				return false;
    		}
    	}
    	
    	return false;
    }

    public boolean canPlaceRobber(HexLocation hexLoc){
        Robber robber = getRobber();

        if(hexLoc.equals(robber))
            return true;
        else
            return false;
    }

    public void setHexes(List<Hex> hexes) {
        this.hexes = hexes;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }

    public void setSettlements(List<Settlement> settlements) {
        this.settlements = settlements;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setRobber(Robber robber) {
        this.robber = robber;
    }

	public List<Hex> getHexes() {
		return hexes;
	}

	public List<Port> getPorts() {
		return ports;
	}

	public List<Settlement> getSettlements() {
		return settlements;
	}

	public List<City> getCities() {
		return cities;
	}

	public int getRadius() {
		return radius;
	}

	public Robber getRobber() {
		return robber;
	}

	@Override
	public void postDeserializationSetup(Game game) {
		for (Hex h : hexes) {
			h.postDeserializationSetup(game);
		}
		for (City c : cities) {
			c.postDeserializationSetup(game);
		}
		for (Settlement s : settlements) {
			s.postDeserializationSetup(game);
		}
		for (Road r : roads) {
            r.postDeserializationSetup(game);
        }
	}
}
