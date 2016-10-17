package client.model.map;

import shared.locations.*;
import client.model.PlayerIndex;

public class Road{
	
	private PlayerIndex owner;
	private VertexLocation location;

	public Road(PlayerIndex owner, VertexLocation location) {
		this.owner = owner;
		this.location = location;
	}

	public PlayerIndex getOwner() {
		return owner;
	}

	public void setOwner(PlayerIndex owner) {
		this.owner = owner;
	}

	public VertexLocation getLocation() {
		return location;
	}

	public void setLocation(VertexLocation loc) {
		this.location = loc;
	}

	
	
}
