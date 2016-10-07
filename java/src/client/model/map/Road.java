package client.model.map;

import shared.locations.*;
import client.model.PlayerIndex;

public class Road{
	
	private PlayerIndex index;
	private VertexLocation location;

	public Road(PlayerIndex index, VertexLocation location) {
		this.index = index;
		this.location = location;
	}

	public PlayerIndex getIndex() {
		return index;
	}

	public void setIndex(PlayerIndex index) {
		this.index = index;
	}

	public VertexLocation getLocation() {
		return location;
	}

	public void setLocation(VertexLocation loc) {
		this.location = loc;
	}

	
	
}
