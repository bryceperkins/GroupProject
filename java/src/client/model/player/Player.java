package client.model.player;

import java.util.ArrayList;
import client.model.*;
import client.model.map.Port;

public class Player {
	private int cities;
    private Color color;
    private boolean discarded;
    private int monuments;
    private String name;
    private DevCardList newDevCards;
    private DevCardList oldDevCards;
    private ArrayList<Port> ports;
    private int playerID;
    private PlayerIndex playerIndex;
    private boolean playedDevCard;
    private ResourceList resources;
    private int roads;
    private int settlements;
    private int soldiers;
    private int userID;
    private int victoryPoints;

    
    public Player(Color color, String name, int playerID, PlayerIndex playerIndex, int userID) {

		this.color = color;
		this.name = name;
		this.playerID = playerID;
		this.playerIndex = playerIndex;
		this.userID = userID;
		
		this.resources = new ResourceList();
        this.newDevCards = new DevCardList();
        this.oldDevCards = new DevCardList();
        this.ports = new ArrayList<Port>();
        this.cities = 4;
        this.roads = 15;
        this.settlements = 5;
        this.monuments = 0;
        this.soldiers = 0;
        this.victoryPoints = 0;
        this.discarded = false;
        this.playedDevCard = false;
    }

	/**
	 * @param resources
	 * @return whether the player has at least the resources in the passed resource list
	 */
    public boolean hasResources(ResourceList resources) {
		return this.resources.hasResources(resources);
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
		return (cities > 0) && resources.hasResources(temp);
	}
	
	/**
	 * checks if player has the prerequisite resources to build a road
	 * @return true if player has resources, else false
	 */
	public boolean canBuildRoad(){
		ResourceList temp = new ResourceList();
		temp.setBrick(1);
		temp.setWood(1);
		return (roads > 0) &&resources.hasResources(temp);
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
		return (settlements > 0) &&resources.hasResources(temp);
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
	
	public boolean canMakeMaritimeTrade(ResourceType rt)
	{
		int ratio = 4;
		ResourceList temp = new ResourceList();
		
		for(int i = 0; i < ports.size(); i++)
		{
			if(ports.get(i).getResource() == rt)
			{
				ratio = ports.get(i).getRatio();
				break;
			}
		}
		
		switch (rt)
		{
		case wood:
			temp.setWood(ratio);
			break;
		case sheep:
			temp.setSheep(ratio);
			break;
		case brick:
			temp.setBrick(ratio);
			break;
		case ore:
			temp.setOre(ratio);
			break;
		case wheat:
			temp.setWheat(ratio);
			break;
		}
		
		return resources.hasResources(temp);
	}
	
	public void addPort(Port port)
	{
		this.ports.add(port);
	}


	public int getCitiesRemaining() {
		return cities;
	}


	public Color getColor() {
		return color;
	}


	public boolean isDiscarded() {
		return discarded;
	}


	public int getMonuments() {
		return monuments;
	}


	public String getName() {
		return name;
	}


	public DevCardList getNewDevCards() {
		return newDevCards;
	}

	public int getPlayerId() {
		return playerID;
	}


	public DevCardList getOldDevCards() {
		return oldDevCards;
	}


	public ArrayList<Port> getPorts() {
		return ports;
	}


	public int getPlayerID() {
		return playerID;
	}


	public PlayerIndex getPlayerIndex() {
		return playerIndex;
	}


	public boolean isPlayedDevCard() {
		return playedDevCard;
	}


	public ResourceList getResources() {
		return resources;
	}


	public int getRoads() {
		return roads;
	}


	public int getSettlements() {
		return settlements;
	}


	public int getSoldiers() {
		return soldiers;
	}


	public int getUserID() {
		return userID;
	}


	public int getVictoryPoints() {
		return victoryPoints;
	}
	
	public void setResources(ResourceList rl)
	{
		this.resources = rl;
	}
	
	
	
}
