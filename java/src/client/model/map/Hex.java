package client.model.map;

import shared.locations.HexLocation;
import client.model.map.Robber;
import shared.definitions.ResourceType;

public class Hex {
	private HexLocation location;
	//private Robber robber;
	private boolean hasRobber;
	private int number;
	private ResourceType resource;

	public Hex() {}

	public Hex(HexLocation location, boolean hasRobber, int value, ResourceType resource){};

	public HexLocation getLocation() {
		return location;
	}

	public void setLocation(HexLocation location) {
		this.location = location;
	}

	public boolean isHasRobber() {
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

	public boolean hasRobber(){return true;}

	public boolean canPlaceRobber(){return true;}
}
