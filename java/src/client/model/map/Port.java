package client.model.map;

import client.model.ResourceType;
import client.model.map.HexLocation;
import client.model.ResourceList;

public class Port {

	private ResourceType resource;
	private HexLocation location;
	private int ratio;
	private Direction direction;

	public Port(ResourceType resource, HexLocation location, int ratio, Direction direction){
		this.resource = resource;
		this.location = location;
		this.ratio = ratio;
		this.direction = direction;
	};

	public boolean canTrade(ResourceList resourceList){return true;}
}
