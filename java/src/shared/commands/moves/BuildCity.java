package shared.commands;

import shared.model.*;
import shared.locations.*;
import server.handlers.iServerFacade;
import server.facades.MovesFacade;

public class BuildCity extends MoveCommand{
    private VertexLocation vertexLocation;
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
        this.vertexLocation = location;
    };

    public String serverExecute(iServerFacade f){
        MovesFacade facade = (MovesFacade) f;
        vertexLocation.setHexLoc(new HexLocation(vertexLocation.getX(), vertexLocation.getY()));
        return facade.buildCity(getIndex(), vertexLocation);
    }
}
