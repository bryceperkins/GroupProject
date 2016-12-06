package shared.locations;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public enum EdgeDirection implements Serializable
{
    @SerializedName("NW")
    NorthWest,
    @SerializedName("N")
    North, 
    @SerializedName("NE")
    NorthEast, 
    @SerializedName("SE")
    SouthEast, 
    @SerializedName("S")
    South, 
    @SerializedName("SW")
    SouthWest;
	
	private transient EdgeDirection opposite;
	
	static
	{
		NorthWest.opposite = SouthEast;
		North.opposite = South;
		NorthEast.opposite = SouthWest;
		SouthEast.opposite = NorthWest;
		South.opposite = North;
		SouthWest.opposite = NorthEast;
	}

	public EdgeDirection getOppositeDirection()
	{
		return opposite;
	}
}

