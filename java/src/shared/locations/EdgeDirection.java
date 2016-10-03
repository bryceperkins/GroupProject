package shared.locations;

public enum EdgeDirection
{
	
	NorthWest, North, NorthEast, SouthEast, South, SouthWest;
	
	private EdgeDirection opposite;
    private String value;
	
	static
	{
		NorthWest.opposite = SouthEast;
		North.opposite = South;
		NorthEast.opposite = SouthWest;
		SouthEast.opposite = NorthWest;
		South.opposite = North;
		SouthWest.opposite = NorthEast;
        
        NorthWest.value = "NW";
        NorthEast.value = "NE";
        North.value = "N";
        SouthWest.value = "SW";
        SouthEast.value = "SE";
        South.value = "S";
	}

    public String toString(){
        return value;
    }
	
	public EdgeDirection getOppositeDirection()
	{
		return opposite;
	}
}

