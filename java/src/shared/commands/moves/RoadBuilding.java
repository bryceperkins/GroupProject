package shared.commands;

import shared.locations.*;
import client.model.*;
import com.google.gson.*;

public class RoadBuilding extends MoveCommand {
    private JsonObject spot1;
    private JsonObject spot2;
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
    public RoadBuilding(PlayerIndex index, EdgeLocation s1, EdgeLocation s2){
        super("Road_Building", index.getIndex());
        JsonObject spot1 = new JsonObject();
        spot1.addProperty("x", s1.getHexLoc().getX());
        spot1.addProperty("y", s1.getHexLoc().getY());
        spot1.addProperty("direction", s1.getDir().toString());
    };
}
