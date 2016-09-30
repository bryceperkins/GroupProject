package client.model.map;

import client.model.HexDirection;

public class ItemLocation {
	private HexLocation location;
	private HexDirection direction;
	
	public ItemLocation(HexLocation location, HexDirection direction) {
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
	public HexLocation getLocation() {
		return location;
	}
	public void setLocation(HexLocation location) {
		this.location = location;
	}
	public HexDirection getDirection() {
		return direction;
	}
	public void setDirection(HexDirection direction) {
		this.direction = direction;
	}
	
	
}
