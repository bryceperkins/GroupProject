package shared.commands;

import shared.model.*;
import shared.locations.*;
import server.handlers.iServerFacade;
import server.facades.MovesFacade;

public class BuildRoad extends MoveCommand {
    private Boolean free;
    private EdgeLocation roadLocation;
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
    public BuildRoad(PlayerIndex index, EdgeLocation location, Boolean free){
        super("buildRoad", index);
        this.free = free;
        this.roadLocation = location;
    };
    public String serverExecute(iServerFacade f){
        MovesFacade facade = (MovesFacade) f;
        return facade.buildRoad(getIndex(), free, roadLocation);
    }
}
