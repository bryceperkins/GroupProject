package client.model.map;

import client.model.PlayerIndex;

public abstract class Piece {
	
	private PlayerIndex index;
	private ItemLocation location;
	
	public Piece(PlayerIndex index, ItemLocation location) {
		super();
		this.index = index;
		this.location = location;
	}

	public PlayerIndex getIndex() {
		return index;
	}

	public void setIndex(PlayerIndex index) {
		this.index = index;
	}

	public ItemLocation getLocation() {
		return location;
	}

	public void setLocation(ItemLocation location) {
		this.location = location;
	}
	
	
}
