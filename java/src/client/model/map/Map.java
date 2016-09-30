package client.model.map;

import java.util.ArrayList;

public class Map {
	
	
	private ArrayList<Hex> hexes;
	private ArrayList<Port> ports;
	private ArrayList<Settlement> settlements;
	private ArrayList<City> cities;
	private ArrayList<Road> roads;
	private int radius;
	private Robber robber;
	
	public Map()
	{
		hexes = new ArrayList<Hex>();
		ports = new ArrayList<Port>();
		settlements = new ArrayList<Settlement>();
		cities = new ArrayList<City>();
		roads = new ArrayList<Road>();
		radius = 5;
		//TODO find hexlocation of center tile
		robber = new Robber(new HexLocation(0,0));
		
	}
	
	public boolean canBuildRoad(ItemLocation loc)
	{
		/*
		 * THINGS TO CONSIDER WHILE CHECKING FOR ROAD
		 * 1.DOES EDGE HAVE A ROAD?
		 * 2.Does the location have a road on an adjacent edge or is settlement on adjacent edge
		 * 
		 */
		
		//theres got to be a better way to do this...
		boolean hasRoad = false;
		boolean isConnected = false;
		
		//1.
		for(int i = 0; i < roads.size(); i++)
		{
			if(loc.equals(roads.get(i)))
			{
				hasRoad = true;
			}
		}
		
		//2.
		
		return true;
	}
	
	public boolean canBuildSettlement(ItemLocation loc)
	{
		/*
		 * 1. PROXIMITY OF OTHER SETTLEMENTS
		 * 2. is settlement already in location
		 */
		return false;
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
	public void gatherResources(Integer diceRoll){}

}
