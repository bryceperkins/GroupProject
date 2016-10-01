package client.model.map;

import client.model.HexDirection;
import client.model.ResourceType;
import client.model.map.HexLocation;
import client.model.ResourceList;

public class Port {

    private ResourceType resource;
    private HexLocation location;
    private int ratio;
    private HexDirection direction;

    public void setResource(ResourceType resource) {
        this.resource = resource;
    }

    public void setLocation(HexLocation location) {
        this.location = location;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public void setDirection(HexDirection direction) {
        this.direction = direction;
    }

    public Port() {}

    public Port(ResourceType r, HexLocation hexLocation, int ratio,
			HexDirection dir) {
		this.resource = r;
		this.location = hexLocation;
		this.ratio = ratio;
		this.direction = dir;
		
	}

	public boolean canTrade(ResourceList resourceList){return true;}

    public ResourceType getResource() {
        return resource;
    }
    
    public HexLocation getLocation() {
        return location;
    }
    
    public int getRatio() {
        return ratio;
    }
    
    public HexDirection getDirection() {
        return direction;
    }
}
