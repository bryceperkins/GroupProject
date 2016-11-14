package shared.model.map;

import com.google.gson.annotations.SerializedName;
import shared.definitions.*;
import shared.locations.*;
import shared.model.ResourceList;

public class Port {


    @SerializedName("resource")
    private ResourceType resource;
    private HexLocation location;
    private int ratio;
    private EdgeDirection direction;

    public void setResource(ResourceType resource) {
        this.resource = resource;
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

    public EdgeDirection getDirection() {
        return direction;
    }
}
