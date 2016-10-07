package client.model.map;

import shared.locations.HexLocation;

public class Robber {
	
	private HexLocation location;

	public Robber(HexLocation location) {
		super();
		this.location = location;
	}

	public HexLocation getLocation() {
		return location;
	}

	public void setLocation(HexLocation location) {
		this.location = location;
	}

	public int getX() {
		return location.getX();
	}

	public int getY() {
		return getY();
	}
}
