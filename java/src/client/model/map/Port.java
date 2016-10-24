package client.model.map;

import com.google.gson.annotations.SerializedName;
import shared.definitions.*;
import shared.locations.*;
import client.model.ResourceList;

public class Port {

    @SerializedName("resource")
    private ResourceType type;
    private HexLocation location;
    private int ratio;
    private EdgeDirection direction;

    public void setType(ResourceType getType) {
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

    public Port(ResourceType r, HexLocation hexLocation, int ratio,
			EdgeDirection dir) {
		this.type = r;
		this.location = hexLocation;
		this.ratio = ratio;
		this.direction = dir;

	}

	public boolean canTrade(ResourceList resourceList){return true;}

    public ResourceType getType() {
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
