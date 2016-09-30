package client.model.map;

import java.util.ArrayList;
import java.util.HashMap;

public class Map {
	
	
	private ArrayList<Hex> hexes;
	private ArrayList<Port> ports;
	private ArrayList<Settlement> settlements;
	private ArrayList<City> cities;
	private ArrayList<Road> roads;
	private HashMap<Integer, ArrayList<Hex>> hexByTileValue;
	private int radius;
	private Robber robber;
	
	public Map()
	{
		hexes = new ArrayList<Hex>();
		ports = new ArrayList<Port>();
		settlements = new ArrayList<Settlement>();
		cities = new ArrayList<City>();
		roads = new ArrayList<Road>();
		hexByTileValue = new HashMap<Integer, ArrayList<Hex>>();
		radius = 3;
		//TODO find hexlocation of center tile
		robber = new Robber(new HexLocation(3,3));
		
		for(int i = 0; i < hexes.size(); i++)
		{
			if(hexByTileValue.containsKey(hexes.get(i).getValue()))
			{
				hexByTileValue.get(hexes.get(i).getValue()).add(hexes.get(i));
			}
			else
			{
				hexByTileValue.put(hexes.get(i).getValue(), new ArrayList<Hex>());
				hexByTileValue.get(hexes.get(i).getValue()).add(hexes.get(i));
			}
		}
		
	}
	
	public boolean canBuildRoad(ItemLocation loc)
	{
		/*
		 * THINGS TO CONSIDER WHILE CHECKING FOR ROAD
		 * 1.DOES EDGE HAVE A ROAD?
		 * 2.a. Does the location have a road on an adjacent edge 
		 * 2.b. Is settlement on adjacent edge
		 * 
		 */
		
		//theres got to be a better way to do this...
		boolean doesNotHaveRoad = true;
		boolean isConnected = false;
		
		//1.
		for(int i = 0; i < roads.size(); i++)
		{
			if(loc.equals(roads.get(i)))
			{
				doesNotHaveRoad = false;
			}
		}
		
		//2.a.
		//TODO
		
		//2.b.
		//TODO
		
		return doesNotHaveRoad && isConnected;
	}
	
	public boolean canBuildSettlement(ItemLocation loc)
	{
		/*TODO
		 * 1. PROXIMITY OF OTHER SETTLEMENTS
		 * 2. is settlement/city already in location
		 */
		boolean isSettlementTooClose = false;
		boolean isSettlementInLocation = false;
		
		for(int i = 0 ; i < settlements.size(); i++)
		{
			if(settlements.get(i).getLocation().equals(loc))
				isSettlementInLocation = true;
			if(i < cities.size())
			{
				if(cities.get(i).equals(loc))
					isSettlementInLocation = true;
			}
		}
		
		return !isSettlementTooClose && !isSettlementInLocation;
	}
	
	public boolean canBuildCity(ItemLocation loc, int playerIndex)
	{
		/*
		 * does location have settlement AND is this settlement owned by the same player
		 */
		for(int i = 0; i < settlements.size(); i++)
		{
			if(loc.equals(settlements.get(i).getLocation()))
			{
				if(settlements.get(i).getPlayerIndex() == playerIndex)
				{
					return true;
				}
				else
					return false;
			}
		}
		return false;
	}
	
	
	/**
	 * for each hex that has integer diceRoll as a hexValue, 
	 * the resource of the hex will be given to each player that is on an adjacent hex intersection
	 * @param diceRoll integer that dictates which hex will produce resources this round. Dice roll must be between 2-12
	 */
	public void gatherResources(Integer diceRoll){
		
		ArrayList<Hex> hexToGiveResource = hexByTileValue.get(diceRoll);
		
		for(int i = 0; i < hexToGiveResource.size(); i++)
		{
			hexToGiveResource.get(i).getResource();
		}
		
	}

}
