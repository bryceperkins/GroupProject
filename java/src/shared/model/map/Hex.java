package shared.model.map;

import java.io.Serializable;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.Game;
import shared.model.PostProcessor;
import shared.definitions.ResourceType;

import java.util.HashSet;
import java.util.Set;

public class Hex implements PostProcessor, Serializable {
	private HexLocation location;
	private boolean hasRobber;
	private int number;
	private ResourceType resource;

	public Hex() {}

	public Hex(HexLocation location, boolean hasRobber, int value, ResourceType resource){
		this.location = location;
		this.hasRobber = hasRobber;
		this.number = value;
		this.resource = resource;
	}

	public HexLocation getLocation() {
		return location;
	}

	public void setLocation(HexLocation location) {
		this.location = location;
	}

	public boolean hasRobber() {
		return hasRobber;
	}

	public void setHasRobber(boolean hasRobber) {
		this.hasRobber = hasRobber;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public ResourceType getResource() {
		return resource;
	}

	public void setResource(ResourceType resource) {
		this.resource = resource;
	}

	public Set<VertexLocation> getNormalizedVertexLocations() {
		Set<VertexLocation> locations = new HashSet<>();
		for (VertexDirection direction : VertexDirection.values()) {
			locations.add(new VertexLocation(location, direction).getNormalizedLocation());
		}
		return locations;
	}

	@Override
	public void postDeserializationSetup(Game game) {
		HexLocation robberLocation = game.getMap().getRobber();
		hasRobber = location.equals(robberLocation);
	}
}
