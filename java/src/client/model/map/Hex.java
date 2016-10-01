package client.model.map;

import client.model.ResourceType;

public class Hex {
	
	private HexLocation location;
	private Robber robber;
	private int value;
	private ResourceType resource;

	public Hex(HexLocation location, Robber robber, int value, ResourceType resource){
		this.location = location;
		this.robber = robber;
		this.value = value;
		this.resource = resource;
	};
	
	public boolean hasRobber()
	{
		if(robber == null)
			return false;
		else
			return true;
	}
	
	public boolean canPlaceRobber()
	{
		return !this.hasRobber();
	}

	public HexLocation getLocation() {
		return location;
	}

	public Robber getRobber() {
		return robber;
	}

	public int getValue() {
		return value;
	}

	public ResourceType getResource() {
		return resource;
	}
	
	
	
}
