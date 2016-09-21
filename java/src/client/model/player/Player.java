package player;


public class Player {
	
	/**
	 * triggers by a player rolling a seven or a robber being moved to a hex tile controlled by a player
	 * forces a player to discard half of his resource list if his resourcelist total is greater than seven
	 */
	public void robberEvent(){}
	
	/**
	 * incremented values in player resourcelist by inputting a resourcelist
	 * addList must be nonempty 
	 * @param addList resourceList containing the resource numbers to be added to player resourcelist
	 */
	public void addResources(ResourceList addList){}
	
	/**
	 * checks if player has the prerequisite resources to build a city
	 * @return true if player has necessary resources, else false
	 */
	public boolean canBuildCity(){return true;}
	
	/**
	 * checks if player has the prerequisite resources to build a road
	 * @return true if player has resources, else false
	 */
	public boolean canBuildRoad(){return true;}
	
	/**
	 * checks if player has resources required to build a settlement
	 * @return true if player has resources, else false
	 */
	public boolean canBuildSettlement(){return true;}
	
	/**
	 * checks if player has resources to buy a devcard
	 * @return true if resources are present, else false
	 */
	public boolean canBuyDevCard(){return true;}
	
	/**
	 * checks if player can make a TradeOffer to another player
	 * offer must be nonempty. TradeOffer must involved this player object
	 * @param offer trader offer to check resource amount against 
	 * @return true if player can make a trade, else false2
	 */
	public boolean canMakeTrade(){return true;}
}
