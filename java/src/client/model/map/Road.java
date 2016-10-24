package client.model.map;

import shared.locations.*;
import client.model.PlayerIndex;

public class Road{
	
	private PlayerIndex owner;
	private EdgeLocation location;

	public Road(PlayerIndex owner, EdgeLocation location) {
		this.owner = owner;
		this.location = location;
	}

	public PlayerIndex getOwner() {
		return owner;
	}

	public void setOwner(PlayerIndex owner) {
		this.owner = owner;
	}

	public EdgeLocation getLocation() {
		return location;
	}

	public void setLocation(EdgeLocation loc) {
		this.location = loc;
	}

	
	
}
