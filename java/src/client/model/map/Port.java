package client.model.map;

import client.model.*;

public class Port {
	private ResourceType resource;
	private HexLocation location;
	private int ratio;
	private HexDirection direction;
	
	public Port(ResourceType resource, HexLocation location, int ratio, HexDirection d) {
		super();
		this.resource = resource;
		this.location = location;
		this.ratio = ratio;
		this.direction = d;
	}

	public ResourceType getResource() {
		return resource;
	}
	public void setResource(ResourceType resource) {
		this.resource = resource;
	}
	public HexLocation getLocation() {
		return location;
	}
	public void setLocation(HexLocation location) {
		this.location = location;
	}
	public int getRatio() {
		return ratio;
	}
	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
	public HexDirection getDirection() {
		return direction;
	}
	public void setDirection(HexDirection direction) {
		this.direction = direction;
	}
	
	
	
}
