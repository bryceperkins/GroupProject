package client.model.player;

import java.util.ArrayList;
import client.model.*;
import client.model.player.*;
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


    
    public Player(Color color, String name, int playerID, int playerIndex, int userID) {

		this.color = color;
		this.name = name;
		this.playerID = playerID;
		this.playerIndex = playerIndex;
		this.userID = userID;
		
		this.resources = new ResourceList();
        this.newDevCards = new DevCardList();
        this.oldDevCards = new DevCardList();
        this.ports = new ArrayList<Port>();
        this.citiesRemaining = 4;
        this.roadsRemaining = 15;
        this.settlementsRemaining = 5;
        this.monumentsPlayed = 0;
        this.soldiersPlayed = 0;
        this.victoryPoints = 0;
        this.didDiscard = false;
        this.playedDevCard = false;
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


	public int getCitiesRemaining() {
		return citiesRemaining;
	}


	public Color getColor() {
		return color;
	}


	public boolean isDidDiscard() {
		return didDiscard;
	}


	public int getMonumentsPlayed() {
		return monumentsPlayed;
	}


	public String getName() {
		return name;
	}


	public DevCardList getNewDevCards() {
		return newDevCards;
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


	public int getPlayerIndex() {
		return playerIndex;
	}


	public boolean isPlayedDevCard() {
		return playedDevCard;
	}


	public ResourceList getResources() {
		return resources;
	}


	public int getRoadsRemaining() {
		return roadsRemaining;
	}


	public int getSettlementsRemaining() {
		return settlementsRemaining;
	}


	public int getSoldiersPlayed() {
		return soldiersPlayed;
	}


	public int getUserID() {
		return userID;
	}


	public int getVictoryPoints() {
		return victoryPoints;
	}
	
	
	
}