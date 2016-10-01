package client.model.map;

import shared.locations.*;
import client.model.PlayerIndex;

public class Road{
	
	private PlayerIndex index;
	private VertexLocation loc;
	private VertexDirection dir;
	
	public Road(PlayerIndex index, VertexLocation loc, VertexDirection dir) {
		this.index = index;
		this.loc = loc;
		this.dir = dir;
	}

	public PlayerIndex getIndex() {
		return index;
	}

	public void setIndex(PlayerIndex index) {
		this.index = index;
	}

	public VertexLocation getLoc() {
		return loc;
	}

	public void setLoc(VertexLocation loc) {
		this.loc = loc;
	}

	public VertexDirection getDir() {
		return dir;
	}

	public void setDir(VertexDirection dir) {
		this.dir = dir;
	}
	
	
	
}
