package client.model.map;

import java.util.*;

import client.model.player.*;
import client.model.*;
import shared.locations.*;

public class Map {

<<<<<<< 5fb7bf6a333ee51b235d9e4cb5cf74e524ce54d5
    private List<Hex> hexes;
    private List<Port> ports;
    private List<VertexLocation> settlements;
    private List<VertexLocation> cities;
    private int radius;
    private Robber robber;

    /**
     * for each hex that has integer diceRoll as a hexValue, 
     * the resource of the hex will be given to each player that is on an adjacent hex intersection
     * @param diceRoll integer that dictates which hex will produce resources this round. Dice roll must be between 2-12
     */
    public void gatherResources(Integer diceRoll){}

    public boolean canBuildRoad(Player player, ItemLocation itemLocation){return true;}

    public boolean canBuildSettlement(Player player, ItemLocation itemLocation){return true;}

    public boolean canBuildCity(Player player,ItemLocation itemLocation){return true; }

    public void setHexes(List<Hex> hexes) {
        this.hexes = hexes;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }

    public void setSettlements(List<VertexLocation> settlements) {
        this.settlements = settlements;
    }

    public void setCities(List<VertexLocation> cities) {
        this.cities = cities;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setRobber(Robber robber) {
        this.robber = robber;
    }
=======
	private ArrayList<Hex> hexes;
	private ArrayList<Port> ports;
	private ArrayList<VertexLocation> settlements;
	private ArrayList<VertexLocation> cities;
	private int radius;
	private Robber robber;

	/**
	 * for each hex that has integer diceRoll as a hexValue, 
	 * the resource of the hex will be given to each player that is on an adjacent hex intersection
	 * @param diceRoll integer that dictates which hex will produce resources this round. Dice roll must be between 2-12
	 */
	public void gatherResources(Integer diceRoll){}
>>>>>>> add tests

	public boolean canBuildRoad(Player player, ItemLocation itemLocation){return true;}

	public boolean canBuildSettlement(Player player, ItemLocation itemLocation){return true;}

	public boolean canBuildCity(Player player,ItemLocation itemLocation){return true; }

}
