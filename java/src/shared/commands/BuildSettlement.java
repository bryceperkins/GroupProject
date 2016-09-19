package shared.commands;

public class BuildSettlement extends PlayingCommand{
    /**
     *  Build a Settlement.
     *
     *  @param free - If this settlement is free
     *  @param locaiton - VertexLocation
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
    private Boolean free;
    private VertexLocation location;
    public BuildSettlement(Boolean free, Vertexlocation location){};
}
