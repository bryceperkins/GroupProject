package client.model.player;

import java.util.ArrayList;
import client.data.*;
import client.model.*;
import client.model.map.Port;
import shared.definitions.ResourceType;
import shared.definitions.CatanColor;
import com.google.gson.annotations.SerializedName;

public class Player {
	@SerializedName("cities")
	private int citiesRemaining;
    private CatanColor color;
	@SerializedName("discarded")
    private boolean didDiscard;
	@SerializedName("monuments")
    private int monumentsPlayed;
    private String name;
    private DevCardList newDevCards;
    private DevCardList oldDevCards;
    private ArrayList<Port> ports;
    private int playerID;
    private PlayerIndex playerIndex;
    private boolean playedDevCard;
    private ResourceList resources;
	@SerializedName("roads")
    private int roadsRemaining;
	@SerializedName("settlements")
    private int settlementsRemaining;
	@SerializedName("soldiers")
    private int soldiersPlayed;
    private int userID;
    private int victoryPoints;

    
    public Player(CatanColor color, String name, int playerID, PlayerIndex playerIndex, int userID) {

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
		case WOOD:
			temp.setWood(ratio);
			break;
		case SHEEP:
			temp.setSheep(ratio);
			break;
		case BRICK:
			temp.setBrick(ratio);
			break;
		case ORE:
			temp.setOre(ratio);
			break;
		case WHEAT:
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
		return citiesRemaining;
	}


	public CatanColor getColor() {
		return color;
	}


	public boolean didDiscard() {
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
	
	public void setResources(ResourceList rl)
	{
		this.resources = rl;
	}

    public PlayerInfo toPlayerInfo(){
        PlayerInfo player = new PlayerInfo();
        player.setId(this.playerID);
        player.setName(this.name);
        if (this.getPlayerIndex() != null){
            player.setPlayerIndex(this.playerIndex.getIndex());
        }
        if (this.getColor() != null){
            player.setColor(this.color);
        }
        return player;
    }
}
