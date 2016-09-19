package shared.commands;

public class BuildCity extends PlayingCommand{
    /**
     *  Build a city.
     *
     *  @param vertexLocation - Location of the city
     *
     *  @pre You own a settlement on the vertex
     *  @pre have resources
     *
     *  @post resources lost
     *  @post settlement returned
     *  @post city is on the map
     */
    private VertexLocation location;
    public BuildCity(location VertexLocation){};
}
