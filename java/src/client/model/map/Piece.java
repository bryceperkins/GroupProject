package client.model.map;

import client.model.PlayerIndex;

public abstract class Piece {
	
	private PlayerIndex owner;
	private ItemLocation location;

	public Piece(PlayerIndex index, ItemLocation location) {
		super();
		this.owner = index;
		this.location = location;
	}

	public PlayerIndex getOwner() {
		return owner;
	}

	public void setOwner(PlayerIndex index) {
		this.owner = owner;
	}

	public ItemLocation getLocation() {
		return location;
	}

	public void setLocation(ItemLocation location) {
		this.location = location;
	}
	
	
}
