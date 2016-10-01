package shared.commands;

import shared.locations.*;
import client.model.*;

public class RoadBuilding extends MoveCommand {
    private EdgeLocation spot1;
    private EdgeLocation spot2;
    /**
     *  RoadBuilding
     *
     *  Build two roads for free
     *
     *  @param spot1 - Location for first road
     *  @param spot2 - Location for second road
     *
     *  @pre Location is open
     *  @pre Location is not on water
     *  @pre You have the resources
     *  @pre not next to another settlement
     *  @pre connected to a road 
     *  @pre connected to the settlement
     *  @pre you have 2 unused roads
     *
     *  @post you have 2 fewer roads
     *  @post 2 new roads appear
     *  @post longest road gained if necessary
     */
    public RoadBuilding(PlayerIndex index, EdgeLocation spot1, EdgeLocation spot2){
        super("Road_Building", index);
        this.spot1 = spot1;
        this.spot2 = spot2;
    };
}
