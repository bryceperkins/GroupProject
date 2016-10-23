package client.model.map;

import shared.definitions.*;
import shared.locations.*;
import client.model.ResourceList;

public class Port {

    private PortType type;
    private HexLocation location;
    private int ratio;
    private EdgeDirection direction;

    public void setType(PortType getType) {
        this.type = type;
    }

    public void setLocation(HexLocation location) {
        this.location = location;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public void setDirection(EdgeDirection direction) {
        this.direction = direction;
    }

    public Port() {}

    public Port(PortType r, HexLocation hexLocation, int ratio,
			EdgeDirection dir) {
		this.type = r;
		this.location = hexLocation;
		this.ratio = ratio;
		this.direction = dir;
		
	}

	public boolean canTrade(ResourceList resourceList){return true;}

    public PortType getType() {
        return type;
    }
    
    public HexLocation getLocation() {
        return location;
    }
    
    public int getRatio() {
        return ratio;
    }
    
    public EdgeDirection getDirection() {
        return direction;
    }
}
