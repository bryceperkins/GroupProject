package shared.commands;

public class BuildRoad extends PlayingCommand {
    /**
     *  Build a Road.
     *
     *  @param free - If this road is built for free
     *  @param location - EdgeLocation
     *
     *  @pre Location is open
     *  @pre Location is not on water
     *  @pre You have the resources
     *  @pre not next to another settlement
     *  @pre connected to a road if not during setup
     *  @pre connected to the settlement if during setup
     *
     *  @post resources lost
     *  @post Settlement is on the map
     *  @post longest road gained if necessary
     */
    private Boolean free;
    private EdgeLocation location;
    public BuildRoad(Boolean free, EdgeLocation location){};
}
