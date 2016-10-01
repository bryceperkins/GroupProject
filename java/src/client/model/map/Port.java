package client.model.map;

import client.model.ResourceType;
import client.model.map.HexLocation;
import client.model.ResourceList;

public class Port {

	private ResourceType resource;
	private HexLocation location;
	private int ratio;
	private Direction direction;

	public ResourceType getResource() {
		return resource;
	}

	public HexLocation getLocation() {
		return location;
	}

	public int getRatio() {
		return ratio;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setResource(ResourceType resource) {
		this.resource = resource;
	}

	public void setLocation(HexLocation location) {
		this.location = location;
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Port() {}

	public Port(ResourceType resource, HexLocation location, int ratio, Direction direction){
		this.resource = resource;
		this.location = location;
		this.ratio = ratio;
		this.direction = direction;
	};

	public boolean canTrade(ResourceList resourceList){return true;}
}
