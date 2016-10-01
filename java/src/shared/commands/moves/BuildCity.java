package shared.commands;

import client.model.*;
import shared.locations.*;

public class BuildCity extends MoveCommand{
    private VertexLocation location;
    /**
     *  Build a city.
     *
     *  @param location - Location of the city
     *
     *  @pre You own a settlement on the vertex
     *  @pre have resources
     *
     *  @post resources lost
     *  @post settlement returned
     *  @post city is on the map
     */
    public BuildCity(PlayerIndex index, VertexLocation location){
        super("buildCity", index);
        this.location = location;
    };
}
