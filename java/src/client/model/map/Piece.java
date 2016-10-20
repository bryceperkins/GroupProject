package client.model.map;

import client.model.Game;
import client.model.PlayerIndex;
import client.model.PostProcessor;
import shared.locations.VertexLocation;

public abstract class Piece implements PostProcessor {
	
	private PlayerIndex owner;
	private VertexLocation location;

	public Piece(PlayerIndex index, VertexLocation location) {
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

	public VertexLocation getLocation() {
		return location;
	}

	public void setLocation(VertexLocation location) {
		this.location = location;
	}

	@Override
	public void postDeserializationSetup(Game game) {
		location.postDeserializationSetup(game);
	}
}
