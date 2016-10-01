package client.model.map;

import java.util.*;

import client.model.player.*;
import client.model.*;
import shared.locations.*;

public class Map {

    private List<Hex> hexes;
    private List<Port> ports;
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
		this.robber = new Robber(new HexLocation(3,3));
		
		for(int i = 0; i < 20; i++)
		{
			hexes.add(new Hex());
		}
	}

	public boolean canBuildRoad(Player player, ItemLocation itemLocation){
    	return true;
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

    public boolean canBuildCity(Player player,ItemLocation itemLocation){

    	for(int i = 0; i < settlements.size(); i++)
    	{
    		if(settlements.get(i).getLocation().equals(itemLocation.getLocation()) && 
    				settlements.get(i).getLocation().getDirection() == itemLocation.getDirection())
    		{
    			if(settlements.get(i).getIndex() == player.getPlayerIndex())
    				return true;
    			else
    				return false;
    		}
    	}
    	
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
    
    
}
