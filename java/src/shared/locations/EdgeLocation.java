package shared.locations;

import client.model.Game;
import client.model.PostProcessor;

import java.util.List;
import java.util.ArrayList;


/**
 * Represents the location of an edge on a hex map
 */
public class EdgeLocation implements PostProcessor
{
	
	private transient HexLocation hexLoc;
	private EdgeDirection direction;
    private int x;
    private int y;
	
	public EdgeLocation(HexLocation hexLoc, EdgeDirection dir)
	{
		setHexLoc(hexLoc);
		setDir(dir);
        this.x = hexLoc.getX();
        this.y = hexLoc.getY();
	}
	
	public HexLocation getHexLoc()
	{
		return hexLoc;
	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}
	
	private void setHexLoc(HexLocation hexLoc)
	{
		if(hexLoc == null)
		{
			throw new IllegalArgumentException("hexLoc cannot be null");
		}
		this.hexLoc = hexLoc;
	}
	
	public EdgeDirection getDir()
	{
		return direction;
	}
	
	private void setDir(EdgeDirection dir)
	{
		this.direction = dir;
	}
	
	@Override
	public String toString()
	{
		return "EdgeLocation [hexLoc=" + hexLoc + ", dir=" + direction + "]";
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((hexLoc == null) ? 0 : hexLoc.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		EdgeLocation other = (EdgeLocation)obj;
		if(direction != other.direction)
			return false;
		if(hexLoc == null)
		{
			if(other.hexLoc != null)
				return false;
		}
		else if(!hexLoc.equals(other.hexLoc))
			return false;
		return true;
	}
	
	/**
	 * Returns a canonical (i.e., unique) value for this edge location. Since
	 * each edge has two different locations on a map, this method converts a
	 * hex location to a single canonical form. This is useful for using hex
	 * locations as map keys.
	 * 
	 * @return Normalized hex location
	 */
	public EdgeLocation getNormalizedLocation()
	{
		
		// Return an EdgeLocation that has direction NW, N, or NE
		
		switch (direction)
		{
			case NorthWest:
			case North:
			case NorthEast:
				return this;
			case SouthWest:
			case South:
			case SouthEast:
				return new EdgeLocation(hexLoc.getNeighborLoc(direction),
										direction.getOppositeDirection());
			default:
				assert false;
				return null;
		}
	}

	@Override
	public void postDeserializationSetup(Game game) {
		if (hexLoc == null) {
			hexLoc = new HexLocation(x, y);
		}
	}
}

