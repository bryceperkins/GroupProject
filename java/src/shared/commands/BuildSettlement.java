package shared.commands;

import client.model.*;
import shared.locations.*;

public class BuildSettlement extends PlayingCommand{
    private Boolean free;
    private VertexLocation location;
    /**
     *  Build a Settlement.
     *
     *  @param free - If this settlement is free
     *  @param location - VertexLocation
     *
     *  @pre Location is open
     *  @pre Location is not on water
     *  @pre You have the resources
     *  @pre not next to another settlement
     *  @pre connected to a road if not during setup
     *
     *  @post resources lost
     *  @post Settlement is on the map
     */
    public BuildSettlement(Boolean free, VertexLocation location){};
}
