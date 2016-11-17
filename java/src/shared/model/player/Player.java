package shared.model.player;

import java.util.ArrayList;
import client.data.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import shared.model.*;
import shared.model.map.*;
import shared.definitions.*;
import com.google.gson.annotations.SerializedName;
import shared.locations.*;

public class Player implements PostProcessor {
	@SerializedName("cities")
	private int citiesRemaining;
    @SerializedName("color")
    private CatanColor color;
	@SerializedName("discarded")
    private boolean didDiscard;
	@SerializedName("monuments")
    private int monumentsPlayed;
    @SerializedName("name")
    private String name;
    private DevCardList newDevCards;
    private DevCardList oldDevCards;
    private ArrayList<Port> ports;
    @SerializedName("id")
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

    public void transferNewDevCards() {
        oldDevCards.addDevCardList(newDevCards);
		newDevCards.clear();
	}
    
    public void setColor(CatanColor color){
        this.color = color;
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
		ResourceList resources = offer.getOffer();
		if (resources.getWood() >= 0){
			resources.setWood(0);
		} else {
			resources.setWood(resources.getWood()*-1);
		}
		if (resources.getBrick() >= 0){
			resources.setBrick(0);
		} else {
			resources.setBrick(resources.getBrick()*-1);
		}
		if (resources.getSheep() >= 0){
			resources.setSheep(0);
		} else {
			resources.setSheep(resources.getSheep()*-1);
		}
		if (resources.getOre() >= 0){
			resources.setOre(0);
		} else {
			resources.setOre(resources.getOre()*-1);
		}
		if (resources.getWheat() >= 0){
			resources.setWheat(0);
		} else {
			resources.setWheat(resources.getWheat()*-1);
		}
		return this.resources.hasResources(resources);
	}
	
	public boolean canMakeMaritimeTrade(PortType pt)
	{
		int ratio = 4;
		ResourceList temp = new ResourceList();
		ResourceType type = null;
		PortType test = pt;
		switch (pt){
		case WOOD:
			type = ResourceType.WOOD;
			break;
		case SHEEP:
			type = ResourceType.SHEEP;
			break;
		case WHEAT:
			type = ResourceType.WHEAT;
			break;
		case BRICK:
			type = ResourceType.BRICK;
			break;
		case ORE:
			type = ResourceType.ORE;
			break;
		}
		for(int i = 0; i < ports.size(); i++)
		{
			if(PortType.fromResourceType(ports.get(i).getResource()).equals(pt) && ports.get(i).getRatio() < ratio){
				ratio = ports.get(i).getRatio();
				test = pt;
			}
			if (ports.get(i).getResource() == null && ports.get(i).getRatio() < ratio){
				ratio = 3;
				test = PortType.THREE;
			}
		}
		switch (test)
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
		case THREE:
			if (type == ResourceType.WOOD) temp.setWood(ratio);
			if (type == ResourceType.SHEEP) temp.setSheep(ratio);
			if (type == ResourceType.BRICK) temp.setBrick(ratio);
			if (type == ResourceType.ORE) temp.setOre(ratio);
			if (type == ResourceType.WHEAT) temp.setWheat(ratio);
		}
		return resources.hasResources(temp);
	}

	public void addDevCard(DevCardType devCard)
	{
		this.newDevCards.addCard(devCard);
	}

	public void removeDevCard(DevCardType devCard)
	{
		this.oldDevCards.removeCard(devCard);
	}
	
	public void addPort(Port port)
	{
		this.ports.add(port);
	}


	public int getCitiesRemaining() {
		return citiesRemaining;
	}
	public void setCitiesRemaining(int cities){this.citiesRemaining = cities;}


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

	public int getPlayerID() {
		return playerID;
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

	public void setRoadsRemaining(int roads){this.roadsRemaining = roads;}


	public int getSettlementsRemaining() {
		return settlementsRemaining;
	}
	public void setSettlementsRemaining(int settlements){this.settlementsRemaining = settlements;}


	public int getSoldiersPlayed() {
		return soldiersPlayed;
	}

	public int getVictoryPoints() {
		return victoryPoints;
	}

	public void setVictoryPoints(int num)
	{
		this.victoryPoints = num;
	}
	
	public void setResources(ResourceList rl)
	{
		this.resources = rl;
	}

    public void setPlayerID(int id){
        this.playerID = id;
    }
    public PlayerInfo toPlayerInfo(){
        PlayerInfo player = new PlayerInfo();
        player.setId(getPlayerID());
        player.setName(getName());
        if (this.getPlayerIndex() != null){
            player.setPlayerIndex(this.playerIndex.getIndex());
        }
        if (this.getColor() != null){
            player.setColor(getColor());
        }
        return player;
    }

	@Override
	public int hashCode() {
		return (playerID + 1) * 31;
	}

	@Override
	public void postDeserializationSetup(Game game) {
		Map map = game.getMap();

		// get player ports
        ports = new ArrayList<>();

		// Puts locations of all player cities and settlements into a set
		Set<HexLocation> playerLocations = new HashSet<>();
		for (City city : map.getCities()) {
			if (city.getOwner().equals(playerIndex)) {
				playerLocations.add(city.getLocation().getHexLoc());
			}
		}
		for (Settlement settlement : map.getSettlements()) {
			if (settlement.getOwner().equals(playerIndex)) {
				playerLocations.add(settlement.getLocation().getHexLoc());
			}
		}

		// Check player locations against port locations
		for (Port p : map.getPorts()) {
			if (playerLocations.contains(p.getLocation())) {
				ports.add(p);
			}
		}
	}
}
