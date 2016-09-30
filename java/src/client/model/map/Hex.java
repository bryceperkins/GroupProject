package client.model.map;

import client.model.map.HexLocation;
import client.model.map.Robber;
import client.model.ResourceType;

public class Hex {
	private HexLocation location;
	//private Robber robber;
	private boolean hasRobber;
	private int value;
	private ResourceType resource;

	public Hex(HexLocation location, boolean hasRobber, int value, ResourceType resource){
		this.location = location;
		this.hasRobber = hasRobber;
		this.value = value;
		this.resource = resource;
	};

	public boolean hasRobber(){return true;}

	public boolean canPlaceRobber(){return true;}
}
