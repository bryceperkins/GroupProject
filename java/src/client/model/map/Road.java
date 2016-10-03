package client.model.map;

import shared.locations.*;
import client.model.PlayerIndex;

public class Road{
	
	private PlayerIndex index;
	private VertexLocation location;
	private VertexDirection direction;
	
	public Road(PlayerIndex index, VertexLocation loc, VertexDirection dir) {
		this.index = index;
		this.location = loc;
		this.direction = dir;
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

	public VertexDirection getDirection() {
		return direction;
	}

	public void setDirection(VertexDirection dir) {
		this.direction = dir;
	}
	
	
	
}
