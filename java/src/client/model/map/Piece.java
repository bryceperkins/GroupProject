package client.model.map;

public abstract class Piece {
	
	private int playerIndex;
	private ItemLocation location;
	
	public Piece(int playerIndex, ItemLocation location) {
		super();
		this.playerIndex = playerIndex;
		this.location = location;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public ItemLocation getLocation() {
		return location;
	}

	public void setLocation(ItemLocation location) {
		this.location = location;
	}
	
	
}
