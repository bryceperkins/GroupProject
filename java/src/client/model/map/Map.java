package client.model.map;

import java.util.*;

import client.model.player.*;
import client.model.*;
import shared.locations.*;

public class Map {

	private List<Hex> hexes;
	private List<Port> ports;
	private List<Road> roads;
	private List<Settlement> settlements;
	private List<City> cities;
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

	public void setSettlements(List<Settlement> settlements) {
		this.settlements = settlements;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public List<Road> getRoads() {
		return roads;
	}

	public void setRoads(List<Road> roads) {
		this.roads = roads;
	}

	public List<Hex> getHexes() {
        return hexes;
    }

    public List<Port> getPorts() {
        return ports;
    }

    public List<Settlement> getSettlements() {
        return settlements;
    }

    public List<City> getCities() {
        return cities;
    }

    public int getRadius() {
        return radius;
    }

    public Robber getRobber() {
        return robber;
    }

    public void setRobber(Robber robber) {
		this.robber = robber;
	}
}
