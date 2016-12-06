package shared.locations;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public enum VertexDirection implements Serializable
{
    @SerializedName("W")
	West, 
    @SerializedName("NW")
    NorthWest, 
    @SerializedName("NE")
    NorthEast, 
    @SerializedName("E")
    East, 
    @SerializedName("SE")
    SouthEast, 
    @SerializedName("SW")
    SouthWest;

	
	private VertexDirection opposite;
	
	static
	{
		West.opposite = East;
		NorthWest.opposite = SouthEast;
		NorthEast.opposite = SouthWest;
		East.opposite = West;
		SouthEast.opposite = NorthWest;
		SouthWest.opposite = NorthEast;
	}
	
	public VertexDirection getOppositeDirection()
	{
		return opposite;
	}
}

