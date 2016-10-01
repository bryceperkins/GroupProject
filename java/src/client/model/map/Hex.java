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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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
