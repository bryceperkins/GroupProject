package shared.model.map;

import shared.model.Game;
import shared.model.PostProcessor;
import shared.locations.*;
import shared.model.PlayerIndex;

public class Road implements PostProcessor {
	
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


	@Override
	public void postDeserializationSetup(Game game) {
		location.postDeserializationSetup(game);
	}
}
