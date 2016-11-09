package shared.locations;

import shared.model.Game;
import shared.model.PostProcessor;

/**
 * Represents the location of a vertex on a hex map
 */
public class VertexLocation implements PostProcessor
{
	private transient HexLocation hexLoc;
	private VertexDirection direction;
    private int x;
    private int y;
	
	public VertexLocation(HexLocation hexLoc, VertexDirection dir)
	{
		setHexLoc(hexLoc);
		setDir(dir);
        this.x = hexLoc.getX();
        this.y = hexLoc.getY();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public HexLocation getHexLoc()
	{
		return hexLoc;
	}

	private void setHexLoc(HexLocation hexLoc)
	{
		if(hexLoc == null)
		{
			throw new IllegalArgumentException("hexLoc cannot be null");
		}
		this.hexLoc = hexLoc;
	}

	public VertexDirection getDir()
	{
		return direction;
	}

	private void setDir(VertexDirection direction)
	{
		this.direction = direction;
	}
	
	@Override
	public String toString()
	{
		return "VertexLocation [hexLoc=" + hexLoc + ", dir=" + direction + "]";
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
		VertexLocation other = (VertexLocation)obj;
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

	public VertexDirection getDirection() {
		return direction;
	}

	/**
	 * Returns a canonical (i.e., unique) value for this vertex location. Since
	 * each vertex has three different locations on a map, this method converts
	 * a vertex location to a single canonical form. This is useful for using
	 * vertex locations as map keys.
	 * 
	 * @return Normalized vertex location
	 */
	public VertexLocation getNormalizedLocation()
	{
		
		// Return location that has direction NW or NE
		
		switch (direction)
		{
			case NorthWest:
			case NorthEast:
				return this;
			case West:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.SouthWest),
										  VertexDirection.NorthEast);
			case SouthWest:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.South),
										  VertexDirection.NorthWest);
			case SouthEast:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.South),
										  VertexDirection.NorthEast);
			case East:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.SouthEast),
										  VertexDirection.NorthWest);
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

