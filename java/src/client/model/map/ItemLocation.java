package client.model.map;

import shared.locations.*;

public class ItemLocation {
	private HexLocation location;
	private VertexDirection direction;
	
	public ItemLocation(HexLocation location, VertexDirection direction) {
		super();
		this.location = location;
		this.direction = direction;
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
		
		ItemLocation compareTo = (ItemLocation)obj;
		if(compareTo.location.equals(this.location) && compareTo.direction == this.direction)
			return true;
			
			return false;
	}

	public VertexLocation toVertexLocation()
	{
		VertexLocation vertLoc = new VertexLocation(location,direction);
		return vertLoc;
	}

	public HexLocation getLocation() {
		return location;
	}
	public void setLocation(HexLocation location) {
		this.location = location;
	}
	public VertexDirection getDirection() {
		return direction;
	}
	public void setDirection(VertexDirection direction) {
		this.direction = direction;
	}
	
	
}
