package shared.model.map;

import shared.model.player.*;
import java.io.Serializable;

public abstract class Building implements Serializable {
	
	/**
	 * checks if player can place a building a give intersection
	 * @param player input player to check intersection neighbors against. Player must be a valid player
	 * @return true if intersection does not have a settlement as a neighbor AND a neighboring edge must contain a road owned by the inputed player
	 */
	public boolean canBuildSettlement(Player player){return true;}
	
	/**
	 * checks if input player can upgrade a settlement to a city
	 * @param player input player to check if player owns selected settlement. 
	 * @return if intersection contains a settlement that is controlled by the input player, then return true; else fase
	 */
	public boolean canBuildCity(Player player){return true;}
}
