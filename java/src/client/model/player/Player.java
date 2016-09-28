package client.model.player;

import java.util.ArrayList;

import client.model.*;
import client.model.map.Port;

public class Player {
	private int citiesRemaining;
    private Color color;
    private boolean didDiscard;
    private int monumentsPlayed;    
    private String name;
    private DevCardList newDevCards;
    private DevCardList oldDevCards;
    private ArrayList<Port> ports;
    private int playerID;
    private int playerIndex;
    private boolean playedDevCard;
    private ResourceList resources;
    private int roadsRemaining;
    private int settlementsRemaining;
    private int soldiersPlayed;
    private int userID;
    private int victoryPoints;


    public Player(String name, Color color, int id){
        this.name = name;
        this.color = color;
        this.playerID = id;
        
        //TODO 
        //playerIndex=?
        //UserID=?
        //playerID=?
        
        this.citiesRemaining = 4;
        this.roadsRemaining = 15;
        this.settlementsRemaining = 5;
        this.monumentsPlayed = 0;
        this.soldiersPlayed = 0;
        this.ports = new ArrayList<Port>();
        this.victoryPoints = 0;
    };

    public String getName(){
        return this.name;
    }
	
	/**
	 * triggers by a player rolling a seven or a robber being moved to a hex tile controlled by a player
	 * forces a player to discard half of his resource list if his resourcelist total is greater than seven
	 */
	public void robberEvent(){
		
		if (resources.getTotal() > 7)
		{
			int numberToDiscard = resources.getTotal()/2;
			
			if(resources.getTotal()%2 == 1)
				numberToDiscard++;
			
			//FIXME
			//do something here to choose cards to discard

			
		}
	}
	
	/**
	 * incremented values in player resourcelist by inputting a resourcelist
	 * addList must be nonempty 
	 * @param addList resourceList containing the resource numbers to be added to player resourcelist
	 */
	public void addResources(ResourceList addList)
	{
		resources.addResources(addList);
	}
	
	/**
	 * checks if player has the prerequisite resources to build a city
	 * @return true if player has necessary resources, else false
	 */
	public boolean canBuildCity()
	{
		ResourceList temp = new ResourceList();
		temp.setWheat(2);
		temp.setOre(3);
		return (citiesRemaining > 0) && resources.hasResources(temp);
	}
	
	/**
	 * checks if player has the prerequisite resources to build a road
	 * @return true if player has resources, else false
	 */
	public boolean canBuildRoad(){
		ResourceList temp = new ResourceList();
		temp.setBrick(1);
		temp.setWood(1);
		return (roadsRemaining > 0) &&resources.hasResources(temp);
	}
	
	/**
	 * checks if player has resources required to build a settlement
	 * @return true if player has resources, else false
	 */
	public boolean canBuildSettlement(){
		ResourceList temp = new ResourceList();
		temp.setWheat(1);
		temp.setSheep(1);
		temp.setWood(1);
		temp.setBrick(1);
		return (settlementsRemaining > 0) &&resources.hasResources(temp);
		}
	
	/**
	 * checks if player has resources to buy a devcard
	 * @return true if resources are present, else false
	 */
	public boolean canBuyDevCard(){		
		ResourceList temp = new ResourceList();
		temp.setWheat(1);
		temp.setOre(1);
		temp.setSheep(1);
		return resources.hasResources(temp);
	}
	
	/**
	 * checks if player can make a TradeOffer to another player
	 * offer must be nonempty. TradeOffer must involved this player object
	 * @param offer trader offer to check resource amount against 
	 * @return true if player can make a trade, else false2
	 */
	public boolean canMakeTrade(TradeOffer offer){
		
		return resources.hasResources(offer.getOffer());
	}

	
	
}
